package com.parkingtycoon.models;

import com.parkingtycoon.helpers.interfaces.FloorDependable;

public abstract class BuildableModel extends BaseModel implements FloorDependable {

    public final int x, y, angle, floor;
    public final boolean onAllFloors;

    private boolean onActiveFloor;
    private boolean demolished;

    public BuildableModel(int x, int y, int angle, int floor) {
        this(x, y, angle, floor, false);
    }

    public BuildableModel(int x, int y, int angle, int floor, boolean onAllFloors) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.floor = floor;
        this.onAllFloors = onAllFloors;
    }

    @Override
    public boolean isOnActiveFloor() {
        return onAllFloors || onActiveFloor;
    }

    @Override
    public void setOnActiveFloor(boolean onActiveFloor) {
        this.onActiveFloor = onActiveFloor;
        notifyViews();
    }

    public boolean isDemolished() {
        return demolished;
    }

    public void setDemolished(boolean demolished) {
        this.demolished = demolished;
        notifyViews();
    }
}
