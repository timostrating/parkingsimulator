package com.parkingtycoon.controllers;

import com.parkingtycoon.models.FinancialModel;

public class FinancialController extends BaseController {
    private FinancialModel model = new FinancialModel();

    public FinancialController() {

    }

    public boolean spend(float min) {
        if (model.amount - min < 0) {
            model.amount -= min;
            return true;
        }

        return false;
    }

    public float getAmount() {
        return model.amount;
    }

    public void addAmount(float add) {
        model.amount += add;
    }
}
