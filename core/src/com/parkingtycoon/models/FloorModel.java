package com.parkingtycoon.models;

import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.pathfinding.NavMap;

/**
 * This Class is responsible for storing all data of a floor.
 * Such as all tiles, parked cars, reservations, buildings and more.
 */
public class FloorModel extends BaseModel {

    /**
     * A floor consists of a lot of tiles.
     * Each tile can be a different type.
     */
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
    public int[][] waitingTime = new int[Game.WORLD_WIDTH][];
    public BuildingModel[][] buildings = new BuildingModel[Game.WORLD_WIDTH][];

    public boolean fromFlagPlaced, toFlagPlaced, stopPlacing;

    private Boolean[][] reserved = new Boolean[Game.WORLD_WIDTH][];
    private Boolean[][] newFloorValid;
    private int[] newFloorFrom, newFloorTo;
    private FloorType newFloorType;

    private boolean transitionIn, transitionOut;

    public int transitionDirection;

    public static final float TRANSITION_DURATION = .15f;

    /**
     * This Navigation map is used by the PathFinder to find a path for a car on this floor
     */
    public NavMap carNavMap = new NavMap() {
        /**
         * This method returns whether cars are allowed to drive here
         *
         * @param x         The x-coordinate
         * @param y         The y-coordinate
         * @param firstNode Whether this is the first node of the path
         * @param lastNode  Whether this is te last node of the path
         * @return
         */
        @Override
        public boolean open(int x, int y, boolean firstNode, boolean lastNode) {
            return Game.inWorld(x, y)
                    && (tiles[x][y] == FloorType.ROAD || ((lastNode || firstNode) && tiles[x][y] == FloorType.PARKABLE));
        }

        /**
         * This method returns a high score for busy areas, so that the pathFinder will avoid this area.
         *
         * @param x X-coordinate
         * @param y Y-coordinate
         * @return  The avoid score
         */
        @Override
        public int avoidScore(int x, int y) {
            return waitingTime[x][y];
        }
    };

    private boolean isCurrentFloor = false;

    /**
     * Returns whether this is the currently active floor
     * @return Whether this is the currently active floor
     */
    public boolean isCurrentFloor() {
        return isCurrentFloor;
    }

    /**
     * Setter for isCurrentFloor
     * @param currentFloor Is this the currently active floor?
     */
    public void setCurrentFloor(boolean currentFloor) {
        isCurrentFloor = currentFloor;
        notifyViews();
    }

    /**
     * Checks if a PARKABLE-tile is reserved by a car
     * @param x X-position of PARKABLE
     * @param y Y-position of PARKABLE
     * @return  Whether this place is reserved by a car
     */
    public boolean isReserved(int x, int y) {
        if (reserved[x] == null)
            return false;

        return Boolean.TRUE.equals(reserved[x][y]);
    }

    /**
     * Sets whether a PARKABLE-tile is reserved by a car
     * @param x X-position of PARKABLE
     * @param y Y-position of PARKABLE
     * @return  Whether this place is reserved by a car
     */
    public void setReserved(int x, int y, boolean reserved) {
        if (this.reserved[x] == null)
            this.reserved[x] = new Boolean[Game.WORLD_HEIGHT];

        this.reserved[x][y] = reserved;
        notifyViews();
    }

    /**
     * Sets a tile on the map of this floor and notifies the FloorsView
     * @param x         X-coordinate
     * @param y         Y-coordinate
     * @param floorType FloorType for this tile
     */
    public void setTile(int x, int y, FloorType floorType) {
        tiles[x][y] = floorType;
        notifyViews();
    }

    /**
     * Returns the type of the floor that is possibly going to be placed
     * @return The type of the floor that is possibly going to be placed
     */
    public FloorType getNewFloorType() {
        return newFloorType;
    }

    /**
     * Setter for the type of floor that is possibly going to be placed
     * @param newFloorType The type of floor that is possibly going to be placed
     */
    public void setNewFloorType(FloorType newFloorType) {

        if (newFloorType == null) {
            fromFlagPlaced = toFlagPlaced = stopPlacing = false;
            newFloorValid = null;
            newFloorFrom = newFloorTo = null;
        }

        this.newFloorType = newFloorType;
        notifyViews();
    }

    /**
     * Get the coordinates from where a new floor is going to be built
     * @return The coordinates from where a new floor is going to be built
     */
    public int[] getNewFloorFrom() {
        return newFloorFrom;
    }

    /**
     * Set the coordinates from where a new floor is going to be built
     * @param newFloorFrom The coordinates from where a new floor is going to be built
     */
    public void setNewFloorFrom(int[] newFloorFrom) {
        this.newFloorFrom = newFloorFrom;
        notifyViews();
    }

    /**
     * Get the coordinates to where a new floor is going to be built
     * @return The coordinates to where a new floor is going to be built
     */
    public int[] getNewFloorTo() {
        return newFloorTo;
    }

    /**
     * Set the coordinates to where a new floor is going to be built
     * @param newFloorTo The coordinates to where a new floor is going to be built
     */
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