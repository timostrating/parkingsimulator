package com.parkingtycoon.models;

/**
 * Created by Sneeuwpopsneeuw on 17-Jan-18.
 */
public class FloorModel extends BaseModel {

    public enum FloorType {
        PARKABLE,
        ROAD
    }

    private FloorType[][] floorNodes;
    private boolean isCurrentFloor;

    public FloorModel() {
        this.floorNodes = new FloorType[][]    {{FloorType.PARKABLE,    FloorType.PARKABLE, FloorType.PARKABLE},
                                                {FloorType.ROAD,        FloorType.ROAD,     FloorType.PARKABLE},
                                                {FloorType.PARKABLE,    FloorType.PARKABLE, FloorType.PARKABLE}};
    }

    public FloorType getFloorTypeAt(int x, int y) {
        return floorNodes[x][y];
    }

    public void setFloorTypeAt(int x, int y, FloorType floorType) {
        floorNodes[x][y] = floorType;
    }

    public boolean isCurrentFloor() {
        return isCurrentFloor;
    }

    public void setCurrentFloor(boolean b) {
        this.isCurrentFloor = b;
        notifyViews();
    }

    public int getWidth() {
        return floorNodes.length;
    }

    public int getHeight() {
        return floorNodes[0].length;
    }

}