package com.parkingtycoon.models;

import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.pathfinding.NavMap;

/**
 * This Class is responsible for storing all data of a floor.
 */
public class FloorModel extends BaseModel {

    public enum FloorType {
        ROAD,
        PARKABLE,
        GRASS,
        BARRIER,
        CONCRETE
    }

    public FloorType[][] tiles = new FloorType[Game.WORLD_WIDTH][];
    public CarModel[][] parkedCars = new CarModel[Game.WORLD_WIDTH][];
    public Boolean[][] accessibleParkables = new Boolean[Game.WORLD_WIDTH][];
    public Boolean[][] reserved = new Boolean[Game.WORLD_WIDTH][];
    public int[][] waitingTime = new int[Game.WORLD_WIDTH][];
    public BuildingModel[][] buildings = new BuildingModel[Game.WORLD_WIDTH][];

    public boolean fromFlagPlaced, toFlagPlaced, stopPlacing;

    private Boolean[][] newFloorValid;
    private int[] newFloorFrom, newFloorTo;
    private FloorType newFloorType;

    private boolean transitionIn, transitionOut;

    public int transitionDirection;

    public static final float TRANSITION_DURATION = .15f;

    public NavMap carNavMap = new NavMap() {
        @Override
        public boolean open(int x, int y, boolean firstNode, boolean lastNode) {
            return Game.inWorld(x, y)
                    && (tiles[x][y] == FloorType.ROAD || ((lastNode || firstNode) && tiles[x][y] == FloorType.PARKABLE));
        }

        @Override
        public int avoidScore(int x, int y) {
            return waitingTime[x][y];
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

    public void setTile(int x, int y, FloorType floorType) {
        tiles[x][y] = floorType;
        notifyViews();
    }

    public FloorType getNewFloorType() {
        return newFloorType;
    }

    public void setNewFloorType(FloorType newFloorType) {

        if (newFloorType == null) {
            fromFlagPlaced = toFlagPlaced = stopPlacing = false;
            newFloorValid = null;
            newFloorFrom = newFloorTo = null;
        }

        this.newFloorType = newFloorType;
        notifyViews();
    }

    public int[] getNewFloorFrom() {
        return newFloorFrom;
    }

    public void setNewFloorFrom(int[] newFloorFrom) {
        this.newFloorFrom = newFloorFrom;
        notifyViews();
    }

    public int[] getNewFloorTo() {
        return newFloorTo;
    }

    public void setNewFloorTo(int[] newFloorTo) {
        this.newFloorTo = newFloorTo;
        notifyViews();
    }

    public Boolean[][] getNewFloorValid() {
        return newFloorValid;
    }

    public void setNewFloorValid(Boolean[][] newFloorValid) {
        this.newFloorValid = newFloorValid;
        notifyViews();
    }

    public boolean getTransitionIn() {
        return transitionIn;
    }

    public boolean getTransitionOut() {
        return transitionOut;
    }

    public void setTransition(boolean in, int direction) {
        this.transitionIn = in;
        this.transitionOut = !in;
        this.transitionDirection = direction;
        notifyViews();
    }


}