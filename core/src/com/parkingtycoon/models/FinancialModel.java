package com.parkingtycoon.models;

/**
 * This Class is responsible for storing the financial status of the simulation.
 *
 * @author Timo Strating
 */
public class FinancialModel extends BaseModel {

    private float amount = 10;

    /**
     * SETTER for the amount of money you have
     *
     * @param amount the value you wich to change the amount to.
     */
    public void setAmount(float amount) {
        this.amount = amount;
        notifyViews();
    }

    /**
     * GETTER for the amount of money you have
     */
    public float getAmount() {
        return amount;
    }

}
