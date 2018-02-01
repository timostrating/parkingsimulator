package com.parkingtycoon.controllers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.ui.DiagramModel;
import com.parkingtycoon.views.ui.HudStatsFinancialView;

public class DiagramsController extends UpdateableController {

    private final CompositionRoot root;
    private DiagramModel diagramMoney = new DiagramModel("Moneys diagram ???", DiagramModel.DiagramModelType.MONEY, Color.YELLOW);
    private DiagramModel diagramCars = new DiagramModel("Cars diagram ???", DiagramModel.DiagramModelType.CARS, Color.RED);


    public DiagramsController(Stage stage) {
        root = CompositionRoot.getInstance();

        HudStatsFinancialView windowView = new HudStatsFinancialView(stage);
        diagramMoney.registerView(windowView);
        diagramCars.registerView(windowView);
    }

    @Override
    public void update() {
        diagramMoney.addToHistory(root.financialController.getAmount());
        diagramCars.addToHistory(root.carsController.pathFollowers.size());
    }

}
