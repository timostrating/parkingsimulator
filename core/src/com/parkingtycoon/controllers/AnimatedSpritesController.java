package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.ArrayNamed;
import com.parkingtycoon.helpers.UpdateableController;
import com.parkingtycoon.models.AnimatedSpriteModel;

public class AnimatedSpritesController extends UpdateableController {

    private static ArrayNamed<AnimatedSpriteModel> models = new ArrayNamed<>();

    public AnimatedSpritesController() {
        CompositionRoot.getInstance().simulationController.registerUpdatable(this);
    }

    @Override
    public void update() {

        SimulationController simulationController = CompositionRoot.getInstance().simulationController;

        float speedMultiplier = simulationController.getUpdatesPerSecond()
                        / SimulationController.REAL_TIME_UPDATES_PER_SECOND;

        for (AnimatedSpriteModel spriteModel : models)
            spriteModel.speedMultiplier = speedMultiplier;
    }

    public AnimatedSpriteModel getAnimatedSpriteModel(String spritePath) {
        AnimatedSpriteModel model = models.get(spritePath);
        if (model == null)
            model = modelFromJson(spritePath);

        return model;
    }

    private AnimatedSpriteModel modelFromJson(String path) {

        AnimatedSpriteModel spriteModel = new AnimatedSpriteModel(path);
        models.add(spriteModel);

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

            // todo: support reverse animations

            for (int i = from; i <= to; i++)
                animation.add(frames[i]);

            spriteModel.animations.add(animation);
        }

        return spriteModel;
    }
}
