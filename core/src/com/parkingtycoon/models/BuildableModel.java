package com.parkingtycoon.models;

public abstract class BuildableModel extends BaseModel {

    public final int x, y, angle, floor;

    public BuildableModel(int x, int y, int angle, int floor) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.floor = floor;
    }

}
