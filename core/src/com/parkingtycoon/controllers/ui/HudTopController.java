package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.BaseController;
import com.parkingtycoon.helpers.interfaces.ClickListener;
import com.parkingtycoon.helpers.interfaces.TimedUpdatable;
import com.parkingtycoon.views.ui.HudBuildView;
import com.parkingtycoon.views.ui.HudCarsView;
import com.parkingtycoon.views.ui.HudSettingsView;
import com.parkingtycoon.views.ui.HudTopView;

/**
 * This class is responsible for the UI that changes the game.
 */
public class HudTopController extends BaseController implements TimedUpdatable {

    public HudTopView view;
    private final CompositionRoot root;
    private int oldFloorIndex = 0;


    public HudTopController(Stage stage) {
        root = CompositionRoot.getInstance();
        view = new HudTopView(stage);

        // LEFT
        view.button.addListener((ClickListener) (event, actor) -> root.simulationController.togglePaused());
        view.speedSlider.addListener(event -> {
            root.simulationController.setUpdatesPerSecond((int)(view.speedSlider.getValue()));
            return true;
        });
        view.floorUp.addListener((ClickListener) (event, actor) -> root.floorsController.moveUpOneFloor());
        view.floorDown.addListener((ClickListener) (event, actor) -> root.floorsController.moveDownOneFloor());

        // RIGHT
        view.deletedButton.addListener((ClickListener) (event, actor) -> root.bluePrintsController.toggleDemolishMode() );
        view.buildButton.addListener((ClickListener) (event, actor) -> new HudBuildView(stage));
        view.carsButton.addListener((ClickListener) (event, actor) -> new HudCarsView());
        view.settings.addListener((ClickListener) (event, actor) -> new HudSettingsView(stage));
    }

    @Override
    public void timedUpdate() {
        if (oldFloorIndex != root.floorsController.getCurrentFloor()) {
            view.floorTitle.setText("Floor: " + root.floorsController.getCurrentFloor());
            oldFloorIndex = root.floorsController.getCurrentFloor();
        }
    }

}