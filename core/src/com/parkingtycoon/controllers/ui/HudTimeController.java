package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.parkingtycoon.CompositionRoot;

/**
 * This class is responsible for controlling the UI that allows the game to have options.
 */
public class HudTimeController extends HudBaseController {

    public HudTimeController(Stage stage) {
        super(stage);

        CompositionRoot root = CompositionRoot.getInstance();

        // BUTTON 1
        final VisImageButton button = new VisImageButton(setupDrawable("ui/test.png"), setupDrawable("ui/test.png"));

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                root.simulationController.togglePaused();
            }
        });

        table.add(button).expand().bottom().right();
    }

}
