package com.parkingtycoon.views.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisProgressBar;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.FinancialModel;
import com.parkingtycoon.views.BaseView;

/**
 * This Class is responsible for showing the stats of the simulation.
 *
 * @author Timo Strating
 */
public class HudStatsView extends BaseView {

    public VisProgressBar happynisBar;
    public VisTextButton showStatsButton;

    private final HubStatsWindow window = new HubStatsWindow();
    private float moneys;


    public HudStatsView(Stage stage) {
        super();
        show();

        stage.addActor(window);
    }

    /**
     * Get the data out of the model and send it to the window
     *
     * @param baseModel the model width the financial data.
     */
    @Override
    public void updateView(BaseModel baseModel) {
        if (baseModel instanceof FinancialModel) {
            FinancialModel model = (FinancialModel) baseModel;
            moneys = model.getAmount();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        window.updateFinances(moneys);
    }

    @Override
    public float renderIndex() {
        return 0;
    }

    /**
     * This is the window with all the stats that will be placed in the left bottom corner.
     */
    class HubStatsWindow extends VisWindow {
        private final VisLabel moneyLabel;

        HubStatsWindow() {
            super("Stats");
            setMovable(false);

            TableUtils.setSpacingDefaults(this);

            setSize(200, 100);

            moneyLabel = new VisLabel("MONEY");
            happynisBar = new VisProgressBar(0, 1_000_000, 1, false);
            showStatsButton = new VisTextButton("Show Stats");

            add(showStatsButton);
            row();
            add(moneyLabel);
            row();
            add(happynisBar);
        }

        public void updateFinances(Float amount) {
            moneyLabel.setText("$"+amount);
            moneyLabel.setColor((amount < 0)? Color.RED : Color.WHITE);
        }
    }

}
