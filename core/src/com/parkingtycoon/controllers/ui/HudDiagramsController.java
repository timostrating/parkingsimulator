package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.UpdateableController;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.ui.DiagramModel;
import com.parkingtycoon.views.BaseView;
import com.parkingtycoon.views.ui.HudDiagramsView;

/**
 * This Controller is responsible for setting up diagrams
 */
public class HudDiagramsController extends UpdateableController implements Showable {

    private final CompositionRoot root;
    private DiagramModel diagramMoney = new DiagramModel("Moneys diagram ???", DiagramModel.DiagramModelType.MONEY, Color.GREEN);
    private DiagramModel diagramTotalCars = new DiagramModel("Cars diagram ???", DiagramModel.DiagramModelType.TOTAL_CARS, Color.RED);
    private DiagramModel diagramAdHocCars = new DiagramModel("Cars diagram ???", DiagramModel.DiagramModelType.ADHOC_CARS, new Color(.1f, 1, .3f, 1));
    private DiagramModel diagramReservedCars = new DiagramModel("Cars diagram ???", DiagramModel.DiagramModelType.RESERVED_CARS, new Color(.1f, .2f, 1, 1));
    private DiagramModel diagramVipCars = new DiagramModel("Cars diagram ???", DiagramModel.DiagramModelType.VIP_CARS, Color.PINK);


    public HudDiagramsController(Stage stage) {
        root = CompositionRoot.getInstance();
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
            if (!carModel.parked)
                continue;
            if (carModel.carType == CarModel.CarType.AD_HOC)
                adHocs++;
            if (carModel.carType == CarModel.CarType.RESERVED)
                reserveds++;
            if (carModel.carType == CarModel.CarType.VIP)
                vips++;
        }
        diagramTotalCars.addToHistory(root.carsController.pathFollowers.size());
        diagramAdHocCars.addToHistory(adHocs);
        diagramReservedCars.addToHistory(reserveds);
        diagramVipCars.addToHistory(vips);
    }

    @Override
    public void show(Stage stage) {
        registerToModels(new HudDiagramsView(stage, diagramMoney, diagramTotalCars, diagramAdHocCars, diagramReservedCars, diagramVipCars));
    }
}
