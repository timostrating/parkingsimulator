package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.TimeController;
import com.parkingtycoon.helpers.interfaces.TimedUpdatable;
import com.parkingtycoon.models.ui.TimeModel;
import com.parkingtycoon.views.ui.HudTimeView;

/**
 * This class is responsible for controlling the UI that allows the game to have options.
 *
 * @author Timo Strating
 */
public class HudTimeController implements TimedUpdatable {

    private final TimeModel model;

    /**
     * Setup the model and view.
     *
     * @param stage the stage to witch the view should be added.
     */
    public HudTimeController(Stage stage) {
        CompositionRoot.getInstance().timeController.registerTimedUpdatable(this, TimeController.TimeUpdate.HOURLY);

        model = new TimeModel();
        HudTimeView view = new HudTimeView(stage);
        model.registerView(view);
    }

    /**
     * Update the Model every time frame.
     */
    @Override
    public void timedUpdate() {
        model.setTime(model.getTime()+60);
    }

}
