package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.BaseController;
import com.parkingtycoon.views.ui.HudBottomLeftView;

/**
 * This class is responsible for controlling the UI that allows the game to have stats.
 */
public class HudBottomLeftController extends BaseController {

    public HudBottomLeftView view;
    private final CompositionRoot root;


    public HudBottomLeftController(Stage stage) {
        HudDiagramsController controller = new HudDiagramsController(stage);

        view = new HudBottomLeftView(stage);
        root = CompositionRoot.getInstance();
        root.financialController.getModel().registerView(view);
    }

}

