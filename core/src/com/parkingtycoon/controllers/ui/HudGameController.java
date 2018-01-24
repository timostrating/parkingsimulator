package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.parkingtycoon.helpers.Logger;

public class HudGameController extends HudBaseController {
    public HudGameController() {
        super();

        // BUTTON 2
        final VisImageButton button2 = new VisImageButton(setupDrawable("ui/test.png"), setupDrawable("ui/test2.png"));

        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Logger.info("TODO");
//                stage.addActor(new TestListView());
            }
        });


        // BUTTON 3
        final VisImageButton button3 = new VisImageButton(setupDrawable("ui/test.png"), setupDrawable("ui/test2.png"));

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