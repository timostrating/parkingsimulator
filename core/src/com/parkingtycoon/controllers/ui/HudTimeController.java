package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.TimeController;
import com.parkingtycoon.helpers.interfaces.TimedUpdatable;
import com.parkingtycoon.models.ui.TimeModel;
import com.parkingtycoon.views.ui.HudTimeView;

/**
 * This class is responsible for controlling the UI that allows the game to have options.
 */
public class HudTimeController implements TimedUpdatable {

    private final TimeModel model;

    public HudTimeController(Stage stage) {
        CompositionRoot.getInstance().timeController.registerTimedUpdatable(this, TimeController.TimeUpdate.HOURLY);

        model = new TimeModel();
        HudTimeView view = new HudTimeView(stage);
        model.registerView(view);
    }

    @Override
    public void timedUpdate() {
        model.setTime(model.getTime()+60);
    }

}
