package com.parkingtycoon.views.ui;

import com.badlogic.gdx.graphics.Color;
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
import com.parkingtycoon.helpers.LicenceGenerator;

import java.util.Comparator;

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

        Array<Model> array = new Array<Model>();
        for (int i = 1; i <= 9; i++) {
            array.add(new Model(LicenceGenerator.getRandomLicencePlate(), Color.WHITE));
        }

        final TestAdapter adapter = new TestAdapter(array);
        ListView<Model> view = new ListView<Model>(adapter);
        view.setUpdatePolicy(UpdatePolicy.ON_DRAW);

        add(view.getMainTable()).colspan(3).grow();

        adapter.setSelectionMode(AbstractListAdapter.SelectionMode.SINGLE);
        view.setItemClickListener(new ItemClickListener<Model>() {
            @Override
            public void clicked (Model item) {
                System.out.println("Clicked: " + item.name);
            }
        });
        adapter.getSelectionManager().setListener(new ListSelectionAdapter<Model, VisTable>() {
            @Override
            public void selected (Model item, VisTable view) {
                System.out.println("ListSelection Selected: " + item.name);
            }

            @Override
            public void deselected (Model item, VisTable view) {
                System.out.println("ListSelection Deselected: " + item.name);
            }
        });

        setSize(300, 300);
        setPosition(458, 245);
    }

    private static class Model {
        public String name;
        public Color color;

        public Model (String name, Color color) {
            this.name = name;
            this.color = color;
        }
    }

    private static class TestAdapter extends ArrayAdapter<Model, VisTable> {
        private final Drawable bg = VisUI.getSkin().getDrawable("window-bg");
        private final Drawable selection = VisUI.getSkin().getDrawable("list-selection");

        public TestAdapter (Array<Model> array) {
            super(array);
            setSelectionMode(SelectionMode.SINGLE);

            setItemsSorter(new Comparator<Model>() {
                @Override
                public int compare (Model o1, Model o2) {
                    return o1.name.toLowerCase().compareTo(o2.name.toLowerCase());
                }
            });
        }

        @Override
        protected VisTable createView (Model item) {
            VisLabel label = new VisLabel(item.name);
            label.setColor(item.color);

            VisTable table = new VisTable();
            table.left();
            table.add(label);
            return table;
        }

        @Override
        protected void updateView (VisTable view, Model item) {
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