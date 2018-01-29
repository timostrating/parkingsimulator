package com.parkingtycoon.controllers;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.helpers.pathfinding.NavMap;
import com.parkingtycoon.helpers.pathfinding.PathFinder;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.models.PathFollowerModel;
import com.parkingtycoon.views.CarView;

import java.util.Iterator;


/**
 * This class is responsible for controlling the Cars in the simulation.
 */
public class CarsController extends PathFollowersController<CarModel> {

    private final Vector2 diff = new Vector2(); // this vector is reused for calculations

    public static final int APPEAR_X = 0, APPEAR_Y = 0, DISAPPEAR_X = 99, DISAPPEAR_Y = 0;

    private FloorsController floorsController = CompositionRoot.getInstance().floorsController;

    public CarModel spawnCar() {

        CarModel car = new CarModel();
        car.position.set(Random.randomInt(10) * 9, 0);
        pathFollowers.add(car);
        CarView carView = new CarView(APPEAR_X, APPEAR_Y);
        carView.show();
        car.registerView(carView);

        CompositionRoot.getInstance().entrancesController.addToQueue(car);

        return car;
    }

    @Override
    protected boolean sendTo(CarModel pathFollower, int x, int y) {

        if (!super.sendTo(pathFollower, x, y))
            return false;

        // move the nodes so that the cars drive on the right of the road

        PathFinder.Node prevNode = null;
        int i = 0;

        for (PathFinder.Node n : pathFollower.getPath()) {

            n.actualX += .5f;
            n.actualY += .5f;

            if (++i < pathFollower.getPath().size() && prevNode != null) {

                Vector2 direction = new Vector2(n.x - prevNode.x, n.y - prevNode.y);
                direction.rotate90(1);
                direction.nor().scl(.3f);

                if (prevNode.actualY - .5f == prevNode.y)
                    prevNode.actualY += direction.y;

                if (prevNode.actualX - .5f == prevNode.x)
                    prevNode.actualX += direction.x;

                n.actualX += direction.x;
                n.actualY += direction.y;

            }

            prevNode = n;
        }

        return true;
    }

    @Override
    public void update() {
        super.update();

        Iterator<CarModel> carsIterator = pathFollowers.iterator();
        while (carsIterator.hasNext()) {

            CarModel car = carsIterator.next();

            if ((int) car.position.x == DISAPPEAR_X && (int) car.position.y == DISAPPEAR_Y) {
                car.disappear();
                carsIterator.remove();
                continue;
            }

            car.parked = car.getPath() == null && !car.waitingInQueue;

            if (car.waitingOn != null || car.waitingInQueue) // increase waitingTime so that cars will avoid this place
                floorsController.floors.get(car.floor).waitingTime[(int) car.position.x][(int) car.position.y]++;

            if (car.getPath() == null && car.timer++ >= car.endTime - car.startTime) {

                // time for this car to leave
                if (unparkCar(car)) {
                    Logger.info("Car at " + car.position + " sent to exit after staying " + car.timer + " updates.");
                    carsIterator.remove();
                }

            }

            updateCarAABB(car);
        }

        for (CarModel car : pathFollowers) {

            detectCollisions(car);

            if (car.waitingOn != null)
                car.brake = 1;                               // brake
            else
                car.brake = Math.max(0, car.brake - .015f);  // accelerate


        }

    }

    @Override
    protected NavMap getNavMap(CarModel pathFollower) {
        return floorsController.floors.get(pathFollower.floor).carNavMap;
    }

    private void detectCollisions(CarModel car) {
        if (car.waitingOn != null
                && (car.floor != car.waitingOn.floor || car.waitingOn.parked || !car.aabb.overlaps(car.waitingOn.aabb)))
            car.waitingOn = null;

        if (car.waitingOn != null || car.parked)
            return;

        // check if car collides with other cars:
        for (CarModel otherCar : pathFollowers) {
            if (car.floor != otherCar.floor                     // cannot collide with cars on another floor
                    || otherCar.parked                          // cannot collide with parked cars
                    || otherCar == car                          // cannot collide with itself
                    || !car.aabb.overlaps(otherCar.aabb))       // does it collide?
                continue;                                       // no.

            // the distance between the cars:
            float dist = diff.set(car.position).sub(otherCar.position).len();

            // the distance between the cars if 'car' waits next update:
            float newDist = diff.set(car.position).sub(
                    otherCar.position.x + otherCar.direction.x,
                    otherCar.position.y + otherCar.direction.y).len();

            CarModel waiter = newDist >= dist ? car : otherCar;
            CarModel prior = waiter == car ? otherCar : car;

            if (!waiter.waitingInQueue && prior.waitingInQueue) {
                CarModel newWaiter = prior;
                prior = waiter;
                waiter = newWaiter;
            }

            boolean canWait = true;

            CarModel waitingOn = prior.waitingOn;
            while (waitingOn != null) {

                if (waitingOn == waiter) {
                    canWait = false;
                    break;
                }
                waitingOn = waitingOn.waitingOn;

            }

            if (canWait)
                waiter.waitingOn = prior;

        }
    }

    private void updateCarAABB(CarModel car) {
        car.aabb.center.set(car.position);

        if (!car.direction.isZero()) {

            float xLen = Math.abs(car.direction.x);
            float yLen = Math.abs(car.direction.y);

            car.aabb.halfSize.set(
                    Math.max(.2f, xLen / (xLen + yLen)),
                    Math.max(.2f, yLen / (xLen + yLen))
            );

        }
    }

    public boolean parkCar(CarModel car) {

        // check if there's an available place for the car.
        int i = 0;
        for (FloorModel floor : floorsController.floors) {
            for (int x = 0; x < Game.WORLD_WIDTH; x++) {
                for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                    if (floor.tiles[x][y] == FloorModel.FloorType.PARKABLE
                            && floor.parkedCars[x][y] == null) {

                        // available place found.
                        floor.parkedCars[x][y] = car;

                        // send car to place:
                        car.setGoal(new PathFollowerModel.Goal(i, x, y) {

                            @Override
                            public void arrived() {

                            }

                            @Override
                            public void failed() {

                            }
                        });

                        Logger.info("Car parked at (" + x + ", " + y + ") on floor " + i);
                        return true;

                    }
                }
            }

            i++;
        }

        return false;
    }

    private boolean unparkCar(CarModel car) {

        if (!CompositionRoot.getInstance().exitsController.addToQueue(car))
            return false; // No exit found

        floorLoop:
        for (FloorModel floor : floorsController.floors) {

            for (int x = 0; x < Game.WORLD_WIDTH; x++) {
                for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                    if (floor.parkedCars[x][y] == car) {
                        floor.parkedCars[x][y] = null;
                        break floorLoop;
                    }
                }
            }
        }
        car.parked = false;

        return true;
    }

}
