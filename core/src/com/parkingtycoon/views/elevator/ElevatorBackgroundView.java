package com.parkingtycoon.views.elevator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.ElevatorModel;
import com.parkingtycoon.views.SpriteView;

/**
 * Shows the background of an elevator
 */
public class ElevatorBackgroundView extends SpriteView {

    private boolean initialized;

    public ElevatorBackgroundView() {
        super("sprites/elevatorBackground.png");
    }


    protected Sprite shadow = new Sprite();

    /**
     * Sets the position and rotation of the background according to the elevators position
     * @param model Model
     */
    @Override
    public void updateView(BaseModel model) {
        super.updateView(model);

        if (model instanceof ElevatorModel && !initialized) {
            ElevatorModel elevator = (ElevatorModel) model;
            spritePosition.set(elevator.x + 1.5f, elevator.y + 3);
            IsometricConverter.normalToIsometric(spritePosition);
            sprite.setPosition(spritePosition.x, spritePosition.y);
                    shadow.setPosition(spritePosition.x-1, spritePosition.y-1);
            sprite.flip(elevator.angle == 0, false);
            initialized = true;
        }
    }

    @Override
    public void start() {
        Texture shadowTexture = TEXTURES.get(spritePath);
        shadow.setRegion(shadowTexture);
        shadow.setSize(shadowTexture.getWidth() / 32f, shadowTexture.getHeight() / 32f);

        super.start();
    }

    @Override
    public void render(SpriteBatch batch) {
        Color c = new Color(batch.getColor());
        shadow.setColor(0,0,0,1);
        shadow.setScale(2, 1);
        shadow.setCenterY(5);
        shadow.draw(batch);
        batch.setColor(c);
    }
}
