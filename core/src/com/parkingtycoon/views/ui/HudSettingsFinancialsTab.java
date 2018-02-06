package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

/**
 * This class in responsible for processing the Financials tab in the settings menu.
 */
public class HudSettingsFinancialsTab extends Tab {

    public IntSpinnerModel adhockTickedPrice = new IntSpinnerModel(100, 0, 999);
    public Spinner adhockSpinner = new Spinner("Adhock ticked price", adhockTickedPrice);
    public IntSpinnerModel reservedTickedPrice = new IntSpinnerModel(200, 0, 999);
    public Spinner reservedSpinner = new Spinner("Reserved ticked price", reservedTickedPrice);
    public IntSpinnerModel vipMembersAmount = new IntSpinnerModel(50, 0, 9999);
    public Spinner vipAmountSpinner = new Spinner("Amount of VIP memberships", vipMembersAmount);
    public IntSpinnerModel vipMemberPrice = new IntSpinnerModel(500, 0, 999);
    public Spinner vipPriceSpinner = new Spinner("VIP membership price", vipMemberPrice);


    public HudSettingsFinancialsTab() {
        super(false, false);  // the user can not close this tab
    }

    /**
     * The title of this tab
     * @return the title that is placed in the tab header
     */
    @Override
    public String getTabTitle () {
        return "Financials";
    }

    /**
     * Gets the content for the tab. This content will be loaded when you have the tab selected
     * @return
     */
    @Override
    public Table getContentTable () {
        VisTable table = new VisTable(true);

        table.add(adhockSpinner).growX().top().left();
        table.row();
        table.add(reservedSpinner).growX().top().left();
        table.row();
        table.addSeparator();
        table.row();
        table.add(vipAmountSpinner).growX().top().left();
        table.row();
        table.add(vipPriceSpinner).growX().top().left();

        return table;
    }
}
