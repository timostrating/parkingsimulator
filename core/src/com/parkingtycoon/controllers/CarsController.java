package com.parkingtycoon.controllers;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.PathFollowerModel;
import com.parkingtycoon.pathfinding.NavMap;
import com.parkingtycoon.pathfinding.PathFinder;

public class CarsController extends PathFollowerController<CarModel> {

    private final Vector2 diff = new Vector2(); // this vector is reused for calculations

    public CarModel createCar() {

        CarModel car = new CarModel();
        pathFollowers.add(car);
        return car;

    }

    @Override
    public void sendTo(PathFollowerModel pathFollower, NavMap navMap, int x, int y) {
        super.sendTo(pathFollower, navMap, x, y);

        if (pathFollower.getPath() != null) {
            // move the nodes so that the cars drive on the right of the road

            PathFinder.Node prevNode = null;
            for (PathFinder.Node n : pathFollower.getPath()) {
                if (prevNode != null) {

                    Vector2 direction = new Vector2(n.x - prevNode.x, n.y - prevNode.y);
                    direction.rotate90(1);
                    direction.nor().scl(.25f);

                    if (prevNode.actualY - .5f == prevNode.y)
                        prevNode.actualY += direction.y;

                    if (prevNode.actualX - .5f == prevNode.x)
                        prevNode.actualX += direction.x;

                    n.actualX += direction.x + .5f;
                    n.actualY += direction.y + .5f;

                }

                prevNode = n;
            }

        }
    }

    @Override
    public void update() {
        super.update();

        Logger.info(pathFollowers.size());

        for (CarModel car : pathFollowers) {

            car.aabb.center.set(car.position);

            if (!car.direction.isZero()) {

                float xLen = Math.abs(car.direction.x);
                float yLen = Math.abs(car.direction.y);

                car.aabb.halfSize.set(
                        Math.max(.2f, xLen / 1.1f / (xLen + yLen)),
                        Math.max(.2f, yLen / 1.1f / (xLen + yLen))
                );

            }
        }

        for (CarModel car : pathFollowers) {

            detectCollisions(car);

            if (car.waitingOn != null)
                car.brake = 1;                              // brake
            else
                car.brake = Math.max(0, car.brake - .01f);  // accelerate


        }

    }

    private void detectCollisions(CarModel car) {
        if (car.waitingOn != null && (!car.aabb.overlaps(car.waitingOn.aabb) || car.floor != car.waitingOn.floor || car.waitingOn.parked))
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

            CarModel waiter = newDist > dist || (otherCar.waitingInQueue && otherCar.getPath() == null) ? car : otherCar;
            CarModel prior = waiter == car ? otherCar : car;

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

}
