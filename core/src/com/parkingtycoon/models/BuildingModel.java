package com.parkingtycoon.models;

import com.parkingtycoon.helpers.interfaces.FloorDependable;

/**
 * A BuildingModel stores information about a building like position and angle
 */
public abstract class BuildingModel extends BaseModel implements FloorDependable {

    public final int x, y, angle, floor;
    public final boolean onAllFloors;

    public BluePrintModel bluePrint;

    private boolean demolished, toBeDemolished;
    private boolean onActiveFloor;

    public BuildingModel(int x, int y, int angle, int floor) {
        this(x, y, angle, floor, false);
    }

    /**
     * This constructor will initialize all final variables
     *
     * @param x           X-position of the building
     * @param y           Y-position of the building
     * @param angle       Angle of the building
     * @param floor       Floor that the building is standing on
     * @param onAllFloors Should the building appear on each floor?
     */
    public BuildingModel(int x, int y, int angle, int floor, boolean onAllFloors) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.floor = floor;
        this.onAllFloors = onAllFloors;
    }

    /**
     * Returns true if the building is on the currently active floor
     * @return true if the building is on the currently active floor
     */
    @Override
    public boolean isOnActiveFloor() {
        return onAllFloors || onActiveFloor;
    }

    /**
     * Setter for onActiveFloor
     * @param onActiveFloor Is the building on the currently active floor?
     */
    @Override
    public void setOnActiveFloor(boolean onActiveFloor) {
        this.onActiveFloor = onActiveFloor;
        notifyViews();
    }

    /**
     * Is the building demolished?
     * @return Whether the building is demolished
     */
    public boolean isDemolished() {
        return demolished;
    }

    /**
     * Setter for demolished, will notify building views.
     * @param demolished Is the building demolished?
     */
    public void setDemolished(boolean demolished) {
        this.demolished = demolished;
        notifyViews();
    }

    /**
     * Does the player hover on this building while in demolishMode?
     * @return Is this building to be demolished?
     */
    public boolean isToBeDemolished() {
        return toBeDemolished;
    }

    /**
     * Setter for toBeDemolished
     * @param toBeDemolished Is this building to be demolished?
     */
    public void setToBeDemolished(boolean toBeDemolished) {
        this.toBeDemolished = toBeDemolished;
        notifyViews();
    }

}
