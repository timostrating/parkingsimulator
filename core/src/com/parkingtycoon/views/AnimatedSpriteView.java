package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.AnimatedSpriteModel;

/**
 * This Class is responsible for showing a sprite that is animated.
 */
public class AnimatedSpriteView extends SpriteView {

    protected AnimatedSpriteModel spriteModel;
    private int currentFrame;
    private float remainingTime;
    private boolean loop, flipX, flipY, done, simulationSpeedDependent;
    private String jsonPath;

    protected AnimatedSpriteModel.Animation currentAnimation;


    public AnimatedSpriteView(String spritePath, boolean simulationSpeedDependent) {
        super(spritePath + ".png");
        jsonPath = spritePath + ".json";
        this.simulationSpeedDependent = simulationSpeedDependent;
    }

    @Override
    public void start() {
        super.start();
        spriteModel = CompositionRoot.getInstance().animatedSpritesController.getAnimatedSpriteModel(jsonPath);
        sprite.setSize(spriteModel.frameWidth / 32f, spriteModel.frameHeight / 32f);
    }

    @Override
    public void render(SpriteBatch batch) {
        updateAnimation();
        super.render(batch);
    }

    protected boolean play(String animationName, boolean loop) {
        return play(animationName, loop, 0);
    }

    protected boolean play(String animationName, boolean loop, int beginFrame) {
        if (spriteModel.animations.get(animationName) == null)
            return false;

        this.loop = loop;
        currentAnimation = spriteModel.animations.get(animationName);
        currentFrame = beginFrame == -1 ? currentAnimation.size() - 2 : beginFrame - 1;
        remainingTime = 0;
        done = false;
        return true;
    }

    private void updateAnimation() {
        if (done || currentAnimation == null)
            return;

        remainingTime -= Gdx.graphics.getDeltaTime() * (simulationSpeedDependent ? spriteModel.speedMultiplier : 1);
        if (remainingTime <= 0) {
            boolean lastFrame = currentFrame == currentAnimation.size() - 1;
            if (!loop && lastFrame) {
                done = true;
                return;
            }

            currentFrame = lastFrame ? 0 : currentFrame + 1;
            AnimatedSpriteModel.Animation.Frame f = currentAnimation.get(currentFrame);
            remainingTime = f.duration;
            setRegion(f);
        }
    }

    protected void flip(boolean x, boolean y) {
        flipX = x;
        flipY = y;
        if (currentAnimation != null && currentFrame != -1)
            setRegion(currentAnimation.get(currentFrame));
    }

    protected void setRegion(AnimatedSpriteModel.Animation.Frame f) {
        sprite.setRegion(
                f.x + (flipX ? spriteModel.frameWidth : 0),                 // x
                f.y + (flipY ? spriteModel.frameHeight : 0),                // y
                flipX ? -spriteModel.frameWidth : spriteModel.frameWidth,   // width
                flipY ? -spriteModel.frameHeight : spriteModel.frameHeight  // height
        );
    }

}
