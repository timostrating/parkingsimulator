package com.parkingtycoon.models;

import java.util.EnumSet;

public class BluePrintModel extends BaseModel {

    public String title;
    public String description;
    public float price;
    public EnumSet<FloorModel.FloorType> floorTypes;
    public Builder builder;

    public BluePrintModel(String title, String description, float price, EnumSet<FloorModel.FloorType> floorTypes, Builder builder) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.floorTypes = floorTypes;
        this.builder = builder;
    }

    @FunctionalInterface
    public interface Builder {
        BuildableModel build(int x, int y);
    }

}
