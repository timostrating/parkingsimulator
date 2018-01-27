package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisProgressBar;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.views.BaseView;

/**
 * This Class is responsible for showing the stats of the simulation.
 */
public class HudStatsView extends BaseView {

    public HudStatsView(Stage stage) {
        stage.addActor(new HubStatsWindow());
    }

    @Override
    public void updateView(BaseModel model) { }

    @Override
    public float renderIndex() {
        return 0;
    }


    class HubStatsWindow extends VisWindow {
        HubStatsWindow() {
            super("Stats");
            setMovable(false);

            setSize(200, 100);

            add(new VisLabel("jammer"));
            row();
            add(new VisProgressBar(0, 100, 1, false));
        }
    }

}
