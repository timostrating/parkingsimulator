package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.FloorModel;

/**
 * This Class is responsible for showing the floor as a map.
 */
public class FloorsView extends BaseView {

    private IsometricTiledMapRenderer renderer;
    private TiledMap tiledMap;


    public FloorsView() {
        super();
        tiledMap = new TmxMapLoader().load("maps/default.tmx");
        renderer = new IsometricTiledMapRenderer(tiledMap, 1 / 16f);
    }

    @Override
    public void updateView(BaseModel model) {

        if (model instanceof FloorModel) {
            FloorModel floor = (FloorModel) model;
            if (floor.isCurrentFloor())
                setTiles(floor);
        }

    }

    @Override
    public void preRender() {
        OrthographicCamera camera = CompositionRoot.getInstance().renderController.getMainCamera();
        moveCamera(camera);
        renderMap(camera);
    }

    @Override
    public float renderIndex() {
        return 0;
    }

    private void moveCamera(OrthographicCamera camera) {  // TODO: this is not the responsibility of this class
        float x = Gdx.input.getX() / (float) Gdx.graphics.getWidth();
        float y = Gdx.input.getY() / (float) Gdx.graphics.getHeight();

        if (x < .02f) camera.position.x -= .1f * camera.zoom;
        else if (x > .98f) camera.position.x += .1f * camera.zoom;

        if (y < .02f) camera.position.y += .1f * camera.zoom;
        else if (y > .98f) camera.position.y -= .1f * camera.zoom;

        camera.update();
    }

    private void renderMap(OrthographicCamera camera) {
        // before all other sprites are rendered -> render IsometricTiledMap
        renderer.setView(camera);
        renderer.render();
    }

    private void setTiles(FloorModel floor) {

        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        TiledMapTileSets tileSets = tiledMap.getTileSets();

        for (int x = 0; x < Game.WORLD_WIDTH; x++) {
            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                FloorModel.FloorType floorType = floor.tiles[x][y];
                TiledMapTile tile = null;

                switch (floorType) {
                    case GRASS: tile = tileSets.getTile(13); break;
                    case ROAD:  tile = tileSets.getTile(78); break;
                    case PARKABLE: tile = tileSets.getTile(174); break;
                    default: tile = tileSets.getTile(17);

                }

                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null) {
                    cell = new TiledMapTileLayer.Cell();
                    layer.setCell(x, y, cell);
                }

                cell.setTile(tile);
            }
        }
    }

}
