package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.ArrayNamed;
import com.parkingtycoon.models.AnimatedSpriteModel;

/**
 * This class is responsible for loading Animated Sprite Data from JSON files to usable AnimatedSpriteModels.
 *
 * @author Hilko Janssen
 */
public class AnimatedSpritesController extends UpdateableController {

    private static ArrayNamed<AnimatedSpriteModel> models = new ArrayNamed<>();

    /**
     * This method will change the speedMultiplier of animated sprites depending on the simulation speed.
     */
    @Override
    public void update() {

        SimulationController simulationController = CompositionRoot.getInstance().simulationController;

        float speedMultiplier = simulationController.getUpdatesPerSecond() / (float) SimulationController.REAL_TIME_UPDATES_PER_SECOND;

        for (AnimatedSpriteModel spriteModel : models)
            spriteModel.speedMultiplier = speedMultiplier;
    }

    /**
     * This method will provide a model with all information of an animated sprite.
     * If the requested model does not exist yet, then the information will be read from a JSON file.
     *
     * @param   spritePath The path of the requested sprite.
     * @return  The requested model
     */
    public AnimatedSpriteModel getAnimatedSpriteModel(String spritePath) {
        AnimatedSpriteModel model = models.get(spritePath);
        if (model == null)
            model = modelFromJson(spritePath);

        return model;
    }

    /**
     * This method will create a new AnimatedSpriteModel containing information that is read from the JSON file
     *
     * @param path  The path of the requested sprite
     * @return      The newly created model
     */
    private AnimatedSpriteModel modelFromJson(String path) {  // TODO: to long

        AnimatedSpriteModel spriteModel = new AnimatedSpriteModel(path);
        models.add(spriteModel);

        JsonValue json = new JsonReader().parse(Gdx.files.internal(path));
        JsonValue jsonFrames = json.get("frames");

        spriteModel.frames = new AnimatedSpriteModel.Animation.Frame[jsonFrames.size];

        for (int i = 0; i < jsonFrames.size; i++) {
            JsonValue jsonFrame = jsonFrames.get(i);
            JsonValue f = jsonFrame.get("frame");

            spriteModel.frames[i] = new AnimatedSpriteModel.Animation.Frame(
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

            boolean reverse = tag.getString("direction").equals("reverse");

            for (int i = from; i <= to; i++) {
                if (reverse)
                    animation.add(0, spriteModel.frames[i]);
                else
                    animation.add(spriteModel.frames[i]);
            }

            spriteModel.animations.add(animation);
        }

        return spriteModel;
    }

}
