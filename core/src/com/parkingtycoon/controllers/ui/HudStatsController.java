package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.UpdatableController;
import com.parkingtycoon.helpers.interfaces.ClickListener;
import com.parkingtycoon.views.ui.HudStatsView;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.show;

/**
 * This class is responsible for controlling the UI that allows the game to have stats.
 *
 * @author Timo Strating
 */
public class HudStatsController extends UpdatableController {

    private HudStatsView view;
    private final CompositionRoot root;

    /**
     * Setup the view.
     *
     * @param stage the stage where the view should be added to.
     */
    public HudStatsController(Stage stage) {
        show();
        HudDiagramsController diagramController = new HudDiagramsController(stage);

        view = new HudStatsView(stage);
        root = CompositionRoot.getInstance();
        root.financialController.getModel().registerView(view);

        view.showStatsButton.addListener((ClickListener) (event, actor) -> diagramController.show(stage));
    }

    @Override
    public void update() {
        view.happynisBar.setValue(root.financialController.getAmount());
    }
}

