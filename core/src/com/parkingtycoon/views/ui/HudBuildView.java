package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.layout.GridGroup;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.views.BaseView;

/**
 * This is the View that is responsible for showing the Build window
 *
 * @author Timo Strating
 */
public class HudBuildView extends BaseView implements Showable {

    public GridGroup group;
    public VisLabel description;
    public VisLabel price;

    private final HudBuildWindow window;

    /**
     * create the build window
     *
     * @param stage the stage where the window will be added to.
     */
    public HudBuildView(Stage stage) {
        super();
        show();

        window = new HudBuildWindow();
        stage.addActor(window);
    }

    @Override
    public void updateView(BaseModel model) { }

    @Override
    public float renderIndex() {
        return 0;
    }

    /**
     * Show only one window.
     *
     * @param stage the stage where the showed item should be added to
     */
    @Override
    public void show(Stage stage) {
        window.setVisible(true);
        window.setZIndex(999);
        stage.addActor(window);
    }

    /**
     * hide the window when building.
     */
    @Override
    public void hide() {
        window.setZIndex(0);
        window.setVisible(false);
    }

    /**
     * This is the Window where you can build things.
     */
    public class HudBuildWindow extends VisWindow {

        HudBuildWindow() {
            super("Build");

            TableUtils.setSpacingDefaults(this);
            columnDefaults(0).left();

            setResizable(true);
            addCloseButton();

            VisTable table = new VisTable();
            table.setWidth(200);

            group = new GridGroup(100, 4);

            VisScrollPane scrollPane = new VisScrollPane(group);
            scrollPane.setFadeScrollBars(false);
            scrollPane.setFlickScroll(false);
            scrollPane.setOverscroll(false, false);
            scrollPane.setScrollingDisabled(true, false); //disable X scrolling

            table.add(scrollPane).top().grow();
            table.row();

            description = new VisLabel("...");
//            description.setWrap(true);
            price = new VisLabel("...");
//            price.setWrap(true);

            VisTable footerTable = new VisTable();
            footerTable.addSeparator();
            footerTable.row();

            VisTable descriptionTable = new VisTable();  // Description
            descriptionTable.add("Description:  ").left();
            descriptionTable.row();
            descriptionTable.add(description).padRight(20);
//            descriptionTable.set;
            footerTable.add(descriptionTable).left();

            VisTable PriceTable = new VisTable();  // Price
            PriceTable.add("Price:  ").right();
            PriceTable.add(price).right().padRight(20);
            footerTable.add(PriceTable).right().padRight(20);

            table.add(footerTable).bottom().growX();

            add(table).grow();

            setSize(650, 500);
            centerWindow();
        }
    }

}
