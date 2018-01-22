package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.AnimatedSpriteModel;

public class AnimatedSpriteView extends SpriteView {

    private int currentFrame;
    private float remainingTime;
    private boolean loop, flipX, flipY, done, simulationSpeedDependent;
    private AnimatedSpriteModel spriteModel;
    private AnimatedSpriteModel.Animation currentAnimation;

    public AnimatedSpriteView(String spritePath, boolean simulationSpeedDependent) {
        super(spritePath + ".png");

        this.simulationSpeedDependent = simulationSpeedDependent;

        spriteModel = CompositionRoot.getInstance().animatedSpritesController.getAnimatedSpriteModel(spritePath);
        sprite.setSize(spriteModel.frameWidth / 16f, spriteModel.frameHeight / 16f);

    }

    @Override
    public void draw(SpriteBatch batch) {
        updateAnimation();
        super.draw(batch);
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
        setRegion(currentAnimation.get(currentFrame));
    }

    private void setRegion(AnimatedSpriteModel.Animation.Frame f) {
        sprite.setRegion(
                f.x + (flipX ? spriteModel.frameWidth : 0),                 // x
                f.y + (flipY ? spriteModel.frameHeight : 0),                // y
                flipX ? -spriteModel.frameWidth : spriteModel.frameWidth,   // width
                flipY ? -spriteModel.frameHeight : spriteModel.frameHeight  // height
        );
    }

}