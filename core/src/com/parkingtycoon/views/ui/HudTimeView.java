package com.parkingtycoon.views.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.ui.TimeModel;
import com.parkingtycoon.views.BaseView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This Class is responsible for showing the time of the simulation.
 */
public class HudTimeView extends BaseView {

    private final HubTimeWindow window;

    public HudTimeView(Stage stage) {
        super();
        window = new HudTimeView.HubTimeWindow();

        stage.addActor(window);
    }

    @Override
    public void updateView(BaseModel model) {
        if (model instanceof TimeModel)
            window.updateTime(((TimeModel)model).getTime());
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        window.reposition();
    }

    @Override
    public float renderIndex() {
        return 0;
    }

    class HubTimeWindow extends VisWindow {
        final float WIDTH = 200;
        final float HEIGHT = 100;
        private final VisLabel timeLabel;

        HubTimeWindow() {
            super("Time");
            setMovable(false);

            setPosition(Gdx.graphics.getWidth() - WIDTH, 0);

            setSize(WIDTH, HEIGHT);

            timeLabel = new VisLabel("TIME");
            add(timeLabel);
        }

        void reposition() {
            setPosition(Gdx.graphics.getWidth() - WIDTH, 0);
        }

        void updateTime(long minutesPassedUntilNow) {
            DateFormat dateInstance = new SimpleDateFormat("yyyy MMMMM dd hh:mm");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, (int)minutesPassedUntilNow);
            String date = dateInstance.format(calendar.getTime());
            window.timeLabel.setText(date);
        }
    }

}