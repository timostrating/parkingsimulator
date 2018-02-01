package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.UpdateableController;
import com.parkingtycoon.models.ui.DiagramModel;
import com.parkingtycoon.views.ui.HudStatsFinancialView;

public class HudFinancialController extends UpdateableController {

    private final CompositionRoot root;
    private DiagramModel diagramModel;


    public HudFinancialController(Stage stage) { // TODO: Clean up the naming
        root = CompositionRoot.getInstance();
        diagramModel = new DiagramModel("Financial diagramModel");
        HudStatsFinancialView windowView = new HudStatsFinancialView(stage);
        diagramModel.registerView(windowView);
    }

    @Override
    public void update() {
        diagramModel.addToHistory(root.financialController.getAmount());
    }

}
