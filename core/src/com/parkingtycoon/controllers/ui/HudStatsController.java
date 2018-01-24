package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.parkingtycoon.CompositionRoot;

public class HudStatsController extends HudBaseController {

    public HudStatsController() {
        super();
        CompositionRoot root = CompositionRoot.getInstance();

        // BUTTON 1
        final VisImageButton button = new VisImageButton(setupDrawable("ui/test.png"));

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                root.simulationController.togglePaused();
            }
        });

        table.add(button).expand().bottom().left();
    }
}
