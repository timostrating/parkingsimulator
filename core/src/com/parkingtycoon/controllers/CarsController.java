package com.parkingtycoon.controllers;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.helpers.pathfinding.PathFinder;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.ElevatorModel;
import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.models.PathFollowerModel;
import com.parkingtycoon.views.CarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * This class is responsible for controlling the Cars in the simulation.
 */
public class CarsController extends PathFollowersController<CarModel> {

    // this vector is reused for calculations
    private final Vector2 diff = new Vector2();

    // Cars that have reserved a place, but are not yet in the game-world
    private final ArrayList<CarModel> reservedCarsToSpawn = new ArrayList<>();

    /**
     * This method will add cars to the simulation.
     *
     * It will decide how big the chances are that a new car is added.
     * It will also decide what type the car will be. (AD_HOC, VIP or RESERVED)
     *
     * When a reserved-type car is added and a place is reserved,
     * the car will not be spawned but after a certain time it will be.
     */
    private void addCars() {
        SimulationController simulationController = CompositionRoot.getInstance().simulationController;
        long updates = simulationController.getUpdates();

        float sin = (float) (Math.sin(
                (double) updates / (double) SimulationController.REAL_TIME_UPDATES_PER_SECOND / 300
        ) + 1) / 2;

        float spawnChance = .995f - (sin * .1f);

        if (Math.random() > spawnChance) {

            if (Math.random() > .2f) {
                spawnCar(new CarModel(CarModel.CarType.AD_HOC));

            } else if (Math.random() > .4f) {
                spawnCar(new CarModel(CarModel.CarType.VIP));

            } else if (reservePlace()) {
                CarModel reserver = new CarModel(CarModel.CarType.RESERVED);
                reservedCarsToSpawn.add(reserver);                              // do not spawn yet.
                reserver.reservationTimer = Random.randomInt(                   // wait before spawning
                        SimulationController.REAL_TIME_UPDATES_PER_SECOND * 15,
                        SimulationController.REAL_TIME_UPDATES_PER_SECOND * 30
                );
                reserver.claimedReservedPlace = true;
            }
        }

        Iterator<CarModel> it = reservedCarsToSpawn.iterator();
        while (it.hasNext()) {

            CarModel reserved = it.next();

            if (--reserved.reservationTimer <= 0) {
                it.remove();
                spawnCar(reserved);                     // the car has waited, now spawn the car.
            }
        }
    }

    /**
     * This method will make a car appear in the game-world and it will send it to an entrance.
     *
     * @param car The car that has to be spawned
     */
    private void spawnCar(CarModel car) {

        car.setOnActiveFloor(car.floor == floorsController.getCurrentFloor());

        if (Math.random() > .5f)
            car.position.set(Random.randomInt(10) * 9, Math.random() > .5f ? 0 : Game.WORLD_HEIGHT - 1);
        else
            car.position.set(Random.randomInt(10) * 9, Math.random() > .5f ? 0 : Game.WORLD_HEIGHT - 1);

        pathFollowers.add(car);
        CarView carView = new CarView(car.position.x, car.position.y, car.carType);
        carView.show();
        car.registerView(carView);

        if (!CompositionRoot.getInstance().entrancesController.addToQueue(car))
            sendToEndOfTheWorld(car, true);

    }

    /**
     * This method will search for an available place that can be reserved.
     * If a place is found it will reserve the place and return true.
     *
     * @return Whether or not a place could be reserved.
     */
    private boolean reservePlace() {

        for (FloorModel floor : floorsController.floors) {
            for (int x = 0; x < Game.WORLD_WIDTH; x++) {
                for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                    if (floor.tiles[x][y] == FloorModel.FloorType.PARKABLE
                            && floor.accessibleParkables[x] != null
                            && Boolean.TRUE.equals(floor.accessibleParkables[x][y])
                            && floor.parkedCars[x][y] == null
                            && !floor.isReserved(x, y)) {

                        // found a place that can be reserved

                        floor.setReserved(x, y, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * The update method will do several things:
     *
     * 1. Call the super.update() method. This will make the cars travel.
     * 2. Call addCars(). This will add cars to the simulation.
     * 3. Increase waitingTime so that other cars will avoid busy areas
     * 4. Send cars to the exit
     * 5. Make cars brake when they're colliding with other cars.
     */
    @Override
    public void update() {
        super.update();

        addCars();

        for (CarModel car : pathFollowers) {

            if (car.waitingOn != null || car.waitingInQueue) // increase waitingTime so that cars will avoid this place
                floorsController.floors.get(car.floor).waitingTime[(int) car.position.x][(int) car.position.y] += 5;

            if (car.parked && car.timer++ >= car.endTime - car.startTime) {

                // time for this car to leave

                if (!sendToExit(car)) {
                    car.timer -= Random.randomInt(20, 50); // try again after some updates. This to prevent extreme lag
                }

            }

            // if there is a traffic jam somehow -> despawn cars that are stuck for a long time
            if (car.waitingOn != null && !car.waitingInQueue) {

                if (++car.waitingTooLongTimer > 1000) {
                    clearParkingSpace(car);
                    car.setDisappeared();
                }

            } else car.waitingTooLongTimer = Math.max(0, --car.waitingTooLongTimer);

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

    /**
     * This method will update the Axis Aligned Bounding Box of a car.
     * The AABB is used to detect collisions with other cars.
     *
     * @param car The car
     */
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

    /**
     * This method will detect collisions with any other car.
     * If a collision is detected, the method will decide which of the 2 cars have to brake.
     *
     * @param car The car you want to detect collisions for.
     */
    private void detectCollisions(CarModel car) {
        if (car.waitingOn != null
                && (car.floor != car.waitingOn.floor
                || car.alwaysPrior
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

            if ((!waiter.waitingInQueue && prior.waitingInQueue && !prior.alwaysPrior) || waiter.alwaysPrior) {
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

    /**
     * This method will search for an available parking place, and send the car to it.
     *
     * @param car   The car you want to park
     * @return      Whether or not an available place was found.
     */
    public boolean parkCar(CarModel car) {
        return parkCar(car, (int) car.position.x, (int) car.position.y);
    }

    /**
     * This method will search for an available parking place, and send the car to it.
     *
     * @param car   The car you want to park
     * @param fromX The x-position from where to calculate a path from
     * @param fromY The y-position from where to calculate a path from
     * @return      Whether or not an available place was found.
     */
    public boolean parkCar(CarModel car, int fromX, int fromY) {

        if (car.carType == CarModel.CarType.RESERVED && !car.claimedReservedPlace) {
            if (!reservePlace())
                return false;
            car.claimedReservedPlace = true;
        }

        // check if there's an available place for the car.
        int i = 0;
        for (FloorModel floor : floorsController.floors) {
            for (int x = 0; x < Game.WORLD_WIDTH; x++) {
                for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                    if (floor.tiles[x][y] != FloorModel.FloorType.PARKABLE
                            || floor.parkedCars[x][y] != null
                            || (car.carType == CarModel.CarType.RESERVED) != floor.isReserved(x, y))
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

    /**
     * This method will actually send a car to its parking place.
     * If the car fails to arrive, the car will try again to find another parking place or drive to the exit.
     *
     * @param car   The car that has to be send to a parking place
     * @param floor The floor that the parking place is on
     * @param x     The x-coordinate of the parking place
     * @param y     The y-coordinate of the parking place
     * @param fromX The x-position from where to calculate a path from
     * @param fromY The y-position from where to calculate a path from
     * @return      Whether or not a path could be calculated to the parking place
     */
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

    /**
     * This method will try to send a car to an exit.
     * This method will also clear the parking space of the car.
     *
     * @param car The car that has to be send to the exit
     * @return    Whether or not an exit was found.
     */
    public boolean sendToExit(CarModel car) {

        if (CompositionRoot.getInstance().exitsController.addToQueue(car)) {
            clearParkingSpace(car);
            return true;
        }
        return false;
    }

    /**
     * This method will make a car drive to the end of the world.
     * When arrived the car will despawn and be removed from the simulation.
     *
     * @param car       The car that has to go away
     * @param forced    If forced, the car will also despawn when no path could be found to the end of the world.
     * @return          Whether or not the car is going to despawn.
     */
    public boolean sendToEndOfTheWorld(CarModel car, boolean forced) {
        return sendToEndOfTheWorld(car, (int) car.position.x, (int) car.position.y, forced);
    }

    /**
     * This method will make a car drive to the end of the world.
     * When arrived the car will despawn and be removed from the simulation.
     *
     * @param car       The car that has to go away
     * @param fromX     The x-position from where to calculate a path from
     * @param fromY     The y-position from where to calculate a path from
     * @param forced    If forced, the car will also despawn when no path could be found to the end of the world.
     * @return          Whether or not the car is going to despawn.
     */
    public boolean sendToEndOfTheWorld(CarModel car, int fromX, int fromY, boolean forced) {

        int toX, toY;
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

    /**
     * This method will clear the parking space of a car
     *
     * @param car The car you want to clear the parking space of.
     */
    public void clearParkingSpace(CarModel car) {

        for (FloorModel floor : floorsController.floors) {
            for (int x = 0; x < Game.WORLD_WIDTH; x++) {
                for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                    if (floor.parkedCars[x][y] == car)
                        floor.parkedCars[x][y] = null;

                    if (car.claimedReservedPlace && car.carType == CarModel.CarType.RESERVED
                            && floor.isReserved(x, y)
                            && floor.parkedCars[x][y] == null) {

                        floor.setReserved(x, y, false);
                        car.claimedReservedPlace = false;
                    }
                }
            }
        }
        car.parked = false;
    }

    /**
     * This method will try to find a path to a elevator
     *
     * @param pathFollower  The car that has to go to an elevator
     * @param fromX         From where to calculate the path
     * @param fromY         From where to calculate the path
     * @return              Whether or not a path was found to an elevator
     */
    @Override
    protected List<PathFinder.Node> getPathToElevator(CarModel pathFollower, int fromX, int fromY) {

        ElevatorsController elevatorsController = CompositionRoot.getInstance().elevatorsController;
        Collections.shuffle(elevatorsController.elevators);

        for (ElevatorModel elevator : elevatorsController.elevators) {

            List<PathFinder.Node> path = getPath(pathFollower.floor, fromX, fromY, elevator.x + 1, elevator.y + 1);
            if (path != null) {
                elevator.cars.add(pathFollower);
                return path;
            }
        }

        return null;
    }

    /**
     * This method will ask the PathFinder for a path from point A to B
     * When a path was found, the path will be repositioned so that cars drive on the right of the road.
     *
     * @param floor On which floor
     * @param fromX From where
     * @param fromY From where
     * @param toX   To where
     * @param toY   To where
     * @return      The found path
     */
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
     * This method is called when the floor underneath an idle car is changed.
     * It will make the car drive away.
     *
     * @param pathFollower The car
     */
    @Override
    protected void onIdlesFloorChanged(CarModel pathFollower) {
        sendToEndOfTheWorld(pathFollower, true);
    }

    /**
     * This method will make sure that cars won't park on a place that is not PARKABLE anymore.
     * The method is called when the floor-layout changed
     *
     * @param floorIndex   The regarding floor
     * @param tilesChanged What tiles have changed?
     */
    @Override
    public void onTerrainChange(int floorIndex, Boolean[][] tilesChanged) {

        FloorModel floor = floorsController.floors.get(floorIndex);

        for (int x = 0; x < Game.WORLD_WIDTH; x++) {
            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                if (floor.isReserved(x, y)
                        && (floor.tiles[x][y] != FloorModel.FloorType.PARKABLE
                        || !Boolean.TRUE.equals(floor.accessibleParkables[x][y]))) {

                    floor.setReserved(x, y, false);

                    CarModel parkedHere = floor.parkedCars[x][y];
                    if (parkedHere != null) {

                        parkedHere.claimedReservedPlace = false;

                    } else if (reservedCarsToSpawn.size() > 0) {

                        reservedCarsToSpawn.remove(0);

                    } else {
                        for (CarModel car : pathFollowers) {
                            if (car.claimedReservedPlace && car.carType == CarModel.CarType.RESERVED) {
                                car.claimedReservedPlace = false;
                                break;
                            }
                        }
                    }
                }
            }
        }

        super.onTerrainChange(floorIndex, tilesChanged);
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
