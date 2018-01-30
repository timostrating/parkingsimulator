package com.parkingtycoon.models;

import com.parkingtycoon.helpers.Logger;

/**
 * This Class is responsible for storing the financial status of the simulation.
 */
public class FinancialModel extends BaseModel {
    private float amount = 10;

    public void setAmount(float amount) {
        this.amount = amount;
        notifyViews();
        Logger.info("mmm");
    }

    public float getAmount() {
        return amount;
    }
}
