package com.parkingtycoon.views.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisProgressBar;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.FinancialModel;
import com.parkingtycoon.views.BaseView;

/**
 * This Class is responsible for showing the stats of the simulation.
 */
public class HudStatsView extends BaseView {

    private final HubStatsWindow window = new HubStatsWindow();
    private float moneys;

    public HudStatsView(Stage stage) {
        super();
        show();

        stage.addActor(window);
    }

    @Override
    public void updateView(BaseModel baseModel) {
        if (baseModel instanceof FinancialModel) {
            FinancialModel model = (FinancialModel) baseModel;
            moneys = model.getAmount();
            Logger.info(moneys);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        window.updateFinances(moneys);
        Logger.info(moneys);
    }

    @Override
    public float renderIndex() {
        return 0;
    }


    class HubStatsWindow extends VisWindow {
        private final VisLabel moneyLabel;
        private final VisProgressBar happynisBar;

        HubStatsWindow() {
            super("Stats");
            setMovable(false);

            TableUtils.setSpacingDefaults(this);

            setSize(200, 100);

            moneyLabel = new VisLabel("MONEY");
            happynisBar = new VisProgressBar(0, 100, 1, false);

            add(moneyLabel);
            row();
            add(happynisBar);
        }

        public void updateFinances(Float amount) {
            moneyLabel.setText(""+amount);
            moneyLabel.setColor((amount < 0)? Color.RED : Color.WHITE);
        }
    }

}
