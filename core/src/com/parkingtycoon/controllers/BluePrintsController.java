package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BluePrintModel;
import com.parkingtycoon.models.BuildableModel;
import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.views.BluePrintView;

import java.util.ArrayList;
import java.util.EnumSet;

public class BluePrintsController extends UpdateableController {

    private BluePrintModel toBeBuilt;
    private BluePrintView bluePrintView;
    private boolean clicked, unset;

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

        root.inputController.onKeyDown.put(Input.Keys.R, () -> {
            if (toBeBuilt == null || unset)
                return false;

            int newAngle = toBeBuilt.getAngle() + 1;
            toBeBuilt.setAngle(newAngle >= 4 ? 0 : newAngle);
            return true;
        });

        root.inputController.onKeyDown.put(Input.Keys.ESCAPE, () -> {
            if (toBeBuilt == null || unset)
                return false;

            unset = true;
            return true;
        });

        root.inputController.onMouseButtonDown.add((screenX, screenY, button) -> {
            if (toBeBuilt != null && !unset) {
                if (button == 0)
                    clicked = true;
                if (button == 1)
                    unset = true;
                else
                    return false;
                return true;
            }
            return false;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_1, () -> {
            setToBeBuilt(bluePrints.get(0));
            return true;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_2, () -> {
            setToBeBuilt(bluePrints.get(1));
            return true;
        });

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

        if (toBeBuilt != null) {

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

            if (clicked && canBuild && root.financialController.spend(toBeBuilt.price)) {

                build(x, y);

            } else if (clicked && !canBuild) {
                unsetToBeBuilt();
            }

        }

        if (unset)
            unsetToBeBuilt();

        unset = false;
        clicked = false;
    }

    private void build(int x, int y) {
        BuildableModel building = toBeBuilt.builder.build(x, y);
        CompositionRoot.getInstance().floorsController.build(building);
    }
}
