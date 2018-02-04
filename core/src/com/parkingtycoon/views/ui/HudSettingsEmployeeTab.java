package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

public class HudSettingsEmployeeTab extends Tab {

    public IntSpinnerModel employeesAmount = new IntSpinnerModel(15, 0, 999);
    public Spinner employeesSpinner = new Spinner("Employees", employeesAmount);
    public IntSpinnerModel employeesPrice = new IntSpinnerModel(5000, 0, 99999, 100);
    public Spinner employeesPriceSpinner = new Spinner("Employees", employeesPrice);


    public HudSettingsEmployeeTab() {
        super(false, false);
    }

    @Override
    public String getTabTitle () {
        return "Employees";
    }

    @Override
    public Table getContentTable () {
        VisTable table = new VisTable(true);

        table.add(employeesSpinner).growX().top().left();
        table.row();
        table.add(employeesPriceSpinner).growX().top().left();

        return table;
    }
}
