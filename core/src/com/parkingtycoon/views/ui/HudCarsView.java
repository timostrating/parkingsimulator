package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.util.adapter.ArrayAdapter;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.views.BaseView;

public class HudCarsView extends BaseView {

    @Override
    public void updateView(BaseModel model) {

    }

    @Override
    public float renderIndex() {
        return 0;
    }



    public class HudCarsWindowView extends VisWindow {
        private final ListView<CarModel> view;

        public HudCarsWindowView () {
            super("Cars");

            TableUtils.setSpacingDefaults(this);
            columnDefaults(0).left();

            addCloseButton();
            setResizable(true);
            closeOnEscape();

            Array<CarModel> genericArray = new Array<>();
            CompositionRoot root = CompositionRoot.getInstance();
            genericArray.addAll(root.carsController.pathFollowers.toArray(new CarModel[root.carsController.pathFollowers.size()]), 0, 0);

            final CarModelAdapter adapter = new CarModelAdapter(genericArray);
            view = new ListView<>(adapter);
            view.setUpdatePolicy(ListView.UpdatePolicy.ON_DRAW);

            add(view.getMainTable()).colspan(3).grow();
            add(new VisLabel("oke"));

            setSize(300, 300);
            setPosition(458, 245);
        }



        private class CarModelAdapter extends ArrayAdapter<CarModel, VisTable> {
            private final Drawable bg = VisUI.getSkin().getDrawable("window-bg");
            private final Drawable selection = VisUI.getSkin().getDrawable("list-selection");

            public CarModelAdapter (Array<CarModel> array) {
                super(array);
                setSelectionMode(SelectionMode.SINGLE);

//            setItemsSorter(new Comparator<CarModel>() {
//                @Override
//                public int compare (CarModel o1, CarModel o2) {
//                    return o1.getLicense().toLowerCase().compareTo(o2.getLicense().toLowerCase());
//                }
//            });
            }

            @Override
            protected VisTable createView (CarModel item) {
                VisLabel label = new VisLabel(item.getLicense());
                label.setColor(item.getColor());

                VisTable table = new VisTable();
                table.left();
                table.add(label);
                return table;
            }

            @Override
            protected void updateView (VisTable view, CarModel item) {
                super.updateView(view, item);
            }

            @Override
            protected void selectView (VisTable view) {
                view.setBackground(selection);
            }

            @Override
            protected void deselectView (VisTable view) {
                view.setBackground(bg);
            }
        }
    }
}
