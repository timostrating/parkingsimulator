package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.helpers.UpdateableController;
import com.parkingtycoon.models.BluePrintModel;
import com.parkingtycoon.models.BuildableModel;
import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.views.BluePrintView;

import java.util.ArrayList;
import java.util.EnumSet;

public class BluePrintsController extends UpdateableController {

    private BluePrintModel toBeBuilt;
    private BluePrintView bluePrintView;

    public ArrayList<BluePrintModel> bluePrints = new ArrayList<BluePrintModel>() {{

        add(new BluePrintModel(
                // title:
                "Entrance barrier",
                // description:
                "Your garage needs at least one entrance.",
                // sprite path:
                "sprites/entrance",
                // price:
                100,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD),
                // builder:
                (x, y) -> CompositionRoot.getInstance().entrancesController.createEntrance(x, y)
        ));

        add(new BluePrintModel(
                // title:
                "Exit",
                // description:
                "This is where customers pay and leave",
                // sprite path:
                "sprites/entrance",
                // price:
                200,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD),
                // builder:
                (x, y) -> CompositionRoot.getInstance().exitsController.createExit(x, y)
        ));

    }};

    public BluePrintsController() {
        CompositionRoot root = CompositionRoot.getInstance();
        root.simulationController.registerUpdatable(this);

        setToBeBuilt(bluePrints.get(0));
    }

    public void unsetToBeBuilt() {
        if (toBeBuilt != null) {
            toBeBuilt.unregisterView(bluePrintView);
            bluePrintView.hide();
            bluePrintView = null;
            toBeBuilt = null;
        }
    }

    public void setToBeBuilt(BluePrintModel bluePrint) {

        unsetToBeBuilt();
        toBeBuilt = bluePrint;
        bluePrintView = new BluePrintView(toBeBuilt.spritePath);
        bluePrintView.show();
        toBeBuilt.registerView(bluePrintView);
        toBeBuilt.setAngle(0);

    }

    @Override
    public void update() {

        // TODO: Gdx.input not always working on seperate thread!!!

        if (toBeBuilt != null) {

            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                int newAngle = toBeBuilt.getAngle() + 1;
                toBeBuilt.setAngle(newAngle >= 4 ? 0 : newAngle);
            }

            CompositionRoot root = CompositionRoot.getInstance();

            Vector3 isometric = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            root.renderController.getMainCamera().unproject(isometric);

            Vector2 normal = new Vector2(isometric.x, isometric.y);
            IsometricConverter.isometricToNormal(normal);

            int x = (int) normal.x, y = (int) normal.y;

            boolean canBuild = root.floorsController.canBuild(x, y, toBeBuilt.floorTypes);
            toBeBuilt.setCanBuild(canBuild);
            toBeBuilt.x = x;
            toBeBuilt.y = y;

            if (Gdx.input.isButtonPressed(0) && canBuild && root.financialController.spend(toBeBuilt.price)) {

                build(x, y);
                setToBeBuilt(bluePrints.get(Math.random() > .5f ? 0 : 1));
            }

        }
    }

    private void build(int x, int y) {
        BuildableModel building = toBeBuilt.builder.build(x, y);
        CompositionRoot.getInstance().floorsController.build(building);
    }
}
