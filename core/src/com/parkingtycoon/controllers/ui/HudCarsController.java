package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.UpdateableController;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.models.ui.CarsListModel;
import com.parkingtycoon.views.ui.HudCarsView;

public class HudCarsController extends UpdateableController implements Showable {

    private final HudCarsView view;
    private final CarsListModel model;


    public HudCarsController(Stage stage) {
        CompositionRoot root = CompositionRoot.getInstance();
        model = new CarsListModel("CarsListView", root.carsController.pathFollowers);
        view = new HudCarsView(stage);
        view.hide();
        model.registerView(view);
    }

    @Override
    public void show(Stage stage) {
        view.show(stage);
    }

    @Override
    public void update() {
        model.redraw();
    }
}
