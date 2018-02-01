package com.parkingtycoon.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.CoordinateRotater;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.views.FlagView;
import com.parkingtycoon.views.FloorsView;

import java.util.ArrayList;

/**
 * This class is responsible for providing the floor of te simulation world
 */
public class FloorsController extends UpdateableController {

    public FloorsView view;
    public ArrayList<FloorModel> floors = new ArrayList<>();
    public FloorModel.FloorType nextFloorType;

    private int currentFloor = 0;

    public static final int BUILD_MARGIN = 15;

    public static boolean inBuildZone(int x, int y) {
        return x >= BUILD_MARGIN
                && x < Game.WORLD_WIDTH - BUILD_MARGIN
                && y >= BUILD_MARGIN
                && y < Game.WORLD_HEIGHT - BUILD_MARGIN;
    }

    public FloorsController() {

        super();

        CompositionRoot root = CompositionRoot.getInstance();

        FloorModel floor = createFloor();
        view = new FloorsView();
        floor.registerView(view);
        floors.add(floor);

        setCurrentFloor(0);

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_3, () -> {
            nextFloorType = FloorModel.FloorType.ROAD;
            return true;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_4, () -> {
            nextFloorType = FloorModel.FloorType.PARKABLE;
            return true;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_5, () -> {
            nextFloorType = FloorModel.FloorType.GRASS;
            return true;
        });

        root.inputController.onMouseButtonDown.add((screenX, screenY, button) -> {

            FloorModel f = floors.get(currentFloor);
            if (button == 0 && f.getNewFloorType() != null) {

                if (!f.fromFlagPlaced)
                    f.fromFlagPlaced = true;
                else
                    f.toFlagPlaced = true;

                return true;
            }
            return false;
        });
        root.inputController.onKeyDown.put(Input.Keys.ESCAPE, () -> { // TODO: cant bind multiple listeners to one key!!!
            FloorModel f = floors.get(currentFloor);
            if (f.getNewFloorType() != null) {
                f.stopPlacing = true;
                Logger.info("ggg");
                return true;
            }
            return false;
        });
    }

    @Override
    public void update() {

        FloorModel floor = floors.get(currentFloor);

        if (nextFloorType != null) {
            floor.setNewFloorType(nextFloorType);
            nextFloorType = null;
        }

        if (floor.getNewFloorType() != null) {
            setNewFloorFlag(floor);
            if (floor.toFlagPlaced)
                placeNewFloorTiles(floor);
            if (floor.stopPlacing)
                floor.setNewFloorType(null);
        }

        for (FloorModel f : floors) {
            for (int x = 0; x < Game.WORLD_WIDTH; x++) {
                for (int y = 0; y < Game.WORLD_HEIGHT; y++) {
                    f.waitingTime[x][y] += f.waitingTime[x][y] > 0 ? -1 : 0;
                }
            }
        }

    }

    public void setCurrentFloor(int currentFloor) {
        if (currentFloor < 0 || currentFloor >= floors.size())
            return;

        this.currentFloor = currentFloor;
        for (int i = 0; i < floors.size(); i++)
            floors.get(i).setCurrentFloor(i == currentFloor);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    private FloorModel createFloor() {
        FloorModel floor = new FloorModel();

        for (int x = 0; x < Game.WORLD_WIDTH; x++) {

            floor.tiles[x] = new FloorModel.FloorType[Game.WORLD_HEIGHT];
            floor.waitingTime[x] = new int[Game.WORLD_HEIGHT];
            floor.accessibleParkables[x] = new Boolean[Game.WORLD_HEIGHT];

            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                floor.tiles[x][y] = inBuildZone(x, y) ?
                        FloorModel.FloorType.GRASS :
                        (x % 9 == 0 || y % 9 == 0 ? FloorModel.FloorType.ROAD : FloorModel.FloorType.GRASS);
            }
        }

        for (int x = 0; x < Game.WORLD_WIDTH; x++)
            floor.parkedCars[x] = new CarModel[Game.WORLD_HEIGHT];

        return floor;
    }

    private void setNewFloorFlag(FloorModel floor) {
        Vector2 cursor = IsometricConverter.cursorToNormal();

        int[] flagCoordinates = new int[]{
                Math.max(0, Math.min((int) cursor.x, Game.WORLD_WIDTH - 1)),
                Math.max(0, Math.min((int) cursor.y, Game.WORLD_HEIGHT - 1))
        };

        if (floor.getNewFloorFrom() == null)
            showFlag(floor, true);

        if (!floor.fromFlagPlaced)
            floor.setNewFloorFrom(flagCoordinates);
        else {
            if (floor.getNewFloorTo() == null)
                showFlag(floor, false);

            floor.setNewFloorTo(flagCoordinates);
        }

        Boolean[][] newFloorValid = new Boolean[Game.WORLD_WIDTH][];

        for (int x = Math.min(flagCoordinates[0], floor.getNewFloorFrom()[0]);
             x <= Math.max(flagCoordinates[0], floor.getNewFloorFrom()[0]);
             x++) {

            newFloorValid[x] = new Boolean[Game.WORLD_HEIGHT];

            for (int y = Math.min(flagCoordinates[1], floor.getNewFloorFrom()[1]);
                 y <= Math.max(flagCoordinates[1], floor.getNewFloorFrom()[1]);
                 y++) {

                newFloorValid[x][y] = inBuildZone(x, y)
                        && (floor.buildings[x] == null || floor.buildings[x][y] == null);

            }
        }

        floor.setNewFloorValid(newFloorValid);
    }

    private void showFlag(FloorModel floor, boolean green) {
        FlagView flag = new FlagView(green);
        flag.show();
        floor.registerView(flag);
    }

    private void placeNewFloorTiles(FloorModel floor) {

        CarsController carsController = CompositionRoot.getInstance().carsController;

        FloorModel.FloorType floorType = floor.getNewFloorType();
        Boolean[][] newFloorValid = floor.getNewFloorValid();

        for (int x = Math.min(floor.getNewFloorTo()[0], floor.getNewFloorFrom()[0]);
             x <= Math.max(floor.getNewFloorTo()[0], floor.getNewFloorFrom()[0]);
             x++) {

            if (newFloorValid[x] == null)
                continue;

            for (int y = Math.min(floor.getNewFloorTo()[1], floor.getNewFloorFrom()[1]);
                 y <= Math.max(floor.getNewFloorTo()[1], floor.getNewFloorFrom()[1]);
                 y++) {

                if (Boolean.TRUE.equals(newFloorValid[x][y]) && CompositionRoot.getInstance().financialController.spend(10)) {
                    floor.setTile(x, y, floorType);

                    CarModel parkedHere = floor.parkedCars[x][y];
                    if (parkedHere != null && !carsController.sendToExit(parkedHere)) {

                        carsController.sendToEndOfTheWorld(parkedHere, true);
                    }
                }
            }
        }

        floor.setNewFloorType(null);
        checkParkables(floor);
        carsController.onTerrainChange(floors.indexOf(floor), newFloorValid);
    }

    private void checkParkables(FloorModel floor) {

        for (int x = 0; x < Game.WORLD_WIDTH; x++) {

            FloorModel.FloorType prevType = FloorModel.FloorType.GRASS;

            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                FloorModel.FloorType type = floor.tiles[x][y];
                floor.accessibleParkables[x][y] = type != FloorModel.FloorType.PARKABLE
                        || prevType == FloorModel.FloorType.ROAD
                        || isParkableAccessible(floor, x, y);

                prevType = type;
            }
        }
    }

    private boolean isParkableAccessible(FloorModel floor, int parkableX, int parkableY) {

        for (int angle = 0; angle < 4; angle++) {

            int x = parkableX + CoordinateRotater.rotate(1, 1, 0, 1, angle);
            int y = parkableY + CoordinateRotater.rotate(0, 1, 1, 1, angle);

            if (x < 0 || x >= Game.WORLD_WIDTH || y < 0 || y >= Game.WORLD_HEIGHT)
                continue;

            if (floor.tiles[x][y] == FloorModel.FloorType.ROAD)
                return true;

        }
        return false;
    }

}
