package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.parkingtycoon.helpers.Logger;

public class HudSettingsEmployeeTab extends Tab {

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

        final IntSpinnerModel employeesSpinnerModel = new IntSpinnerModel(15, 0, 999);
        Spinner employeesSpinner = new Spinner("Employees", employeesSpinnerModel);
        employeesSpinner.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Logger.info("changed employeesSpinner to: " + employeesSpinnerModel.getValue());
            }
        });

        final IntSpinnerModel employeesPriceSpinnerModel = new IntSpinnerModel(5000, 0, 99999, 100);
        Spinner employeesPriceSpinner = new Spinner("Monthly cost per employee $", employeesPriceSpinnerModel);
        employeesPriceSpinner.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Logger.info("changed employeesPriceSpinner to: " + employeesPriceSpinnerModel.getValue());
            }
        });

        table.add(employeesSpinner).growX().top().left();
        table.row();
        table.add(employeesPriceSpinner).growX().top().left();

        return table;
    }
}
