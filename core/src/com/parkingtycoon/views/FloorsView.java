package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.parkingtycoon.Game;
import com.parkingtycoon.models.BaseModel;

public class FloorsView extends BaseView {

    private IsometricTiledMapRenderer renderer;
    private TiledMap tiledMap;

    public FloorsView() {
        super();
        tiledMap = new TmxMapLoader().load("maps/default.tmx");
        renderer = new IsometricTiledMapRenderer(tiledMap, 1 / 16f);

        Game game = Game.getInstance();
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        game.worldHeight = layer.getHeight();
        game.worldWidth = layer.getWidth();
    }

    @Override
    public void updateView(BaseModel model) {

    }

    @Override
    public void preRender() {
        OrthographicCamera camera = Game.getInstance().getWorldCamera();
        moveCamera(camera);
        renderMap(camera);
    }

    @Override
    public float renderIndex() {
        return 0;
    }

    private void moveCamera(OrthographicCamera camera) {

        float x = Gdx.input.getX() / (float) Gdx.graphics.getWidth();
        float y = Gdx.input.getY() / (float) Gdx.graphics.getHeight();

        if (x < .02f) camera.position.x -= .1f * camera.zoom;
        else if (x > .98f) camera.position.x += .1f * camera.zoom;

        if (y < .03f) camera.position.y += .1f * camera.zoom;
        else if (y > .97f) camera.position.y -= .1f * camera.zoom;

        camera.update();
    }

    private void renderMap(OrthographicCamera camera) {
        // before all other sprites are rendered -> render IsometricTiledMap
        renderer.setView(camera);
        renderer.render();
    }
}
