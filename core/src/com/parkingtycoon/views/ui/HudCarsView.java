package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.util.adapter.AbstractListAdapter;
import com.kotcrab.vis.ui.util.adapter.ArrayAdapter;
import com.kotcrab.vis.ui.util.adapter.ListSelectionAdapter;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.ui.CarsListModel;
import com.parkingtycoon.views.BaseView;

import java.util.Comparator;

/**
 * This view is responsible for showing a window where the cars are visible in a list. TODO change
 */
public class HudCarsView extends BaseView implements Showable {

    private HudCarsWindowView window;


    public HudCarsView(Stage stage) {
        super.show();
        window = new HudCarsWindowView();
        stage.addActor(window);
    }

    @Override
    public void updateView(BaseModel model) {
        if (model instanceof CarsListModel)
            window.update((CarsListModel)model);
    }

    @Override
    public void preRender() {
        window.render();
    }

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
        private final ListView<CarModel> view;
        private final CompositionRoot root;
        private final CarModelAdapter adapter;

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

            adapter = new CarModelAdapter(genericArray);
            view = new ListView<CarModel>(adapter);
            view.setUpdatePolicy(ListView.UpdatePolicy.IMMEDIATELY);

            VisTable footerTable = new VisTable();
            footerTable.addSeparator();
            footerTable.add("Total Cars: "+root.carsController.pathFollowers.size());
            view.setFooter(footerTable);
//            final VisValidatableTextField nameField = new VisValidatableTextField();
//            VisTextButton addButton = new VisTextButton("Add");

//            SimpleFormValidator validator = new SimpleFormValidator(addButton);
//            validator.notEmpty(nameField, "");

//            add(new VisLabel("New Name:"));
//            add(nameField);
//            add(addButton);
            row();
            add(view.getMainTable()).colspan(3).grow();

//            addButton.addListener(new ChangeListener() {
//                @Override
//                public void changed (ChangeEvent event, Actor actor) {
//                    //by changing array using adapter view will be invalidated automatically
//                    adapter.add(new CarModel(nameField.getText(), Color.GRAY));
//                    nameField.setText("");
//                }
//            });

            adapter.setSelectionMode(AbstractListAdapter.SelectionMode.SINGLE);
            view.setItemClickListener(new ListView.ItemClickListener<CarModel>() {
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

        boolean hasRendered = true;
        public void update(CarsListModel model) {
            if (hasRendered) {
                hasRendered = false;
                view.rebuildView();
            }
        }

        public void render() {
            hasRendered = true;
        }

        private class CarModelAdapter extends ArrayAdapter<CarModel, VisTable> {

            private final Drawable bg = VisUI.getSkin().getDrawable("window-bg");
            private final Drawable selection = VisUI.getSkin().getDrawable("list-selection");
            private VisTable table;


            public CarModelAdapter (Array<CarModel> array) {
                super(array);
                table =  new VisTable(true);
                setSelectionMode(SelectionMode.SINGLE);

                setItemsSorter(new Comparator<CarModel>() {
                    @Override
                    public int compare (CarModel o1, CarModel o2) {
                        return o1.getLicense().toLowerCase().compareTo(o2.getLicense().toLowerCase());
                    }
                });
            }

            @Override
            protected VisTable createView (CarModel item) {
                VisLabel label = new VisLabel(item.getLicense());
//                label.setColor(item.color);

                table.right();
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
