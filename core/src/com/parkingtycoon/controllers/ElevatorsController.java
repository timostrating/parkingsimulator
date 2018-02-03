package com.parkingtycoon.controllers;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.BuildingModel;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.ElevatorModel;
import com.parkingtycoon.views.elevator.ElevatorBackgroundView;
import com.parkingtycoon.views.elevator.ElevatorView;

import java.util.ArrayList;

public class ElevatorsController extends UpdateableController {

    public ArrayList<ElevatorModel> elevators = new ArrayList<>();

    @Override
    public void update() {

        for (ElevatorModel elevator : elevators)
            updateElevator(elevator);

    }

    public ElevatorModel createElevator(int x, int y, int angle) {
        ElevatorModel elevator = new ElevatorModel(x, y, angle);
        elevators.add(elevator);
        ElevatorView elevatorView = new ElevatorView();
        elevatorView.show();
        elevator.registerView(elevatorView);
        ElevatorBackgroundView backView = new ElevatorBackgroundView();
        backView.show();
        elevator.registerView(backView);
        return elevator;
    }

    public void removeElevator(BuildingModel building) {
        if (building instanceof ElevatorModel) {

            ElevatorModel elevator = (ElevatorModel) building;
            elevators.remove(elevator);

            if (elevator.currentlyElevating != null) {          // despawn the car that is currently inside the elevator
                elevator.currentlyElevating.setDisappeared();
                CompositionRoot.getInstance().carsController.clearParkingSpace(elevator.currentlyElevating);
            }
        }
    }

    private void updateElevator(ElevatorModel elevator) {

        for (CarModel car : elevator.cars) {
            if (onWaitingPosition(elevator, car))
                car.brake = 1;
        }

        if (elevator.currentlyElevating != null) {

            elevate(elevator);

        } else {
            closeDoors(elevator);
            elevateNextCar(elevator);
        }
    }

    private void elevateNextCar(ElevatorModel elevator) {

        if (elevator.cars.size() == 0)
            return;     // no cars to elevate

        CarModel nextCar = null;

        for (CarModel car : elevator.cars)
            if ((nextCar == null || car.floor == elevator.currentFloor) && onWaitingPosition(elevator, car))
                nextCar = car;

        if (nextCar == null)
            return;

        if (nextCar.goal == null || nextCar.goal.floor == nextCar.floor) {
            elevator.cars.remove(nextCar);
            return;
        }

        elevator.currentlyElevating = nextCar;
        elevator.timeLeft = Math.abs(nextCar.floor - nextCar.goal.floor) * 20 + 10;
        nextCar.alwaysPrior = true;

    }

    private boolean onWaitingPosition(ElevatorModel elevator, CarModel car) {
        int waitingPlaceX = elevator.x + (elevator.angle == 0 ? 2 : 1);
        int waitingPlaceY = elevator.y + (elevator.angle == 0 ? 1 : 2);

        return car.waitingOn == null
                && (int) car.position.x == waitingPlaceX
                && (int) car.position.y == waitingPlaceY
                && car != elevator.currentlyElevating;
    }

    private void elevate(ElevatorModel elevator) {

        CarModel elevating = elevator.currentlyElevating;
        if (elevator.currentFloor != elevating.floor) {

            elevating.brake = 1;
            if (closeDoors(elevator))
                elevator.currentFloor = elevating.floor;

        } else if (elevating.goal != null && elevating.getCurrentPath() != null && elevating.floor != elevating.goal.floor) {

            if (!openDoors(elevator))
                elevating.brake = 1;

        } else if (elevating.goal != null && elevating.floor != elevating.goal.floor) {

            if (closeDoors(elevator)) {

                elevating.floor = elevating.goal.floor;
                elevating.setOnActiveFloor(
                        elevating.floor == CompositionRoot.getInstance().floorsController.getCurrentFloor()
                );
                elevator.currentFloor = elevating.goal.floor;

            }

        } else if (--elevator.timeLeft <= 0 && openDoors(elevator)) {

            elevating.elevationDone = true;

            if (elevating.goal == null) {
                elevating.setDisappeared();
                CompositionRoot.getInstance().carsController.clearParkingSpace(elevating);
            }

            if (carLeftElevator(elevator) || elevating.isDisappeared()) {
                elevating.alwaysPrior = false;
                elevator.currentlyElevating = null;
                elevator.cars.remove(elevating);
            }
        }
    }

    private boolean openDoors(ElevatorModel elevator) {
        elevator.setDoorsTimer(Math.min(1, elevator.getDoorsTimer() + .05f));
        return elevator.getDoorsTimer() == 1;
    }

    private boolean closeDoors(ElevatorModel elevator) {
        elevator.setDoorsTimer(Math.max(0, elevator.getDoorsTimer() - .05f));
        return elevator.getDoorsTimer() == 0;
    }

    private boolean carLeftElevator(ElevatorModel elevator) {

        float distance = new Vector2(elevator.x + 1.5f, elevator.y + 1.5f).sub(
                elevator.currentlyElevating.position.x,
                elevator.currentlyElevating.position.y
        ).len();
        return distance > 1.5f;
    }

}