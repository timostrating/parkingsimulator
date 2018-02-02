package com.parkingtycoon.models;

import java.util.EnumSet;

public class BluePrintModel extends BaseModel {

    public final String title;
    public final String description;
    public final String spritePath;
    public final float spriteOriginX, spriteOriginY;
    public final float price;
    public final EnumSet<FloorModel.FloorType> canBuildOn;
    public final FloorModel.FloorType[][] tiles;
    public final Builder builder;
    public final boolean buildOnAllFloors;
    public final int[] allowedAngles;
    public int x, y;
    public boolean[][] validTiles;

    private int angle;
    private boolean canBuild, active;

    public BluePrintModel(String title, String description, String spritePath, float spriteOriginX, float spriteOriginY,
                          float price, EnumSet<FloorModel.FloorType> canBuildOn, FloorModel.FloorType[][] tiles,
                          Builder builder, boolean buildOnAllFloors, int... allowedAngles) {

        this.title = title;
        this.description = description;
        this.spritePath = spritePath;
        this.spriteOriginX = spriteOriginX;
        this.spriteOriginY = spriteOriginY;
        this.price = price;
        this.canBuildOn = canBuildOn;
        this.tiles = tiles;
        this.builder = builder;
        this.buildOnAllFloors = buildOnAllFloors;
        this.allowedAngles = allowedAngles.length == 0 ? new int[] {0, 1, 2, 3} : allowedAngles;
        angle = this.allowedAngles[0];
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
