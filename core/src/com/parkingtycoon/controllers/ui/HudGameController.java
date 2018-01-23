package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.parkingtycoon.helpers.Logger;

public class HudGameController extends HudBaseController {
    public HudGameController() {
        super();

        Texture buttonTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(buttonTexture));

        // BUTTON 2
        final VisImageButton button2 = new VisImageButton(drawable);
        button2.setWidth(100);
        button2.setHeight(100);

        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Logger.info("TODO");
//                stage.addActor(new TestListView());
            }
        });


        // BUTTON 3
        final VisImageButton button3 = new VisImageButton(drawable);
        button3.setWidth(100);
        button3.setHeight(100);

        button3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Logger.info("TODO");
//                stage.addActor(new TestGraphView());
            }
        });

        table.add(button2);
        table.add(button3);
    }
}