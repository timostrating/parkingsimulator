package com.parkingtycoon.controllers;

import com.badlogic.gdx.math.Vector2;
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

        if (pathFollower.path != null) {
            // move the nodes so that the cars drive on the right of the road

            PathFinder.Node prevNode = null;
            for (PathFinder.Node n : pathFollower.path) {
                if (prevNode != null) {

                    Vector2 direction = new Vector2(n.actualX - prevNode.actualX, n.actualY - prevNode.actualY);
                    direction.rotate90(1);
                    direction.nor().scl(.25f);
                    n.actualX += direction.x;
                    n.actualY += direction.y;

                }

                prevNode = n;
            }

        }
    }

    @Override
    public void update() {
        super.update();

        for (CarModel car : pathFollowers) {

            car.aabb.center.set(car.position);

            if (!car.direction.isZero()) {

                if (Math.abs(car.direction.y) < .01f)
                    car.aabb.halfSize.set(1, .15f);
                else if (Math.abs(car.direction.x) < .1f)
                    car.aabb.halfSize.set(.15f, 1);
                else
                    car.aabb.halfSize.set(.5f, .5f);
            }
        }

        for (CarModel car : pathFollowers) {

            detectCollisions(car);

            if (car.waitingOn != null)
                car.brake = 1;                              // brake
            else
                car.brake = Math.max(0, car.brake - .04f);  // accelerate


        }

    }

    private void detectCollisions(CarModel car) {
        if (car.waitingOn != null && (!car.aabb.overlaps(car.waitingOn.aabb) || car.floor != car.waitingOn.floor))
            car.waitingOn = null;

        if (car.waitingOn != null)
            return;

        // check if car collides with other cars:
        for (CarModel otherCar : pathFollowers) {
            if (car.floor != otherCar.floor || otherCar == car || !car.aabb.overlaps(otherCar.aabb))
                continue;

            // the distance between the cars:
            float dist = diff.set(car.position).sub(otherCar.position).len();

            // the distance between the cars if 'car' waits next update:
            float newDist = diff.set(car.position).sub(
                    otherCar.position.x + otherCar.direction.x,
                    otherCar.position.y + otherCar.direction.y).len();

            CarModel waiter = newDist > dist ? car : otherCar;
            CarModel prior = waiter == car ? otherCar : car;

            boolean canWait = true;

            CarModel waitingOn = prior.waitingOn;
            while (waitingOn != null) {

                if (waitingOn == waiter) {
                    canWait = false;
                }
                waitingOn = waitingOn.waitingOn;

            }

            if (canWait)
                waiter.waitingOn = prior;

        }
    }

}
