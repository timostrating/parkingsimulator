package com.parkingtycoon.models;

import java.util.EnumSet;

public class BluePrintModel extends BaseModel {

    public String title;
    public String description;
    public String spritePath;
    public float price;
    public EnumSet<FloorModel.FloorType> floorTypes;
    public Builder builder;
    public int x, y;

    private int angle = 0;
    private boolean canBuild;

    public BluePrintModel(String title, String description, String spritePath,
                          float price, EnumSet<FloorModel.FloorType> floorTypes, Builder builder) {

        this.title = title;
        this.description = description;
        this.spritePath = spritePath;
        this.price = price;
        this.floorTypes = floorTypes;
        this.builder = builder;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
        notifyViews();
    }

    public boolean canBuild() {
        return canBuild;
    }

    public void setCanBuild(boolean canBuild) {
        this.canBuild = canBuild;
        notifyViews();
    }

    @FunctionalInterface
    public interface Builder {
        BuildableModel build(int x, int y);
    }

}
