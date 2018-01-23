package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.helpers.UpdateableController;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.views.FloorsView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sneeuwpopsneeuw on 17-Jan-18.
 */
public class FloorsController extends UpdateableController {

    private ArrayList<FloorModel> floors = new ArrayList<>();
    private FloorsView view;
    private int currentFloor = 0;

    public FloorsController() {

        CompositionRoot.getInstance().simulationController.registerUpdatable(this);

        FloorModel floor = createFloor();
        view = new FloorsView();
        floor.registerView(view);
        floors.add(floor);

        setCurrentFloor(0);
    }

    @Override
    public void update() {

        for (FloorModel floor : floors) {
            Iterator<CarModel> carIterator = floor.cars.iterator();
            while (carIterator.hasNext()) {

                CarModel car = carIterator.next();

                if (car.timer++ >= car.endTime - car.startTime) {

                    // time for this car to leave
                    if (sendCarToExit(car)) {
                        Logger.info("Car at " + car.position + " sent to exit after staying " + car.timer + " updates.");
                        carIterator.remove();
                    }

                }
            }

        }
    }

    public boolean parkCar(CarModel car) {

        // check if there's an available place for the car.
        int i = 0;
        for (FloorModel floor : floors) {
            for (int x = 0; x < Game.WORLD_WIDTH; x++) {
                for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                    if (floor.tiles[x][y] == FloorModel.FloorType.PARKABLE
                            && floor.parkedCars[x][y] == null) {

                        // available place found.
                        floor.parkedCars[x][y] = car;
                        floor.cars.add(car);

                        // send car to place:
                        CompositionRoot.getInstance().pathFollowerController.sendTo(car, floor.carNavMap, x, y);

                        Logger.info("Car parked at (" + x + ", " + y + ") on floor " + i);
                        return true;

                    }
                }
            }

            i++;
        }

        return false;
    }

    public boolean sendCarToExit(CarModel car) {

        floorLoop:
        for (FloorModel floor : floors) {

            for (int x = 0; x < Game.WORLD_WIDTH; x++) {
                for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                    if (floor.parkedCars[x][y] == car) {
                        floor.parkedCars[x][y] = null;
                        break floorLoop;
                    }
                }
            }
        }

        return CompositionRoot.getInstance().exitsController.addToQueue(car);
    }

    public void setCurrentFloor(int currentFloor) {
        if (currentFloor < 0 || currentFloor >= floors.size())
            return;

        this.currentFloor = currentFloor;
        for (int i = 0; i < floors.size(); i++)
            floors.get(i).setCurrentFloor(i == currentFloor);
    }

    private FloorModel createFloor() {
        FloorModel floor = new FloorModel();

        // (hack) adding tiles: (road and parkables)
        for (int x = 0; x < Game.WORLD_WIDTH; x++) {

            floor.tiles[x] = new FloorModel.FloorType[Game.WORLD_HEIGHT];

            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                floor.tiles[x][y] = x % 2 == 0 || y % 2 == 0 ? FloorModel.FloorType.GRASS : FloorModel.FloorType.PARKABLE;

            }
        }

        for (int x = 0; x < Game.WORLD_WIDTH; x++)
            floor.parkedCars[x] = new CarModel[Game.WORLD_HEIGHT];

        return floor;
    }

}
