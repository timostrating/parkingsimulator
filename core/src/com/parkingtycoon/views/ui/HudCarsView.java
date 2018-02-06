package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.ui.CarsListModel;
import com.parkingtycoon.views.BaseView;

/**
 * This view is responsible for showing a window where the cars are visible in a list. TODO change
 *
 * @author Timo Strating
 */
public class HudCarsView extends BaseView implements Showable {

    private HudCarsWindowView window;


    public HudCarsView(Stage stage) {
        window = new HudCarsWindowView();
        stage.addActor(window);
    }

    @Override
    public void updateView(BaseModel model) {
        if (model instanceof CarsListModel)
            window.update((CarsListModel)model);
    }

    @Override
    public void preRender() { }

    @Override
    public float renderIndex() {
        return 0;
    }

    @Override
    public void show(Stage stage) {
        window.setVisible(true);
        window.setZIndex(999);
        stage.addActor(window);
    }

    @Override
    public void hide() {
        window.setZIndex(0);
        window.setVisible(false);
    }


    /**
     * This is the class that shows the cars window.
     */
    public class HudCarsWindowView extends VisWindow {
        private final VisLabel totalCars;
        private final VisLabel maxCarsAtOnce;
        private final VisLabel totalUpdates;

        public HudCarsWindowView () {
            super("Cars");

            TableUtils.setSpacingDefaults(this);
            columnDefaults(0).left();

            addCloseButton();
            setResizable(true);
            closeOnEscape();

            VisTable table = new VisTable(true);
            totalCars = new VisLabel("totalCars");
            table.add(new VisLabel("TotalCars: ")).left();
            table.add(totalCars).right();
            table.row();

            maxCarsAtOnce = new VisLabel("maxCarsAtOnce");
            table.add("Maximum Cars at Once: ").left();
            table.add(maxCarsAtOnce).right();
            table.row();

            totalUpdates = new VisLabel("totalUpdates");
            table.add("Total Simulation Steps: ").left();
            table.add(totalUpdates).right();
            table.row();

            add(table);


            setSize(300, 300);
            setPosition(458, 245);
        }

        public void update(CarsListModel model) {
            totalCars.setText(""+model.totalCars);
            maxCarsAtOnce.setText(""+model.maxCarsAtOnce);
            totalUpdates.setText(""+model.totalUpdates);
        }
    }

}
