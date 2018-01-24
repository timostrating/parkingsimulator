package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.parkingtycoon.CompositionRoot;

public class HudOptionsController extends HudBaseController {

    public HudOptionsController(Stage stage) {
        super(stage);
        CompositionRoot root = CompositionRoot.getInstance();

        // BUTTON 1
        final VisImageButton button = new VisImageButton(setupDrawable("ui/test.png"), setupDrawable("ui/test2.png"));

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                root.simulationController.togglePaused();
            }
        });


        // SLIDER
        final VisSlider slider = new VisSlider(10, 100, 1, false);
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
