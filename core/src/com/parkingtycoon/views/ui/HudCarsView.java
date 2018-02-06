package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarModel;
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
//        if (model instanceof CarsListModel)
//            window.update((CarsListModel)model);
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



    public class HudCarsWindowView extends VisWindow {
        private final CompositionRoot root;

        public HudCarsWindowView () {
            super("Cars");

            TableUtils.setSpacingDefaults(this);
            columnDefaults(0).left();

            addCloseButton();
            setResizable(true);
            closeOnEscape();

            Array<CarModel> genericArray = new Array<>();
            root = CompositionRoot.getInstance();
            genericArray.addAll(root.carsController.pathFollowers.toArray(new CarModel[root.carsController.pathFollowers.size()]), 0, 0);



            setSize(300, 300);
            setPosition(458, 245);
        }
    }

}
