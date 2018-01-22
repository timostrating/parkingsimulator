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

    public boolean isCurrentFloor() {
        return isCurrentFloor;
    }

    public void setCurrentFloor(boolean currentFloor) {
        isCurrentFloor = currentFloor;
        notifyViews();
    }

    public FloorType[][] tiles = new FloorType[Game.WORLD_WIDTH][];
    public ArrayList<CarModel> cars = new ArrayList<>();
    public CarModel[][] parkedCars = new CarModel[Game.WORLD_WIDTH][];

    private boolean isCurrentFloor = false;

    public FloorModel() {
        for (int x = 0; x < Game.WORLD_WIDTH; x++) {

            tiles[x] = new FloorType[Game.WORLD_HEIGHT];

            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                tiles[x][y] = FloorType.GRASS;

            }
        }
    }

}