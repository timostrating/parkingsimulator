package com.parkingtycoon.models;

import com.parkingtycoon.helpers.interfaces.FloorDependable;

public abstract class BuildableModel extends BaseModel implements FloorDependable {

    public final int x, y, angle, floor;

    private boolean onActiveFloor;

    public BuildableModel(int x, int y, int angle, int floor) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.floor = floor;
    }

    @Override
    public boolean isOnActiveFloor() {
        return onActiveFloor;
    }

    @Override
    public void setOnActiveFloor(boolean onActiveFloor) {
        this.onActiveFloor = onActiveFloor;
        notifyViews();
    }
}
