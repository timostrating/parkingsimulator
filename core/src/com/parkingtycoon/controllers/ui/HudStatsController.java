package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.ui.DiagramModel;
import com.parkingtycoon.views.ui.HudStatsFinancialView;
import com.parkingtycoon.views.ui.HudStatsView;

/**
 * This class is responsible for controlling the UI that allows the game to have stats.
 */
public class HudStatsController extends HudBaseController {

    private final CompositionRoot root;

    public HudStatsController(Stage stage) {
        super(stage);

        DiagramModel model = new DiagramModel();
        HudStatsView view = new HudStatsView(stage);
        root = CompositionRoot.getInstance();
        root.financialController.getModel().registerView(view);

        HudStatsFinancialView windowView = new HudStatsFinancialView(stage);
    }

}
