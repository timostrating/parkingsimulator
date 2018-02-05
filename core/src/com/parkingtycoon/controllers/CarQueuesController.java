package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.CoordinateRotater;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.models.BuildingModel;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;
import com.parkingtycoon.models.PathFollowerModel;
import com.parkingtycoon.views.queue.ArrowView;
import com.parkingtycoon.views.queue.BarrierView;
import com.parkingtycoon.views.queue.QueueSignView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is responsible for providing a blueprint for any kind of Queue.
 */
public abstract class CarQueuesController extends UpdatableController {

    protected ArrayList<CarQueueModel> queues = new ArrayList<CarQueueModel>();

    public CarQueuesController() {
        CompositionRoot.getInstance().simulationController.registerUpdatable(this);
    }

    public boolean addToQueue(CarModel car) {
        if (queues.size() == 0)
            return false;

        // add car to random entrance queue
        Collections.shuffle(queues);
        for (CarQueueModel q : queues) {

            if (q.carTypes.contains(car.carType)
                    && q.cars.size() <= Random.randomInt(q.maxQueueLength / 2, q.maxQueueLength)
                    && sendCarToQueue(q, car)
                    && q.cars.add(car)) {

                car.waitingInQueue = true;
                car.firstInQueue = false;
                car.queue = q;
                return true;
            }
        }
        return false;
    }

    private boolean sendCarToQueue(CarQueueModel queue, CarModel car) {

        int x = queue.x + CoordinateRotater.rotate(2, 3, 1, 3, queue.angle);
        int y = queue.y + CoordinateRotater.rotate(1, 3, 2, 3, queue.angle);

        PathFollowerModel.Goal goal = new PathFollowerModel.Goal(
                queue.floor, x, y,
                (int) car.position.x, (int) car.position.y
        ) {

            @Override
            public void arrived() {
                car.firstInQueue = true;
            }

            @Override
            public void failed() {
                car.firstInQueue = false;
                car.waitingInQueue = false;
                queue.removeCar(car);

                if (!addToQueue(car))
                    CompositionRoot.getInstance().carsController.sendToEndOfTheWorld(car, true);
            }
        };

        return CompositionRoot.getInstance().carsController.setGoal(car, goal);
    }

    @Override
    public void update() {
        for (CarQueueModel queue : queues) {

            for (CarModel car : queue.cars) {

                if (!car.firstInQueue)
                    continue;

                // this is the first car in the queue

                if (queue.popTimer++ >= queue.popInterval) {

                    if (nextAction(car)) {

                        // car has new action, now remove from queue
                        queue.removeCar(car);
                        car.waitingInQueue = false;
                        car.firstInQueue = false;
                        car.queue = null;

                        //reset popTimer
                        queue.popTimer = 0;

                    } else queue.popTimer -= 100;
                }
                break;
            }
        }
    }

    protected abstract boolean nextAction(CarModel car);

    protected void createViews(CarQueueModel queue, String type) {
        QueueSignView queueSign = new QueueSignView(type + queue.angle % 2);
        queueSign.show();
        queue.registerView(queueSign);
        BarrierView barrier = new BarrierView();
        barrier.show();
        queue.registerView(barrier);
        ArrowView arrow = new ArrowView();
        queue.registerView(arrow);
        arrow.show();
    }

    public void removeQueue(BuildingModel building) {
        if (building instanceof CarQueueModel)
            queues.remove(building);
    }

}
