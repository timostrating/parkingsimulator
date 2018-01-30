package com.parkingtycoon.controllers;

import com.parkingtycoon.models.FinancialModel;

/**
 * This class is responsible for processing the money of the player.
 */
public class FinancialController extends BaseController {
    private FinancialModel model = new FinancialModel();

    public FinancialController() {
        super();
        model.setAmount(10_000);
    }

    public boolean spend(float min) {
        if (model.getAmount() - min >= 0) {
            model.setAmount(model.getAmount() - min); ;
            return true;
        }

        return false;
    }

    public float getAmount() {
        return model.getAmount();
    }

    public void addAmount(float add) {
        model.setAmount(model.getAmount() + add); ;
    }

    public FinancialModel getModel() {
        return model;
    }

}
