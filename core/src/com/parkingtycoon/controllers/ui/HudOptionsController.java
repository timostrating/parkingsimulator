package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.helpers.interfaces.ClickListener;

/**
 * This class is responsible for controlling the UI that allows the game to have options.
 */
public class HudOptionsController extends HudBaseController {

    private VisLabel floorTitle;
    private final CompositionRoot root;
    private int oldFloorIndex = 99;

    public HudOptionsController(Stage stage) {
        super(stage);
        root = CompositionRoot.getInstance();

        // PAUSE
        final VisTextButton button = new VisTextButton("Pause");
        button.addListener((ClickListener) (event, actor) -> root.simulationController.togglePaused());

        // SLIDER
        final VisLabel speedLabel = new VisLabel("speed");
        final VisSlider speedSlider = new VisSlider(10, 1000, 1, false);
        speedSlider.addListener(event -> {
            root.simulationController.setUpdatesPerSecond((int)(speedSlider.getValue()));
            return true;
        });


        final VisTextButton floorUp = new VisTextButton("^");
        floorUp.addListener((ClickListener) (event, actor) -> Logger.info("UP"));

        floorTitle = new VisLabel("Floor: XX");

        final VisTextButton floorDown = new VisTextButton("v");
        floorDown.addListener((ClickListener) (event, actor) -> Logger.info("DOWN"));


        table.add(button).padRight(20);
        table.add(speedLabel).padRight(5);
        table.add(speedSlider).padRight(50);

        table.add(floorUp).padRight(10);
        table.add(floorTitle).padRight(10);
        table.add(floorDown);
    }

    @Override
    public void update() {
        if (oldFloorIndex != root.floorsController.getCurrentFloor()) {
            floorTitle.setText("Floor: " + root.floorsController.getCurrentFloor());
            oldFloorIndex = root.floorsController.getCurrentFloor();
        }
    }

}
