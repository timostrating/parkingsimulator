package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.BuildingModel;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.ElevatorModel;

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

        for (CarModel car : elevator.cars)
            if (onWaitingPosition(elevator, car))
                car.brake = 1;

        if (elevator.currentlyElevating != null) {

            elevate(elevator);

        } else elevateNextCar(elevator);
    }

    private void elevateNextCar(ElevatorModel elevator) {

        if (elevator.cars.size() == 0)
            return;

        CarModel nextCar = null;

        for (CarModel car : elevator.cars)
            if ((nextCar == null || car.floor == elevator.currentFloor) && onWaitingPosition(elevator, car))
                nextCar = car;

        if (nextCar == null)
            return;

        elevator.currentlyElevating = nextCar;
        elevator.timeLeft = Math.abs(nextCar.floor - nextCar.goal.floor) * 20 + 80;

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

        } else if (elevating.getCurrentPath() != null) {

            if (!openDoors(elevator))
                elevating.brake = 1;

        } else if (elevating.floor != elevating.goal.floor) {

            if (closeDoors(elevator)) {

                elevating.floor = elevating.goal.floor;
                elevating.setOnActiveFloor(
                        elevating.floor == CompositionRoot.getInstance().floorsController.getCurrentFloor()
                );
                elevator.currentFloor = elevating.goal.floor;

            }

        } else if (--elevator.timeLeft <= 0 && openDoors(elevator)) {

            elevating.elevationDone = true;
            elevator.currentlyElevating = null;
            elevator.cars.remove(elevating);

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

}