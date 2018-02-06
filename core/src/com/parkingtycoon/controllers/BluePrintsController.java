package com.parkingtycoon.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.CoordinateRotater;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BluePrintModel;
import com.parkingtycoon.models.BuildingModel;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.views.BluePrintView;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * The responsibility of this class is to place buildings on the current floor.
 */
public class BluePrintsController extends UpdatableController {

    // Threadsafe
    private BluePrintModel nextToBeBuilt;
    private boolean demolishMode;

    private BluePrintModel toBeBuilt;
    private BluePrintView bluePrintView;
    private BuildingModel toBeDemolished;
    private boolean clicked;

    private final CompositionRoot root = CompositionRoot.getInstance();

    public ArrayList<BuildingModel> buildings = new ArrayList<>();
    public ArrayList<BluePrintModel> bluePrints = new ArrayList<BluePrintModel>() {{

        add(new BluePrintModel(
                // title:
                "Entrance",
                // description:
                "Your garage needs at least one entrance.",
                // uiImagePath
                "sprites/entrance",
                // sprite:
                "sprites/queueBluePrint", 4, 5,
                // price:
                100,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD, FloorModel.FloorType.GRASS, FloorModel.FloorType.CONCRETE),
                // Floor that will appear under the building:
                new FloorModel.FloorType[][]{
                        {null, FloorModel.FloorType.ROAD, null},
                        {FloorModel.FloorType.GRASS, FloorModel.FloorType.BARRIER, FloorModel.FloorType.GRASS},
                        {null, FloorModel.FloorType.ROAD, null}
                },
                // builder:
                (x, y, angle, floor) -> root.entrancesController.createEntrance(x, y, angle, floor, CarModel.CarType.AD_HOC),
                // build on all floors at once:
                false,
                // on demolish:
                building -> root.entrancesController.removeQueue(building)
        ));

        add(new BluePrintModel(
                // title:
                "V.I.P. Entrance",
                // description:
                "Build a special entrance for parking-subscribers.",
                // uiImagePath
                "sprites/vip",
                // sprite:
                "sprites/queueBluePrint", 4, 5,
                // price:
                1000,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD, FloorModel.FloorType.GRASS, FloorModel.FloorType.CONCRETE),
                // Floor that will appear under the building:
                new FloorModel.FloorType[][]{
                        {null, FloorModel.FloorType.ROAD, null},
                        {FloorModel.FloorType.GRASS, FloorModel.FloorType.BARRIER, FloorModel.FloorType.GRASS},
                        {null, FloorModel.FloorType.ROAD, null}
                },
                // builder:
                (x, y, angle, floor) -> root.entrancesController.createEntrance(x, y, angle, floor, CarModel.CarType.VIP),
                // build on all floors at once:
                false,
                // on demolish:
                building -> root.entrancesController.removeQueue(building)
        ));

        add(new BluePrintModel(
                // title:
                "Reserved entrance",
                // description:
                "Build a special entrance for people who have reserved a place.",
                // uiImagePath
                "sprites/vip",
                // sprite:
                "sprites/queueBluePrint", 4, 5,
                // price:
                1000,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD, FloorModel.FloorType.GRASS, FloorModel.FloorType.CONCRETE),
                // Floor that will appear under the building:
                new FloorModel.FloorType[][]{
                        {null, FloorModel.FloorType.ROAD, null},
                        {FloorModel.FloorType.GRASS, FloorModel.FloorType.BARRIER, FloorModel.FloorType.GRASS},
                        {null, FloorModel.FloorType.ROAD, null}
                },
                // builder:
                (x, y, angle, floor) -> root.entrancesController.createEntrance(x, y, angle, floor, CarModel.CarType.RESERVED),
                // build on all floors at once:
                false,
                // on demolish:
                building -> root.entrancesController.removeQueue(building)
        ));

        add(new BluePrintModel(
                // title:
                "Exit",
                // description:
                "This is where customers pay and leave",
                // uiImagePath
                "sprites/exit",
                // sprite:
                "sprites/queueBluePrint", 4, 5,
                // price:
                200,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD, FloorModel.FloorType.GRASS, FloorModel.FloorType.CONCRETE),
                // Floor that will appear under the building:
                new FloorModel.FloorType[][]{
                        {null, FloorModel.FloorType.ROAD, null},
                        {FloorModel.FloorType.GRASS, FloorModel.FloorType.BARRIER, FloorModel.FloorType.GRASS},
                        {null, FloorModel.FloorType.ROAD, null}
                },
                // builder:
                (x, y, angle, floor) -> root.exitsController.createExit(x, y, angle, floor),
                // build on all floors at once:
                false,
                // on demolish:
                building -> root.exitsController.removeQueue(building)
        ));

        add(new BluePrintModel(
                // title:
                "Car elevator",
                // description:
                "With elevators cars can switch floors.",
                // uiImagePath
                "sprites/entrance",
                // sprite:
                "sprites/elevatorBluePrint", 3, 4,
                // price:
                200,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD, FloorModel.FloorType.GRASS, FloorModel.FloorType.CONCRETE),
                // Floor that will appear under the building:
                new FloorModel.FloorType[][]{
                        {FloorModel.FloorType.CONCRETE, FloorModel.FloorType.CONCRETE, FloorModel.FloorType.CONCRETE},
                        {FloorModel.FloorType.CONCRETE, FloorModel.FloorType.ROAD, FloorModel.FloorType.CONCRETE},
                        {FloorModel.FloorType.CONCRETE, FloorModel.FloorType.ROAD, FloorModel.FloorType.CONCRETE},
                        {FloorModel.FloorType.CONCRETE, FloorModel.FloorType.ROAD, FloorModel.FloorType.CONCRETE},
                },
                // builder:
                (x, y, angle, floor) -> root.elevatorsController.createElevator(x, y, angle),
                // build on all floors at once:
                true,
                // on demolish:
                building -> root.elevatorsController.removeElevator(building),
                // only allowed angles:
                0, 3
        ));

    }};

    public BluePrintsController() {
        super();

        root.inputController.onKeyDown.put(Input.Keys.R, () -> {
            if (nextToBeBuilt == null)
                return false;

            boolean next = false, found = false;
            for (int angle : nextToBeBuilt.allowedAngles) {
                if (next) {
                    toBeBuilt.setAngle(angle);
                    found = true;
                    break;
                }
                next = angle == nextToBeBuilt.getAngle();
            }
            if (!found)
                toBeBuilt.setAngle(toBeBuilt.allowedAngles[0]);

            return true;
        });

        root.inputController.onKeyDown.put(Input.Keys.ESCAPE, () -> {
            if (nextToBeBuilt == null)
                return false;

            nextToBeBuilt = null;
            return true;
        });

        root.inputController.onMouseButtonDown.add((screenX, screenY, button) -> {
            if (demolishMode || nextToBeBuilt != null) {
                if (button == 0)
                    clicked = true;
                if (button == 1)
                    nextToBeBuilt = null;
                else
                    return false;
                return true;
            }
            return false;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_1, () -> {
            nextToBeBuilt = bluePrints.get(0);
            return true;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_2, () -> {
            nextToBeBuilt = bluePrints.get(2);
            return true;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_7, () -> {
            nextToBeBuilt = bluePrints.get(1);
            return true;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_8, () -> {
            nextToBeBuilt = bluePrints.get(3);
            return true;
        });

    }

    private void unsetToBeBuilt() {
        if (toBeBuilt != null) {
            toBeBuilt.setActive(false);
            toBeBuilt.unregisterView(bluePrintView);
            toBeBuilt.unregisterView(root.floorsController.view);
            bluePrintView = null;
            toBeBuilt = null;
        }
    }

    private void setToBeBuilt(BluePrintModel bluePrint) {

        toBeBuilt = bluePrint;
        bluePrintView = new BluePrintView(toBeBuilt.spritePath, bluePrint.spriteOriginX, bluePrint.spriteOriginY);
        bluePrintView.show();
        toBeBuilt.setActive(true);
        toBeBuilt.registerView(bluePrintView);
        toBeBuilt.registerView(root.floorsController.view);
        toBeBuilt.setAngle(0);

    }

    public void toggleDemolishMode() {
        demolishMode = !demolishMode;
    }

    public void setNextToBeBuilt(BluePrintModel nextToBeBuilt) {
        this.nextToBeBuilt = nextToBeBuilt;
    }

    @Override
    public void update() {

        if (nextToBeBuilt != toBeBuilt) {
            unsetToBeBuilt();
            if (nextToBeBuilt != null)
                setToBeBuilt(nextToBeBuilt);
        }

        if (toBeBuilt != null) {

            demolishMode = false;

            CompositionRoot root = CompositionRoot.getInstance();

            Vector2 cursor = IsometricConverter.cursorToNormal();
            int x = (int) cursor.x, y = (int) cursor.y;

            boolean canBuild = canBuild(toBeBuilt, x, y);
            toBeBuilt.x = x;
            toBeBuilt.y = y;

            if (clicked && canBuild && root.financialController.spend(toBeBuilt.price)) {

                FloorsController floorsController = root.floorsController;
                int floorIndex = floorsController.getCurrentFloor();
                build(toBeBuilt, x, y, toBeBuilt.getAngle(), floorIndex);

            } else if (clicked && !canBuild) {
                // todo: message: can't build here
            }
        }

        if (demolishMode) {

            Vector2 cursor = IsometricConverter.cursorToNormal();
            int x = (int) cursor.x, y = (int) cursor.y;

            FloorsController floorsController = CompositionRoot.getInstance().floorsController;
            FloorModel floor = floorsController.floors.get(floorsController.getCurrentFloor());

            if (Game.inWorld(x, y) && floor.buildings[x] != null && floor.buildings[x][y] != null) {

                BuildingModel newToBeDemolished = floor.buildings[x][y];

                if (toBeDemolished != newToBeDemolished)
                    unsetToBeDemolished();

                toBeDemolished = newToBeDemolished;
                toBeDemolished.setToBeDemolished(true);

                if (clicked) {
                    demolish(toBeDemolished);
                    toBeDemolished = null;
                }

            } else unsetToBeDemolished();
        } else unsetToBeDemolished();

        clicked = false;
    }

    private void unsetToBeDemolished() {
        if (toBeDemolished == null)
            return;
        toBeDemolished.setToBeDemolished(false);
        toBeDemolished = null;
    }

    /**
     * Checks if a building can be build on a given position
     *
     * @param bluePrint The bluePrint of the building
     * @param originX   The x-position of the building
     * @param originY   The y-position of the building
     * @return Can the building be build here?
     */
    public boolean canBuild(BluePrintModel bluePrint, int originX, int originY) {

        FloorsController floorsController = root.floorsController;
        FloorModel floor = floorsController.floors.get(floorsController.getCurrentFloor());

        boolean canBuild = true;
        int width = bluePrint.tiles.length, height = bluePrint.tiles[0].length;
        bluePrint.validTiles = new boolean[width][];

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                if (bluePrint.tiles[x][y] == null)
                    continue;

                int worldX = CoordinateRotater.rotate(x, width, y, height, bluePrint.getAngle()) + originX;
                int worldY = CoordinateRotater.rotate(y, height, x, width, bluePrint.getAngle()) + originY;

                boolean canBuildHere =

                        // cannot build in noBuildZone:
                        FloorsController.inBuildZone(worldX, worldY)

                                // can building be built on this floorType?
                                && bluePrint.canBuildOn.contains(floor.tiles[worldX][worldY])

                                // is there already another building here?
                                && (floor.buildings[worldX] == null || floor.buildings[worldX][worldY] == null);

                canBuild &= canBuildHere;

                if (bluePrint.validTiles[x] == null)
                    bluePrint.validTiles[x] = new boolean[height];

                bluePrint.validTiles[x][y] = canBuildHere;
            }
        }

        bluePrint.setCanBuild(canBuild);
        return canBuild;
    }

    public void build(BluePrintModel bluePrint, int originX, int originY, int angle, int floorIndex) {

        bluePrint.setAngle(angle);
        BuildingModel building = bluePrint.builder.build(originX, originY, angle, floorIndex);
        building.bluePrint = bluePrint;
        buildings.add(building);

        FloorsController floorsController = root.floorsController;

        if (bluePrint.buildOnAllFloors)
            for (int i = 0; i < floorsController.floors.size(); i++)
                placeBuildingOnFloor(building, bluePrint, i, originX, originY);

        else
            placeBuildingOnFloor(building, bluePrint, floorIndex, originX, originY);
    }

    private void placeBuildingOnFloor(
            BuildingModel building,
            BluePrintModel bluePrint,
            int floorIndex,
            int originX, int originY) {

        Boolean[][] tilesChanged = new Boolean[Game.WORLD_WIDTH][];

        CompositionRoot root = CompositionRoot.getInstance();
        FloorModel floor = root.floorsController.floors.get(floorIndex);

        int width = bluePrint.tiles.length, height = bluePrint.tiles[0].length;

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                FloorModel.FloorType floorType = bluePrint.tiles[x][y];
                if (floorType == null)
                    continue;

                int worldX = CoordinateRotater.rotate(x, width, y, height, bluePrint.getAngle()) + originX;
                int worldY = CoordinateRotater.rotate(y, height, x, width, bluePrint.getAngle()) + originY;

                if (floor.buildings[worldX] == null)
                    floor.buildings[worldX] = new BuildingModel[Game.WORLD_HEIGHT];

                if (tilesChanged[worldX] == null)
                    tilesChanged[worldX] = new Boolean[Game.WORLD_HEIGHT];

                BuildingModel currentBuilding = floor.buildings[worldX][worldY];
                if (currentBuilding != null)
                    demolish(currentBuilding);

                floor.buildings[worldX][worldY] = building;
                tilesChanged[worldX][worldY] = true;

                floor.setTile(worldX, worldY, floorType);

            }
        }

        root.floorsController.updateBuildings(floorIndex);
        root.carsController.onTerrainChange(floorIndex, tilesChanged);
    }

    public void demolish(BuildingModel building) {

        FloorsController floorsController = CompositionRoot.getInstance().floorsController;

        if (building.onAllFloors)
            for (int i = 0; i < floorsController.floors.size(); i++)
                removeBuildingFromFloor(
                        building,
                        i
                );

        else
            removeBuildingFromFloor(
                    building,
                    building.floor
            );

        buildings.remove(building);
        building.bluePrint.demolisher.demolish(building);
        building.setDemolished(true);
        demolishMode = false;
    }

    private void removeBuildingFromFloor(BuildingModel building, int floorIndex) {

        Boolean[][] tilesChanged = new Boolean[Game.WORLD_WIDTH][];

        CompositionRoot root = CompositionRoot.getInstance();
        FloorModel floor = root.floorsController.floors.get(floorIndex);

        for (int x = 0; x < Game.WORLD_WIDTH; x++) {
            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {
                if (floor.buildings[x] != null && floor.buildings[x][y] == building) {

                    if (tilesChanged[x] == null)
                        tilesChanged[x] = new Boolean[Game.WORLD_HEIGHT];

                    floor.setTile(x, y, floorIndex == 0 ? FloorModel.FloorType.GRASS : FloorModel.FloorType.CONCRETE);
                    floor.buildings[x][y] = null;

                    tilesChanged[x][y] = true;
                }
            }
        }

        root.carsController.onTerrainChange(floorIndex, tilesChanged);
    }

}
