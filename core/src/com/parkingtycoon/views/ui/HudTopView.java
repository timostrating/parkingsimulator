package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

/**
 * This class is responsible for showing the top of the ui
 */
public class HudTopView {
    public VisTextButton pauseButton = new VisTextButton("Pause", "toggle");
//    public VisTextButton randombutton = new VisTextButton("I");
    public VisTextButton save = new VisTextButton("save");
    public VisSlider speedSlider = new VisSlider(10, 1000, 1, false);
    public VisTextButton floorUp = new VisTextButton("^");
    public VisLabel floorTitle = new VisLabel("Floor: XX");
    public VisTextButton floorDown = new VisTextButton("v");;

    public VisTextButton deletedButton = new VisTextButton("delete");
    public VisTextButton buildButton = new VisTextButton("build");
    public VisTextButton carsButton = new VisTextButton("Cars");
    public VisTextButton settings = new VisTextButton("Settings");

    /**
     * the standard constructor that combines the left and right side in one table.
     *
     * @param stage the stage where the buttons will be added.
     */
    public HudTopView(Stage stage) {
        VisTable table = new VisTable();
//        table.setDebug(debug, true);
        table.setFillParent(true);
        table.pad(20);

        table.add(getLeftTable()).expand().top().left();
        table.add(getRightTable()).expand().top().right();

        stage.addActor(table);
    }

    /**
     * the left part of the UI
     *
     * @return table width all the buttons that are on the left side
     */
    private VisTable getLeftTable() {
        VisTable table = new VisTable(true);

        table.add(pauseButton).padRight(20);
        table.add(new VisLabel("speed")).padRight(5);
        table.add(speedSlider).padRight(50);

        VisTable floorTable = new VisTable(true);
        floorTable.add(floorUp).padRight(10);
        floorTable.add(floorTitle).padRight(10);
        floorTable.add(floorDown);
        table.add(floorTable);

        return table;
    }

    /**
     * The right part of the UI
     *
     * @return table width all the buttons that are on the right side
     */
    private VisTable getRightTable() {
        VisTable table = new VisTable(true);
        table.add(deletedButton).padLeft(10);
        table.add(buildButton).padLeft(10);
        table.add(carsButton).padLeft(50);
        table.add(save).padLeft(50);
        table.add(settings).padLeft(10);
        return table;
    }

}
