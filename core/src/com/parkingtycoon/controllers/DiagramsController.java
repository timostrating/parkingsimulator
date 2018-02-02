package com.parkingtycoon.controllers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.ui.DiagramModel;
import com.parkingtycoon.views.BaseView;
import com.parkingtycoon.views.ui.HudStatsFinancialView;

public class DiagramsController extends UpdateableController {

    private final CompositionRoot root;
    private DiagramModel diagramMoney = new DiagramModel("Moneys diagram ???", DiagramModel.DiagramModelType.MONEY, Color.GREEN);
    private DiagramModel diagramTotalCars = new DiagramModel("Cars diagram ???", DiagramModel.DiagramModelType.TOTAL_CARS, Color.RED);
    private DiagramModel diagramAdHocCars = new DiagramModel("Cars diagram ???", DiagramModel.DiagramModelType.ADHOC_CARS, Color.BLUE);
    private DiagramModel diagramReservedCars = new DiagramModel("Cars diagram ???", DiagramModel.DiagramModelType.RESERVED_CARS, Color.YELLOW);
    private DiagramModel diagramVipCars = new DiagramModel("Cars diagram ???", DiagramModel.DiagramModelType.VIP_CARS, Color.PINK);


    public DiagramsController(Stage stage) {
        root = CompositionRoot.getInstance();

        HudStatsFinancialView windowView = new HudStatsFinancialView(stage, diagramMoney, diagramTotalCars, diagramAdHocCars, diagramReservedCars, diagramVipCars);
        HudStatsFinancialView windowView2 = new HudStatsFinancialView(stage, diagramMoney, diagramTotalCars, diagramAdHocCars, diagramReservedCars, diagramVipCars);
        HudStatsFinancialView windowView3 = new HudStatsFinancialView(stage, diagramMoney, diagramTotalCars, diagramAdHocCars, diagramReservedCars, diagramVipCars);

        registerToModels(windowView);
        registerToModels(windowView2);
        registerToModels(windowView3);
    }

    private void registerToModels(BaseView view) {
        diagramMoney.registerView(view);
        diagramTotalCars.registerView(view);
        diagramAdHocCars.registerView(view);
        diagramReservedCars.registerView(view);
        diagramVipCars.registerView(view);
    }

    @Override
    public void update() {
        diagramMoney.addToHistory(root.financialController.getAmount());

        long adHocs=0, reserveds=0, vips=0;
        for (CarModel carModel : root.carsController.pathFollowers) {
            if (carModel.carType == CarModel.CarType.AD_HOC)
                adHocs++;
            if (carModel.carType == CarModel.CarType.RESERVED)
                reserveds++;
            if (carModel.vip)
                vips++;
        }
        diagramTotalCars.addToHistory(root.carsController.pathFollowers.size());
        diagramAdHocCars.addToHistory(adHocs);
        diagramReservedCars.addToHistory(reserveds);
        diagramVipCars.addToHistory(vips);
    }

}
