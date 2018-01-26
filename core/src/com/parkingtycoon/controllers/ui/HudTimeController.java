package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.models.HudTimeModel;
import com.parkingtycoon.views.ui.HudTimeView;

/**
 * This class is responsible for controlling the UI that allows the game to have options.
 */
public class HudTimeController extends HudBaseController {

    public HudTimeController(Stage stage) {
        super(stage);

        HudTimeModel model = new HudTimeModel();
        HudTimeView view = new HudTimeView(stage);
        model.registerView(view);

//        CompositionRoot root = CompositionRoot.getInstance();
//
//        // BUTTON 1
//        final VisTextButton button = new VisTextButton("nice");
//
//        button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                root.simulationController.togglePaused();
//            }
//        });
//
//        table.add(button).expand().bottom().right();
    }

}
