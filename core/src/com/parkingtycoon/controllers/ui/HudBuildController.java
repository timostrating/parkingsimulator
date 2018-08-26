package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.BaseController;
import com.parkingtycoon.helpers.TextureHelper;
import com.parkingtycoon.helpers.interfaces.ClickListener;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.models.BluePrintModel;
import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.views.ui.HudBuildView;

/**
 * This Class is responsible for processing the user requests in the build window.
 *
 * @author Timo Strating
 */
public class HudBuildController extends BaseController implements Showable {

    private HudBuildView view;


    /**
     * Setup the model and view
     *
     * @param stage the stage where the View should be added to.
     */
    public HudBuildController(Stage stage) {
        CompositionRoot root = CompositionRoot.getInstance();

        view = new HudBuildView(stage);
        view.hide();


        for (BluePrintModel bluePrint : root.bluePrintsController.bluePrints) {
            VisImageButton button = new VisImageButton(TextureHelper.setupDrawable(bluePrint.uiImagePath+".jpg", 100, 100), bluePrint.title);
            button.addListener((event) -> {  // hover
                view.description.setText(bluePrint.description);
                view.price.setText(""+bluePrint.price);
                return true;
            });
            button.addListener((ClickListener) (event, actor) -> {  // click
                view.hide();
                root.bluePrintsController.setNextToBeBuilt(bluePrint);
            });
            view.group.addActor(button);
        }

        for (FloorModel.FloorType floorType : FloorModel.FloorType.values()) {
            VisTextButton button = new VisTextButton(floorType.toString());
            button.addListener((event) -> {  // hover
                view.description.setText(floorType.toString());
                view.price.setText("100");
                return true;
            });
            button.addListener((ClickListener) (event, actor) -> {  // click
                view.hide();
                root.floorsController.nextFloorType = floorType;
            });
            view.group.addActor(button);
        }
    }

    /**
     * Give the view the responsibility of processing the Showable interface.
     *
     * @param stage the stage where the View should be added to.
     */
    @Override
    public void show(Stage stage) {
        view.show(stage);
    }
}
