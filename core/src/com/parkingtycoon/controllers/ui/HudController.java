package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.Input;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.views.ui.HudView;

/**
 * This class is responsible for setting up the UI.
 */
public class HudController {

    public final HudView view;

    private boolean debug = false;


    public HudController() {
        CompositionRoot root = CompositionRoot.getInstance();

        view = new HudView();

        new HudTopController(view.stage);
        new HudBottomLeftController(view.stage);
        new HudBottomRightController(view.stage);

        root.inputController.onKeyDown.put(Input.Keys.F2, () -> {
            toggleDebug();
            return true;
        });
    }

    public void toggleDebug() {
        debug = !debug;
    }
}
