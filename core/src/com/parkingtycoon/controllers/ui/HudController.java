package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.Input;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.views.ui.HudView;

/**
 * This class is responsible for setting up the UI.
 *
 * @author Timo Strating
 */
public class HudController {

    public final HudView view;

    private boolean debug = false;

    /**
     * Setup the hud view and the other controllers
     */
    public HudController(Game game) {
        CompositionRoot root = CompositionRoot.getInstance();

        view = new HudView(game);

        new HudTopController(view.stage);
        new HudStatsController(view.stage);
        new HudTimeController(view.stage);

        root.inputController.onKeyDown.put(Input.Keys.F2, () -> {
            toggleDebug();
            return true;
        });
    }

    public void toggleDebug() {
        debug = !debug;
    }
}
