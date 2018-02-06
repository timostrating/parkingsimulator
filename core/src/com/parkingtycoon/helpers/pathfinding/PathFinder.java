package com.parkingtycoon.helpers.pathfinding;

import com.parkingtycoon.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * The PathFinder is a static class that uses the A*-algorithm to find a path between 2 points on a grid.
 * It needs a NavMap to navigate.
 */
public class PathFinder {

    /**
     * A node is where a Path consists of
     */
    public static class Node {
        public int x, y;
        public float actualX, actualY;

        private Node parent;
        private boolean open = true;
        private int h;

        /**
         * This constructor makes a Node that can be placed in a Path
         *
         * @param x      The x-position of the node
         * @param y      The y-position of the node
         * @param parent The node before this node
         * @param h      Heuristic, the minimal estimated cost to travel from here to the goal
         */
        private Node(int x, int y, Node parent, int h) {
            this.x = x;
            this.y = y;
            actualX = x;
            actualY = y;
            this.parent = parent;
            this.h = h;
        }

        /**
         * The estimated cost of using this node in the path
         *
         * @param currentX The x-position of the current node
         * @param currentY The y-position ot the current node
         * @return
         */
        private int cost(int currentX, int currentY) {
            int g = currentX != x && currentY != y ? 14 : 10;
            return h + g;
        }

        @Override
        public String toString() {
            return "Node at (" + x + ", " + y + ")";
        }
    }

    private PathFinder() {
    }

    /**
     * This method can calculate a path between 2 points using the A*-algorithm.
     * It needs a navMap to navigate
     *
     * @param navMap    A map for navigating
     * @param fromX     From x-coordinate
     * @param fromY     From y-coordinate
     * @param toX       To x-coordinate
     * @param toY       To y-coordinate
     * @return          The found path
     */
    public static List<Node> calcPath(NavMap navMap, int fromX, int fromY, int toX, int toY) {

        if (!navMap.open(fromX, fromY, true, false) || !navMap.open(toX, toY, false, true))
            return null; // impossible goal

        Node[][] nodes = new Node[Game.WORLD_WIDTH][];

        nodes[fromX] = new Node[Game.WORLD_HEIGHT];
        Node current = new Node(fromX, fromY, null, 0);
        nodes[fromX][fromY] = current; // the very first node of the path

        while (!(current.x == toX && current.y == toY)) {

            current.open = false;

            // create a node for each neighbour of the current node:
            for (int x = current.x - 1 < 0 ? 0 : current.x - 1;
                 x < current.x + 2 && x < Game.WORLD_WIDTH;
                 x++) {

                for (int y = current.y - 1 < 0 ? 0 : current.y - 1;
                     y < current.y + 2 && y < Game.WORLD_HEIGHT;
                     y++) {

                    if (x == current.x && y == current.y
                            || (nodes[x] != null && nodes[x][y] != null))
                        continue; // node already exists.

                    if (navMap.open(x, y, false, x == toX && y == toY)) {

                        if (!navMap.allowDiagonalPaths && x != current.x && y != current.y)
                            continue; // continue if diagonal paths are not allowed

                        int h = (Math.abs(x - toX) + Math.abs(y - toY)) * 10 + navMap.avoidScore(x, y) * 20;

                        Node newNode = new Node(x, y, current, h);

                        if (nodes[x] == null)
                            nodes[x] = new Node[Game.WORLD_HEIGHT];

                        nodes[x][y] = newNode;

                    }
                }
            }

            Node bestNode = null;
            int bestCost = 0;

            for (int x = 0; x < Game.WORLD_WIDTH; x++) {

                if (nodes[x] == null)
                    continue;

                for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                    Node node = nodes[x][y];
                    if (node == null || !node.open)
                        continue;

                    int nodeCost = node.cost(current.x, current.y);
                    if (bestNode == null || nodeCost < bestCost) {
                        bestNode = node;
                        bestCost = nodeCost;
                    }

                }
            }

            if (bestNode == null)
                return null; // impossible goal.

            current = bestNode;
        }

        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(0, current);
            current = current.parent;
        }

        return path;
    }

}
