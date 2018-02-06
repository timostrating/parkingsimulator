package com.parkingtycoon.controllers;

import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.FinancialModel;

/**
 * This class is responsible for processing the money of the player.
 *
 * @author GGG
 */
public class FinancialController extends BaseController {
    private FinancialModel model = new FinancialModel();
    public float adhockTicketPrice = 200;
    public float reservedTicketPrice = 300;

    /**
     * This is the default constructor.
     */
    public FinancialController() {
        super();
        model.setAmount(10_000);
    }

    /**
     * spend a X amount of money if you have enough.
     *
     * @param min the amount you need to pay
     * @return this will return if the transaction was successful.
     */
    public boolean spend(float min) {
        if (model.getAmount() - min >= 0) {
            model.setAmount(model.getAmount() - min);

            return true;
        }

        return false;
    }

    /**
     * Spend X amount of money even if that means that you go negative.
     *
     * @param min the amount you need to pay
     * @param canGoNegative can you go negative if you don't have enough money?
     */
    public void spend(float min, boolean canGoNegative) {
        if (canGoNegative || model.getAmount() - min >= 0)
            model.setAmount(model.getAmount() - min);
    }

    /**
     * Getter for the amount of money you have
     *
     * @return the amount of money you have
     */
    public float getAmount() {
        return model.getAmount();
    }

    /**
     * Add money to your account.
     */
    public void addAmount(float add) {
        model.setAmount(model.getAmount() + add);
    }

    public void addCarPayment(CarModel.CarType carType) {
        if (carType == CarModel.CarType.AD_HOC)
            addAmount(adhockTicketPrice);
        if (carType == CarModel.CarType.RESERVED)
            addAmount(reservedTicketPrice);

        // vips pay monthly
    }

    /**
     * Getter fot the model that this controller is holding
     *
     * @return model that this controller is holding
     */
    public FinancialModel getModel() {
        return model;
    }

}
