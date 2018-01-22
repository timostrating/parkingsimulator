package com.parkingtycoon.models;

import com.parkingtycoon.Game;

import java.util.ArrayList;

/**
 * Created by Sneeuwpopsneeuw on 17-Jan-18.
 */
public class FloorModel extends BaseModel {

    public enum FloorType {
        ROAD,
        PARKABLE,
        GRASS
    }

    private boolean isCurrentFloor = false;
    public FloorType[][] tiles = new FloorType[Game.WORLD_WIDTH][];
    public ArrayList<CarModel> cars = new ArrayList<>();
    public CarModel[][] parkedCars = new CarModel[Game.WORLD_WIDTH][];

    public boolean isCurrentFloor() {
        return isCurrentFloor;
    }

    public void setCurrentFloor(boolean currentFloor) {
        isCurrentFloor = currentFloor;
        notifyViews();
    }

}