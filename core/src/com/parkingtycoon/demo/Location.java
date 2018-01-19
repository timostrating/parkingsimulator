package com.parkingtycoon.demo;

public class Location {

    private int floor;
    private int row;
    private int place;

    /**
     * Constructor for objects of class demo.Location
     */
    public Location(int floor, int row, int place) {
        this.floor = floor;
        this.row = row;
        this.place = place;
    }

    /* getters */
    public int getFloor() {
        return floor;
    }
    public int getRow() {
        return row;
    }
    public int getPlace() {
        return place;
    }

}