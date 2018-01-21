package com.parkingtycoon.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.ArrayNamed;
import com.parkingtycoon.interfaces.Named;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.SimulationModel;

import java.util.ArrayList;

public class AnimatedSpriteView extends SpriteView {

    private int currentFrame;
    private float remainingTime;
    private float speedMultiplier = 1;
    private boolean loop, flipX, flipY, done;
    private AnimatedSpriteModel spriteModel;
    private AnimatedSpriteModel.Animation currentAnimation;

    public AnimatedSpriteView(String spritePath, boolean simulationSpeedDependent) {
        super(spritePath + ".png");

        spriteModel = animatedSpriteModels.get(spritePath);
        if (spriteModel == null)
            modelFromJson(spritePath);

        sprite.setSize(spriteModel.frameWidth / 16f, spriteModel.frameHeight / 16f);

        if (simulationSpeedDependent)
            CompositionRoot.getInstance().simulationController.getModel().registerView(this);

    }

    @Override
    public void updateView(BaseModel model) {
        if (model instanceof SimulationModel)
            speedMultiplier = (float) ((SimulationModel) model).updatesPerSecond
                    / SimulationModel.REAL_TIME_UPDATES_PER_SECOND;
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

        remainingTime -= Gdx.graphics.getDeltaTime() * speedMultiplier;
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

    private void modelFromJson(String path) {

        spriteModel = new AnimatedSpriteModel(path);
        animatedSpriteModels.add(spriteModel);

        JsonValue json = new JsonReader().parse(Gdx.files.internal(path + ".json"));
        JsonValue jsonFrames = json.get("frames");

        AnimatedSpriteModel.Animation.Frame[] frames = new AnimatedSpriteModel.Animation.Frame[jsonFrames.size];

        for (int i = 0; i < jsonFrames.size; i++) {
            JsonValue jsonFrame = jsonFrames.get(i);
            JsonValue f = jsonFrame.get("frame");

            frames[i] = new AnimatedSpriteModel.Animation.Frame(
                    f.getInt("x"),
                    f.getInt("y"),
                    jsonFrame.getFloat("duration") / 1000f
            );

            if (spriteModel.frameWidth == 0) {
                spriteModel.frameWidth = f.getInt("w");
                spriteModel.frameHeight = f.getInt("h");
            }

        }

        JsonValue tags = json.get("meta").get("frameTags");
        for (JsonValue tag : tags) {

            AnimatedSpriteModel.Animation animation = new AnimatedSpriteModel.Animation(tag.getString("name"));

            int from = tag.getInt("from"),
                    to = tag.getInt("to");

            for (int i = from; i <= to; i++)
                animation.add(frames[i]);

            spriteModel.animations.add(animation);
        }

    }

    private static ArrayNamed<AnimatedSpriteModel> animatedSpriteModels = new ArrayNamed<>();

    private static class AnimatedSpriteModel implements Named {

        private String name;
        private ArrayNamed<Animation> animations = new ArrayNamed<>();
        private int frameWidth, frameHeight;

        private AnimatedSpriteModel(String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }

        static class Animation extends ArrayList<Animation.Frame> implements Named {

            static class Frame {
                int x, y;
                float duration;

                public Frame(int x, int y, float duration) {
                    this.x = x;
                    this.y = y;
                    this.duration = duration;
                }

            }

            private String name;

            Animation(String name) {
                this.name = name;
            }

            @Override
            public String name() {
                return name;
            }
        }

    }

}
