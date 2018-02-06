package com.parkingtycoon.models;

import java.util.EnumSet;

/**
 * A BluePrint contains all the information that can be used to build a building
 */
public class BluePrintModel extends BaseModel {

    public final String title;
    public final String description;
    public final String uiImagePath;
    public final String spritePath;
    public final float spriteOriginX, spriteOriginY;
    public final float price;
    public final EnumSet<FloorModel.FloorType> canBuildOn;
    public final FloorModel.FloorType[][] tiles;
    public final Builder builder;
    public final boolean buildOnAllFloors;
    public final int[] allowedAngles;
    public final Demolisher demolisher;
    public int x, y;
    public boolean[][] validTiles;

    private int angle;
    private boolean canBuild, active;

    /**
     * This constructor will create a new BluePrint
     *
     * @param title            Title of the building
     * @param description      Description of the building
     * @param uiImagePath      Path to the image that is shown in the Build/Catalogue-Window
     * @param spritePath       Path to the ghost-sprite that is shown while choosing a position to build
     * @param spriteOriginX    X-coordinate of the center of the sprite
     * @param spriteOriginY    Y-coordinate of the center of the sprite
     * @param price            Price of this building
     * @param canBuildOn       FloorTypes that this building is allowed to stand on (CONCRETE for example)
     * @param tiles            FloorTypes that will be placed underneath the building
     * @param builder          The builder should build the building
     * @param buildOnAllFloors If true, the building will appear on each floor. Like elevators
     * @param demolisher       Action that is performed when demolishing
     * @param allowedAngles    In which angles is this building allowed to be built? (min 0, max 3)
     */
    public BluePrintModel(String title, String description, String uiImagePath, String spritePath, float spriteOriginX, float spriteOriginY,
                          float price, EnumSet<FloorModel.FloorType> canBuildOn, FloorModel.FloorType[][] tiles,
                          Builder builder, boolean buildOnAllFloors, Demolisher demolisher, int... allowedAngles) {

        this.title = title;
        this.description = description;
        this.uiImagePath = uiImagePath;
        this.spritePath = spritePath;
        this.spriteOriginX = spriteOriginX;
        this.spriteOriginY = spriteOriginY;
        this.price = price;
        this.canBuildOn = canBuildOn;
        this.tiles = tiles;
        this.builder = builder;
        this.buildOnAllFloors = buildOnAllFloors;
        this.allowedAngles = allowedAngles.length == 0 ? new int[] {0, 1, 2, 3} : allowedAngles;
        this.demolisher = demolisher;
        angle = this.allowedAngles[0];
    }

    public int getAngle() {
        return angle;
    }

    /**
     * This will update the angle of the bluePrint and it will notify the BluePrintView
     * @param angle New angle
     */
    public void setAngle(int angle) {
        this.angle = angle;
        notifyViews();
    }

    public boolean canBuild() {
        return canBuild;
    }

    /**
     * This variable stores whether this building can be build on the current position
     *
     * @param canBuild Whether this building can be build on the current position
     */
    public void setCanBuild(boolean canBuild) {
        this.canBuild = canBuild;
        notifyViews();
    }

    /**
     * Is the bluePrint active?
     * @return Whether the bluePrint is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * When active, the bluePrintView should be showed
     * @param active Is bluePrint active
     */
    public void setActive(boolean active) {
        this.active = active;
        notifyViews();
    }

    /**
     * Builders should create a Building on a given position
     */
    @FunctionalInterface
    public interface Builder {
        BuildingModel build(int x, int y, int angle, int floor);
    }

    /**
     * Demolishers can take action when a building is removed
     */
    @FunctionalInterface
    public interface Demolisher {
        void demolish(BuildingModel building);
    }

}
