package models;

/**
 * Created by Sneeuwpopsneeuw on 17-Jan-18.
 */
public class Floor {
    private FloorType[][] floorNodes;

    public FloorType getFloorTypeAt(int x, int y) {
        return floorNodes[y][x];
    }

    public int getColmns() { return floorNodes.length; }
    public int getRows() { return floorNodes[0].length; }

    public Floor() {
        this.floorNodes = new FloorType[][]    {{FloorType.PARKABLE,    FloorType.PARKABLE, FloorType.PARKABLE},
                                                {FloorType.ROAD,        FloorType.ROAD,     FloorType.PARKABLE},
                                                {FloorType.PARKABLE,    FloorType.PARKABLE, FloorType.PARKABLE}};
    }
}