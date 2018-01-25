package com.parkingtycoon.models;

import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.pathfinding.NavMap;

import java.util.ArrayList;

/**
 * This Class is responsible for storing all data of a floor.
 */
public class FloorModel extends BaseModel {

    public enum FloorType {
        ROAD,
        PARKABLE,
        GRASS
    }

    public FloorType[][] tiles = new FloorType[Game.WORLD_WIDTH][];
    public ArrayList<CarModel> cars = new ArrayList<>();
    public CarModel[][] parkedCars = new CarModel[Game.WORLD_WIDTH][];

    public NavMap carNavMap = new NavMap() {
        @Override
        public boolean open(int x, int y, boolean firstNode, boolean lastNode) {
            return tiles[x][y] == FloorType.ROAD || ((lastNode || firstNode) && tiles[x][y] == FloorType.PARKABLE);
        }
    };

    private boolean isCurrentFloor = false;

    public boolean isCurrentFloor() {
        return isCurrentFloor;
    }

    public void setCurrentFloor(boolean currentFloor) {
        isCurrentFloor = currentFloor;
        notifyViews();
    }

}