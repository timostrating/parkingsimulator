package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.parkingtycoon.CompositionRoot;

public class HudOptionsController extends HudBaseController {

    public HudOptionsController() {
        super();
        CompositionRoot root = CompositionRoot.getInstance();

        // BUTTON 1
        Texture buttonTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(buttonTexture));

        final VisImageButton button = new VisImageButton(drawable);
        button.setWidth(100);
        button.setHeight(100);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                root.simulationController.togglePaused();
            }
        });


        // SLIDER
        final VisSlider slider = new VisSlider(10, 3000, 1, false);
        slider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                root.simulationController.setUpdatesPerSecond((int)(slider.getValue()));
                return true;
            }
        });

        table.add(button).expand().top().left();
        table.add(slider).expand().top().left();
    }
}
