package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.BaseController;
import com.parkingtycoon.helpers.interfaces.ClickListener;
import com.parkingtycoon.views.ui.HudStatsView;

/**
 * This class is responsible for controlling the UI that allows the game to have stats.
 */
public class HudStatsController extends BaseController {

    public HudStatsView view;
    private final CompositionRoot root;


    public HudStatsController(Stage stage) {
        HudDiagramsController diagramController = new HudDiagramsController(stage);

        view = new HudStatsView(stage);
        root = CompositionRoot.getInstance();
        root.financialController.getModel().registerView(view);

        view.showStatsButton.addListener((ClickListener) (event, actor) -> diagramController.show(stage));
    }

}

