package com.parkingtycoon.controllers.ui;

import com.kotcrab.vis.ui.widget.VisTable;

public abstract class HudBaseController {


    protected final VisTable table;

    public HudBaseController() {
        table = new VisTable();
    }

    public VisTable getTable() {
        return table;
    }
}
