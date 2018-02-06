package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.CoordinateRotater;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.BluePrintModel;
import com.parkingtycoon.models.FloorModel;

/**
 * This View will show a Ghost-version of a building before the building is actually built.
 */
public class BluePrintView extends AnimatedSpriteView {

    private String animationName;
    private boolean canBuild, fadingIn;
    private Color color = new Color();
    private Texture tileTexture, blockedTileTexture;
    private int originX, originY, width, height, angle;
    private FloorModel.FloorType[][] tiles;
    private boolean[][] validTiles;

    public BluePrintView(String spritePath, float spriteOriginX, float spriteOriginY) {
        super(spritePath, false);
        sprite.setOrigin(spriteOriginX, spriteOriginY);
    }

    /**
     * Gather textures
     */
    @Override
    public void start() {
        super.start();
        tileTexture = TEXTURES.get("sprites/bluePrintTile.png");
        blockedTileTexture = TEXTURES.get("sprites/blockedBluePrintTile.png");
    }

    /**
     * Set positions of textures
     * @param model BluePrint
     */
    @Override
    public void updateView(BaseModel model) {

        if (model instanceof BluePrintModel) {

            BluePrintModel bluePrint = (BluePrintModel) model;

            if (!bluePrint.isActive()) {
                end();
                return;
            }

            angle = bluePrint.getAngle();
            animationName = "bluePrintAngle" + angle;
            canBuild = bluePrint.canBuild();
            spritePosition.set(bluePrint.x, bluePrint.y);
            IsometricConverter.normalToIsometric(spritePosition);
            sprite.setOriginBasedPosition(spritePosition.x, spritePosition.y);

            width = bluePrint.tiles.length;
            height = bluePrint.tiles[0].length;

            originX = bluePrint.x;
            originY = bluePrint.y;

            tiles = bluePrint.tiles;
            validTiles = bluePrint.validTiles;
        }

    }

    /**
     * This will render the ghost-textures
     * @param batch
     */
    @Override
    public void render(SpriteBatch batch) {

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                if (tiles[x][y] == null || validTiles == null)
                    continue;

                int worldX = CoordinateRotater.rotate(x, width, y, height, angle) + originX;
                int worldY = CoordinateRotater.rotate(y, height, x, width, angle) + originY;

                Vector2 isoPosition = IsometricConverter.normalToIsometric(new Vector2(worldX, worldY));
                batch.draw(
                        validTiles[x][y] ? tileTexture : blockedTileTexture,    // white/red tile-border texture
                        isoPosition.x - 2, isoPosition.y - 2,                   // position
                        4, 2                                                    // scale
                );

            }

        }

        super.render(batch);

    }

    /**
     * This will update the animation of the ghost-textures
     */
    @Override
    public void preRender() {

        if (canBuild) {
            float delta = Gdx.graphics.getDeltaTime() * 1.2f;
            if (fadingIn) {
                color.a = Math.min(1, color.a + delta);
                if (color.a == 1) fadingIn = false;
            } else {
                color.a = Math.max(.6f, color.a - delta);
                if (color.a == .6f) fadingIn = true;
            }
        } else color.a = 1;

        color.set(1, canBuild ? 1 : .2f, canBuild ? 1 : .2f, color.a);

        sprite.setColor(color);

        if (currentAnimation == null || !currentAnimation.name().equals(animationName))
            play(animationName, true);
    }

    @Override
    public void debugRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.line(spritePosition.x - .1f, spritePosition.y - .1f, spritePosition.x + .1f, spritePosition.y + .1f);
        shapeRenderer.line(spritePosition.x - .1f, spritePosition.y + .1f, spritePosition.x + .1f, spritePosition.y - .1f);
        super.debugRender(shapeRenderer);
    }
}

