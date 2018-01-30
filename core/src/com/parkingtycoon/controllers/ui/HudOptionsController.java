package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.parkingtycoon.CompositionRoot;

/**
 * This class is responsible for controlling the UI that allows the game to have options.
 */
public class HudOptionsController extends HudBaseController {

    public HudOptionsController(Stage stage) {
        super(stage);
        CompositionRoot root = CompositionRoot.getInstance();

        // PAUSE
        final VisTextButton button = new VisTextButton("Pause");

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                root.simulationController.togglePaused();
            }
        });


        // SLIDER
        final VisLabel speedLabel = new VisLabel("speed");
        final VisSlider speedSlider = new VisSlider(10, 1000, 1, false);
        speedSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                root.simulationController.setUpdatesPerSecond((int)(speedSlider.getValue()));
                return true;
            }
        });

        table.add(button).padRight(20);
        table.add(speedLabel).padRight(5);
        table.add(speedSlider).padRight(10);
    }

}
