package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.BaseController;
import com.parkingtycoon.helpers.TextureHelper;
import com.parkingtycoon.helpers.interfaces.ClickListener;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.models.BluePrintModel;
import com.parkingtycoon.views.ui.HudBuildView;

public class HudBuildController extends BaseController implements Showable {

    private HudBuildView view;

    public HudBuildController(Stage stage) {
        view = new HudBuildView(stage);
        CompositionRoot root = CompositionRoot.getInstance();

        for (BluePrintModel bluePrint : root.bluePrintsController.bluePrints) {
            VisImageButton button = new VisImageButton(TextureHelper.setupDrawable(bluePrint.uiImagePath+".jpg", 100, 100), bluePrint.title);
            button.addListener((EventListener) (event) -> {  // hover
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
    }

    @Override
    public void show(Stage stage) {
        view.show(stage);
    }
}
