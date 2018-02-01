package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.util.adapter.AbstractListAdapter;
import com.kotcrab.vis.ui.util.adapter.ArrayAdapter;
import com.kotcrab.vis.ui.util.adapter.ListSelectionAdapter;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.ListView.ItemClickListener;
import com.kotcrab.vis.ui.widget.ListView.UpdatePolicy;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.models.CarModel;

/**
 * This Class is a temporary class for testing the possibilities of a lists and windows.  TODO: Change into a usable class
 */
public class TestListView extends VisWindow {
    public TestListView () {
        super("Cars");

        TableUtils.setSpacingDefaults(this);
        columnDefaults(0).left();

        addCloseButton();
        setResizable(true);
        closeOnEscape();

//        Array<CarModel> array = new Array<CarModel>();
//        for (int i = 1; i <= 9; i++) {
//            array.add(new CarModel(LicenceGenerator.getRandomLicencePlate(), Color.WHITE));
//        }

        Array<CarModel> array = new Array<CarModel>();


        final TestAdapter adapter = new TestAdapter(array);
        ListView<CarModel> view = new ListView<CarModel>(adapter);
        view.setUpdatePolicy(UpdatePolicy.ON_DRAW);

        add(view.getMainTable()).colspan(3).grow();

        adapter.setSelectionMode(AbstractListAdapter.SelectionMode.SINGLE);
        view.setItemClickListener(new ItemClickListener<CarModel>() {
            @Override
            public void clicked (CarModel item) {
                System.out.println("Clicked: " + item.getLicense());
            }
        });
        adapter.getSelectionManager().setListener(new ListSelectionAdapter<CarModel, VisTable>() {
            @Override
            public void selected (CarModel item, VisTable view) {
                System.out.println("ListSelection Selected: " + item.getLicense());
            }

            @Override
            public void deselected (CarModel item, VisTable view) {
                System.out.println("ListSelection Deselected: " + item.getLicense());
            }
        });

        setSize(300, 300);
        setPosition(458, 245);
    }


    private static class TestAdapter extends ArrayAdapter<CarModel, VisTable> {
        private final Drawable bg = VisUI.getSkin().getDrawable("window-bg");
        private final Drawable selection = VisUI.getSkin().getDrawable("list-selection");

        public TestAdapter (Array<CarModel> array) {
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