package com.parkingtycoon.models;

public abstract class BuildableModel extends BaseModel {

    public final int x, y;

    public BuildableModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
