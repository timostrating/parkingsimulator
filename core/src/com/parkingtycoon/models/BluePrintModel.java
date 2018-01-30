package com.parkingtycoon.models;

import java.util.EnumSet;

public class BluePrintModel extends BaseModel {

    public String title;
    public String description;
    public String spritePath;
    public float spriteOriginX, spriteOriginY;
    public float price;
    public EnumSet<FloorModel.FloorType> canBuildOn;
    public FloorModel.FloorType[][] tiles;
    public Builder builder;
    public int x, y;
    public boolean[][] validTiles;

    private int angle = 0;
    private boolean canBuild, active;

    public BluePrintModel(String title, String description, String spritePath, float spriteOriginX, float spriteOriginY,
                          float price, EnumSet<FloorModel.FloorType> canBuildOn, FloorModel.FloorType[][] tiles, Builder builder) {

        this.title = title;
        this.description = description;
        this.spritePath = spritePath;
        this.spriteOriginX = spriteOriginX;
        this.spriteOriginY = spriteOriginY;
        this.price = price;
        this.canBuildOn = canBuildOn;
        this.tiles = tiles;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        notifyViews();
    }

    @FunctionalInterface
    public interface Builder {
        BuildableModel build(int x, int y, int angle, int floor);
    }

}
