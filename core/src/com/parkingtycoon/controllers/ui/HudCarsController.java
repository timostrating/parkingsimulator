package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.BaseController;
import com.parkingtycoon.controllers.TimeController;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.helpers.interfaces.TimedUpdatable;
import com.parkingtycoon.models.ui.CarsListModel;
import com.parkingtycoon.views.ui.HudCarsView;

/**
 * This Class is responsible for processing the user requests in the Cars window.
 *
 * @author Timo Strating
 */
public class HudCarsController extends BaseController implements Showable, TimedUpdatable {

    private final CompositionRoot root;
    private final HudCarsView view;
    private final CarsListModel model = new CarsListModel();

    /**
     * Setup the Model and View
     */
    public HudCarsController(Stage stage) {
        root = CompositionRoot.getInstance();
        root.timeController.registerTimedUpdatable(this, TimeController.TimeUpdate.HOURLY);
        view = new HudCarsView(stage);
        view.hide();
        model.registerView(view);
    }

    /**
     * Give the view the responsibility of processing the Showable interface.
     *
     * @param stage the stage where the View should be added to.
     */
    @Override
    public void show(Stage stage) {
        view.show(stage);
    }

    @Override
    public void timedUpdate() {
        model.setData(
                root.carsController.pathFollowers.size(),
                root.simulationController.getUpdates()
        );
    }
}
