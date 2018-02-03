package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class HudTopView {
    public final VisTextButton button = new VisTextButton("Pause", "toggle");
    public final VisSlider speedSlider = new VisSlider(10, 1000, 1, false);
    public final VisTextButton floorUp = new VisTextButton("^");
    public final VisLabel floorTitle = new VisLabel("Floor: XX");
    public final VisTextButton floorDown = new VisTextButton("v");;

    public final VisTextButton deletedButton = new VisTextButton("delete", "toggle");
    public final VisTextButton buildButton = new VisTextButton("build");
    public final VisTextButton carsButton = new VisTextButton("Cars");
    public final VisTextButton settings = new VisTextButton("Settings");

    public HudTopView(Stage stage) {
        VisTable table = new VisTable();
//        table.setDebug(debug, true);
        table.setFillParent(true);
        table.pad(20);

        table.add(getLeftTable()).expand().top().left();
        table.add(getRightTable()).expand().top().right();

        stage.addActor(table);
    }

    private VisTable getLeftTable() {
        VisTable table = new VisTable(true);
        table.add(button).padRight(20);
        table.add(new VisLabel("speed")).padRight(5);
        table.add(speedSlider).padRight(50);

        table.add(floorUp).padRight(10);
        table.add(floorTitle).padRight(10);
        table.add(floorDown);
        return table;
    }

    private VisTable getRightTable() {
        VisTable table = new VisTable(true);
        table.add(deletedButton).padLeft(10);
        table.add(buildButton).padLeft(10);
        table.add(carsButton).padLeft(50);
        table.add(settings).padLeft(50);
        return table;
    }

}
