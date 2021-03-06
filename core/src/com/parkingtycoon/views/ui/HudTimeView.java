package com.parkingtycoon.views.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.helpers.ui.GameWindowResizeListener;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.ui.TimeModel;
import com.parkingtycoon.views.BaseView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This Class is responsible for showing the time of the simulation.
 *
 * @author Timo Strating
 */
public class HudTimeView extends BaseView {

    private final HubTimeWindow window;


    /**
     * the standard constructor
     *
     * @param stage the stage where we add the window to
     */
    public HudTimeView(Stage stage) {
        super();
        show();

        window = new HudTimeView.HubTimeWindow();

        stage.addActor(window);
    }

    /**
     *
     * @param model the Model gives his this to us. The BaseModel can be cast to the specific model type you are looking for.
     */
    @Override
    public void updateView(BaseModel model) {
        if (model instanceof TimeModel)
            window.updateTime(((TimeModel)model).getTime());
    }

    /**
     *
     * @param batch the batch where you cna render your data on.
     */
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
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

            addListener(new GameWindowResizeListener() {
                @Override
                public void resize() {
                    setPosition(Gdx.graphics.getWidth() - WIDTH, 0);
                }
            });

            setPosition(Gdx.graphics.getWidth() - WIDTH, 0);

            setSize(WIDTH, HEIGHT);

            timeLabel = new VisLabel("TIME");
            add(timeLabel);
        }

        void updateTime(long minutesPassedUntilNow) {
            DateFormat dateInstance = new SimpleDateFormat("yyyy MMMMM dd hh:mm");
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.MINUTE, (int)minutesPassedUntilNow);
//            String date = dateInstance.format(calendar.getTime());
            Date d = new Date(minutesPassedUntilNow * 1000 * 60);
            window.timeLabel.setText(dateInstance.format(d));
        }
    }

}