package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.views.ui.HudBuildView;
import com.parkingtycoon.views.ui.HudCarsView;

/**
 * This class is responsible for the UI that changes the game.
 */
public class HudGameController extends HudBaseController {

    private final CompositionRoot root;

    public HudGameController(Stage stage) {
        super(stage);
        root = CompositionRoot.getInstance();

        final VisTextButton deletedButton = new VisTextButton("delete");
        deletedButton.addListener((com.parkingtycoon.helpers.interfaces.ClickListener) (event, actor) -> root.financialController.spend(-1000));

        final VisTextButton buildButton = new VisTextButton("build");
        deletedButton.addListener((com.parkingtycoon.helpers.interfaces.ClickListener) (event, actor) -> new HudBuildView(stage));

        final VisTextButton carsButton = new VisTextButton("Cars");
        deletedButton.addListener((com.parkingtycoon.helpers.interfaces.ClickListener) (event, actor) -> new HudCarsView());

        table.add(deletedButton).padLeft(10);
        table.add(buildButton).padLeft(10);
        table.add(carsButton).padLeft(50);
    }

}