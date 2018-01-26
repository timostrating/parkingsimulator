package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.models.ui.HudStatsModel;
import com.parkingtycoon.views.ui.HudStatsView;

public class HudStatsController extends HudBaseController {

    public HudStatsController(Stage stage) {
        super(stage);


        HudStatsModel model = new HudStatsModel();
        HudStatsView view = new HudStatsView(stage);
        view.show();
        model.registerView(view);

//        CompositionRoot root = CompositionRoot.getInstance();

        // BUTTON 1
//        VisLabel label = new VisLabel("label");
//        VisProgressBar progressbar = new VisProgressBar(0, 100, 1, false);

//        final VisImageButton button = new VisImageButton(setupDrawable("ui/test.png"), setupDrawable("ui/test.png"));

//        button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                root.simulationController.togglePaused();
//            }
//        });


//        table.add().bottom().left();
//        table.add(button).expand().bottom().left();
    }
}
