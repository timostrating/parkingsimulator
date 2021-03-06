package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.controllers.FloorsController;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.BluePrintModel;
import com.parkingtycoon.models.FloorModel;

import static com.parkingtycoon.models.FloorModel.TRANSITION_DURATION;

/**
 * This Class is responsible for rendering all floorTiles, and transition effects between floors
 */
public final class FloorsView extends BaseView {

    private IsometricTiledMapRenderer renderer;
    private TiledMap tiledMap;

    private boolean showNoBuildZone;
    private Color noBuildZoneColor = new Color(.1f, 0, 0, 0);

    private Boolean[][] validTiles;
    private Texture invalidTileTexture = SpriteView.TEXTURES.get("sprites/blockedBluePrintTile.png");

    private boolean transitionIn, transitionOut;
    private float transitionTimer, transitionDistance;
    private int transitionDirection;
    private Color fadeColor = new Color();

    /**
     * The constructor will initialize the Isometric TiledMap
     */
    public FloorsView() {
        tiledMap = new TmxMapLoader().load("maps/default.tmx");
        renderer = new IsometricTiledMapRenderer(tiledMap, 1 / 32f);
        tiledMap.getLayers().get(0).setOffsetY(32);

        show();
    }

    /**
     * The updateView method will be called by all the floors
     * It will gather information that is needed to render the map correctly.
     * @param model A floorModel
     */
    @Override
    public void updateView(BaseModel model) {

        if (!showNoBuildZone)
            noBuildZoneColor.a = 0;

        if (model instanceof FloorModel) {
            FloorModel floor = (FloorModel) model;
            if (!floor.isCurrentFloor())
                return;

            setTiles(floor);
            showNoBuildZone = floor.getNewFloorType() != null;
            validTiles = floor.getNewFloorValid();

            if (transitionIn != floor.getTransitionIn() || transitionOut != floor.getTransitionOut()) {

                transitionIn = floor.getTransitionIn();
                transitionOut = floor.getTransitionOut();
                transitionDirection = floor.transitionDirection;
                transitionTimer = 0;

                OrthographicCamera camera = CompositionRoot.getInstance().renderController.getMainCamera();

                if (transitionIn) {
                    camera.position.y += transitionDistance * 2 * transitionDirection;
                    camera.update();
                } else if (transitionOut)
                    transitionDistance = camera.zoom;

            }


        } else if (model instanceof BluePrintModel) {

            showNoBuildZone = ((BluePrintModel) model).isActive();

        }

    }

    /**
     * The render method will render some additional things
     * @param batch
     */
    @Override
    public void render(SpriteBatch batch) {
        if (validTiles == null)
            return;

        // keep reference to array because this.validTiles might be set to null by other thread
        Boolean[][] validTiles = this.validTiles;

        for (int x = 0; x < Game.WORLD_WIDTH; x++) {
            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {
                if (validTiles[x] == null)
                    continue;

                if (Boolean.FALSE.equals(validTiles[x][y])) {

                    Vector2 isoPosition = IsometricConverter.normalToIsometric(new Vector2(x, y));

                    batch.draw(
                            invalidTileTexture,
                            isoPosition.x - 2, isoPosition.y - 2,
                            4, 2
                    );

                }
            }
        }
    }

    /**
     * Before other views are rendered, the camera is moved and the map will be rendered correctly.
     */
    @Override
    public void preRender() {
        OrthographicCamera camera = CompositionRoot.getInstance().renderController.getMainCamera();
        moveCamera(camera);
        renderMap(camera);
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        if (showNoBuildZone)
            renderNoBuildZone(shapeRenderer);

        if (transitionIn || transitionOut)
            renderFade(shapeRenderer, transitionTimer, transitionIn);
    }

    @Override
    public float renderIndex() {
        return 9999;
    }

    /**
     * This method will move the camera if the cursor is near an edge of the screen
     * @param camera The camera to move
     */
    private void moveCamera(OrthographicCamera camera) {
        float x = Gdx.input.getX() / (float) Gdx.graphics.getWidth();
        float y = Gdx.input.getY() / (float) Gdx.graphics.getHeight();

        if (x < .02f && camera.position.x > 0)
            camera.position.x -= .1f * camera.zoom;
        else if (x > .98f && camera.position.x < 400)
            camera.position.x += .1f * camera.zoom;

        if (y < .02f && camera.position.y < 100)
            camera.position.y += .1f * camera.zoom;
        else if (y > .98f && camera.position.y > -100)
            camera.position.y -= .1f * camera.zoom;

        if ((transitionIn || transitionOut) && transitionTimer <= TRANSITION_DURATION) {
            float deltaTime = Gdx.graphics.getDeltaTime();

            if (transitionTimer + deltaTime > TRANSITION_DURATION)
                deltaTime = TRANSITION_DURATION - transitionTimer;

            transitionTimer += deltaTime;
            if (transitionTimer < TRANSITION_DURATION)
                camera.position.y += (deltaTime / TRANSITION_DURATION) * -transitionDistance * transitionDirection;
        }

        camera.update();
    }

    /**
     * This method will actually render the map.
     * @param camera Camera that is used
     */
    private void renderMap(OrthographicCamera camera) {
        // before all other sprites are rendered -> render IsometricTiledMap
        renderer.setView(camera);
        renderer.render();
    }

    /**
     * This method will update the tiles on the TiledMap according to the given FloorModel
     * @param floor FloorModel that has to be rendered
     */
    private void setTiles(FloorModel floor) {

        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        TiledMapTileSets tileSets = tiledMap.getTileSets();

        for (int x = 0; x < Game.WORLD_WIDTH; x++) {
            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                TiledMapTile tile = null;

                FloorModel.FloorType type = getFloorType(floor, x, y);
                if (type != null) {
                    switch (type) {
                        case GRASS:
                            tile = tileSets.getTile(9);
                            break;
                        case ROAD:
                            boolean alongX = getFloorType(floor, x - 1, y) == FloorModel.FloorType.ROAD
                                    && getFloorType(floor, x + 1, y) == FloorModel.FloorType.ROAD;

                            boolean alongY = getFloorType(floor, x, y - 1) == FloorModel.FloorType.ROAD
                                    && getFloorType(floor, x, y + 1) == FloorModel.FloorType.ROAD;

                            if (alongX == alongY)
                                tile = tileSets.getTile(3);
                            else
                                tile = tileSets.getTile(alongX ? 2 : 1);

                            break;
                        case PARKABLE:
                            tile = tileSets.getTile(floor.isReserved(x, y) ? 11 : 4);
                            break;
                        case BARRIER:
                            tile = tileSets.getTile(3);
                            break;
                        case CONCRETE:
                            tile = tileSets.getTile(5);
                            break;
                        default:
                            tile = tileSets.getTile(6);
                    }
                }

                TiledMapTileLayer.Cell cell = layer.getCell(x, Game.WORLD_HEIGHT - y - 1);
                if (cell == null) {
                    cell = new TiledMapTileLayer.Cell();
                    layer.setCell(x, Game.WORLD_HEIGHT - y - 1, cell);
                }

                cell.setTile(tile);
            }
        }
    }

    private FloorModel.FloorType getFloorType(FloorModel floor, int x, int y) {
        if (floor.getNewFloorFrom() != null) {
            int[] from = floor.getNewFloorFrom();
            int[] to = floor.getNewFloorTo();
            if (to == null)
                to = from;

            boolean inNewArea = x >= Math.min(from[0], to[0])
                    && x <= Math.max(from[0], to[0])
                    && y >= Math.min(from[1], to[1])
                    && y <= Math.max(from[1], to[1]);

            if (inNewArea)
                return floor.getNewFloorType();
        }

        return x >= 0 && x < Game.WORLD_WIDTH && y >= 0 && y < Game.WORLD_HEIGHT ? floor.tiles[x][y] : null;
    }

    /**
     * In debug mode, a grid will be drawn
     * @param shapeRenderer ShapeRenderer used to render
     */
    @Override
    public void debugRender(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(Color.DARK_GRAY);
        for (int x = 0; x < Game.WORLD_WIDTH; x++)
            shapeRenderer.line(x, 0, x, Game.WORLD_HEIGHT);

        for (int y = 0; y < Game.WORLD_HEIGHT; y++)
            shapeRenderer.line(0, y, Game.WORLD_WIDTH, y);
    }

    /**
     * This method will darken the zone where the player is not allowed to build
     * @param shapeRenderer ShapeRenderer used to render
     */
    private void renderNoBuildZone(ShapeRenderer shapeRenderer) {

        noBuildZoneColor.a = Math.min(.5f, noBuildZoneColor.a + .04f);
        shapeRenderer.setColor(noBuildZoneColor);

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                shapeRenderer.triangle(
                        x == 0 ? Game.WORLD_WIDTH * 4 : 0, 0,
                        (x == 0 ? Game.WORLD_WIDTH * 4 : 0) + FloorsController.BUILD_MARGIN * (x == 0 ? -4 : 4), 0,
                        Game.WORLD_WIDTH * 2, Game.WORLD_HEIGHT * (y == 1 ? -1 : 1)
                );
                shapeRenderer.triangle(
                        (x == 0 ? Game.WORLD_WIDTH * 4 : 0) + FloorsController.BUILD_MARGIN * (x == 0 ? -4 : 4), 0,
                        Game.WORLD_WIDTH * 2, (Game.WORLD_HEIGHT - FloorsController.BUILD_MARGIN * 2) * (y == 1 ? -1 : 1),
                        Game.WORLD_WIDTH * 2, Game.WORLD_HEIGHT * (y == 1 ? -1 : 1)
                );
            }
        }
    }

    /**
     * This method is used for the fade effect
     * @param shapeRenderer   ShapeRenderer used to render
     * @param transitionTimer Timer of the transition
     * @param in              Whether to fade in or out
     */
    private void renderFade(ShapeRenderer shapeRenderer, float transitionTimer, boolean in) {
        fadeColor.a = in ? TRANSITION_DURATION - transitionTimer : transitionTimer;
        fadeColor.a *= 1 / TRANSITION_DURATION;

        shapeRenderer.setColor(fadeColor);
        shapeRenderer.rect(-50, -150, 500, 300);
    }

}
