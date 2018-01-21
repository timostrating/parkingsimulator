package com.parkingtycoon.models;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by Sneeuwpopsneeuw on 17-Jan-18.
 */
public class FloorModel extends BaseModel {
    private TiledMap tiledMap;
    private TiledMapTileLayer layer;



    public FloorModel() {
        tiledMap = new TmxMapLoader().load("maps/default.tmx");;
        layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public int getWidth() {
        return layer.getWidth();
    }

    public int getHeight() {
        return layer.getHeight();
    }
}