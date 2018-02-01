package com.parkingtycoon.controllers;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.helpers.pathfinding.PathFinder;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.models.PathFollowerModel;
import com.parkingtycoon.views.CarView;

import java.util.List;


/**
 * This class is responsible for controlling the Cars in the simulation.
 */
public class CarsController extends PathFollowersController<CarModel> {

    private final Vector2 diff = new Vector2(); // this vector is reused for calculations

    private FloorsController floorsController = CompositionRoot.getInstance().floorsController;

    public void spawnCar() {
        CarModel car = new CarModel();
        car.setOnActiveFloor(car.floor == floorsController.getCurrentFloor());
        car.vip = Math.random() > .7f; // todo: change with slider

        if (Math.random() > .5f)
            car.position.set(Random.randomInt(10) * 9, Math.random() > .5f ? 0 : Game.WORLD_HEIGHT - 1);
        else
            car.position.set(Random.randomInt(10) * 9, Math.random() > .5f ? 0 : Game.WORLD_HEIGHT - 1);

        pathFollowers.add(car);
        CarView carView = new CarView(car.position.x, car.position.y, car.vip);
        carView.show();
        car.registerView(carView);

        if (!CompositionRoot.getInstance().entrancesController.addToQueue(car))
            sendToEndOfTheWorld(car, true);

    }

    @Override
    public void update() {
        super.update();

        for (CarModel car : pathFollowers) {

            if (car.waitingOn != null || car.waitingInQueue) // increase waitingTime so that cars will avoid this place
                floorsController.floors.get(car.floor).waitingTime[(int) car.position.x][(int) car.position.y] += 5;

            if (car.parked && car.timer++ >= car.endTime - car.startTime) {

                // time for this car to leave

                if (!sendToExit(car)) {
                    car.timer -= 10; // try again after 10 updates. This to prevent extreme lag
                }

            }

            updateCarAABB(car);
        }

        /*
        In the previous loop the AABB (Axis aligned bounding box) of all cars were updated.
        In this loop we will use those AABBs to check if a car is colliding and needs to brake.
         */
        for (CarModel car : pathFollowers) {

            detectCollisions(car);

            if (car.waitingOn != null || car.direction.isZero())
                car.brake = 1;                               // brake
            else
                car.brake = Math.max(0, car.brake - .015f);  // accelerate

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

    private void detectCollisions(CarModel car) {
        if (car.waitingOn != null
                && (car.floor != car.waitingOn.floor
                || car.waitingOn.parked
                || !car.aabb.overlaps(car.waitingOn.aabb)
                || car.waitingOn.isDisappeared()))
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

    public boolean parkCar(CarModel car) {
        return parkCar(car, (int) car.position.x, (int) car.position.y);
    }

    public boolean parkCar(CarModel car, int fromX, int fromY) {

        // check if there's an available place for the car.
        int i = 0;
        for (FloorModel floor : floorsController.floors) {
            for (int x = 0; x < Game.WORLD_WIDTH; x++) {
                for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                    if (floor.tiles[x][y] != FloorModel.FloorType.PARKABLE
                            || floor.parkedCars[x][y] != null)
                        continue;

                    // Available place found.

                    if (!sendToParkingPlace(car, i, x, y, fromX, fromY))
                        continue;   // Cannot reach this place -> search for another place.

                    floor.parkedCars[x][y] = car;

//                    Logger.info("Car parked at (" + x + ", " + y + ") on floor " + i);
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    private boolean sendToParkingPlace(CarModel car, int floor, int x, int y, int fromX, int fromY) {

        if (!floorsController.floors.get(floor).accessibleParkables[x][y])
            return false;

        PathFollowerModel.Goal goal = new PathFollowerModel.Goal(
                floor, x, y,
                fromX, fromY
        ) {

            @Override
            public void arrived() {
                car.parked = true;
                car.startTime = CompositionRoot.getInstance().simulationController.getUpdates();
                car.endTime = car.startTime + Random.randomInt(
                        240 * SimulationController.REAL_TIME_UPDATES_PER_SECOND,
                        500 * SimulationController.REAL_TIME_UPDATES_PER_SECOND
                );
            }

            @Override
            public void failed() {
                clearParkingSpace(car);
                if (!parkCar(car) && !sendToExit(car))
                    sendToEndOfTheWorld(car, true);
            }

        };

        return setGoal(car, goal);
    }

    public boolean sendToExit(CarModel car) {

        if (CompositionRoot.getInstance().exitsController.addToQueue(car)) {
            clearParkingSpace(car);
            return true;
        }
        return false;
    }

    public boolean sendToEndOfTheWorld(CarModel car, boolean forced) {
        return sendToEndOfTheWorld(car, (int) car.position.x, (int) car.position.y, forced);
    }

    public boolean sendToEndOfTheWorld(CarModel car, int fromX, int fromY, boolean forced) {

        int toX = 0, toY = 0;
        if (Math.random() > .5f) {
            toX = Random.randomInt(10) * 9;
            toY = Math.random() > .5f ? 0 : Game.WORLD_HEIGHT - 1;
        } else {
            toX = Random.randomInt(10) * 9;
            toY = Math.random() > .5f ? 0 : Game.WORLD_HEIGHT - 1;
        }

        if (setGoal(car, new PathFollowerModel.Goal(0, toX, toY, fromX, fromY) {

            @Override
            public void arrived() {
                car.setDisappeared();
            }

            @Override
            public void failed() {
                car.setDisappeared();
            }

        })) {
            clearParkingSpace(car);
            return true;

        } else if (forced) {
            clearParkingSpace(car);
            car.setDisappeared();
            return true;
        }
        return false;
    }

    public void clearParkingSpace(CarModel car) {

        FloorModel floor = floorsController.floors.get(car.floor);

        for (int x = 0; x < Game.WORLD_WIDTH; x++) {
            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                if (floor.parkedCars[x][y] == car) {
                    floor.parkedCars[x][y] = null;
                }
            }
        }
        car.parked = false;

    }

    @Override
    protected List<PathFinder.Node> getPathToElevator(int floor, int fromX, int fromY) {
        return null; // todo: implement elevators.
    }

    @Override
    protected List<PathFinder.Node> getPath(int floor, int fromX, int fromY, int toX, int toY) {

        List<PathFinder.Node> path = PathFinder.calcPath(
                floorsController.floors.get(floor).carNavMap,   // The NavigationMap that is used to find a path
                fromX, fromY,                                   // from coordinates
                toX, toY                                        // to coordinates
        );

        repositionPath(path);
        return path;
    }


    /**
     * This method will move the nodes of a path to the right side of the road,
     * so that cars always drive on the right.
     *
     * @param path The path you want to reposition.
     */
    private void repositionPath(List<PathFinder.Node> path) {

        if (path == null)
            return;

        PathFinder.Node prevNode = null;
        int i = 0;

        for (PathFinder.Node n : path) {

            n.actualX += .5f;
            n.actualY += .5f;

            if (++i < path.size() && prevNode != null) {

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
    }

}
