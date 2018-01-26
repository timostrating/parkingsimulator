package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.views.ui.TestListView;

/**
 * This class is responsible for the UI that changes the game.
 */
public class HudGameController extends HudBaseController {

    public HudGameController(Stage stage) {
        super(stage);

        // DELETE
        final VisTextButton deletedButton = new VisTextButton("delete");

        deletedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Logger.info("TODO");
//                stage.addActor(new TestListView());
            }
        });


        // BUILD
        final VisTextButton buildButton = new VisTextButton("build");

        buildButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Logger.info("TODO");
//                stage.addActor(new TestListView());
            }
        });


        // CARS
        final VisTextButton carsButton = new VisTextButton("Cars");

        carsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Logger.info("TODO");
                stage.addActor(new TestListView());
            }
        });

        table.add(deletedButton).padLeft(10);
        table.add(buildButton).padLeft(10);
        table.add(carsButton).padLeft(50);
    }

}