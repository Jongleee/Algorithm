package com.example.algorithm.java.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class AStarAlgorithm {
    static class AStarData {
        int x;
        int y;
        int parentIndex;
        int gCost;
        int hCost;
        int fCost;
    
        public AStarData(int x, int y, int parentIndex, int gCost, int hCost) {
            this.x = x;
            this.y = y;
            this.parentIndex = parentIndex;
            this.gCost = gCost;
            this.hCost = hCost;
            this.fCost = gCost + hCost;
        }
    }
    
    private static final int[] directionX = { 0, 0, -1, 1 };
    private static final int[] directionY = { 1, -1, 0, 0 };
    private static List<AStarData> openList = new ArrayList<>();
    private static List<AStarData> closedList = new ArrayList<>();

    public static List<int[]> findShortestPath(int startX, int startY, int endX, int endY) {
        closedList.add(new AStarData(startX, startY, -1, 0, 0));
        int currentIndex = 0;

        while (closedList.get(currentIndex).x != endX || closedList.get(currentIndex).y != endY) {
            AStarData current = closedList.get(currentIndex);
            for (int i = 0; i < directionX.length; i++) {
                int nextX = current.x + directionX[i];
                int nextY = current.y + directionY[i];
                if (isNodeInClosedList(nextX, nextY)) {
                    continue;
                }
                boolean isNodeInOpenList = isNodeInOpenList(nextX, nextY);
                if (!isNodeInOpenList) {
                    openList.add(new AStarData(nextX, nextY, currentIndex, current.gCost + 1,
                            calculateHCost(nextX, nextY, endX, endY)));
                } else {
                    updateGCostIfShorter(current, nextX, nextY);
                }
            }
            if (openList.isEmpty()) {
                break;
            }
            currentIndex = getNextIndex();
        }

        return buildPath(currentIndex);
    }

    private static boolean isNodeInClosedList(int x, int y) {
        return closedList.stream().anyMatch(n -> n.x == x && n.y == y);
    }

    private static boolean isNodeInOpenList(int x, int y) {
        for (AStarData node : openList) {
            if (node.x == x && node.y == y) {
                return true;
            }
        }
        return false;
    }

    private static void updateGCostIfShorter(AStarData current, int nextX, int nextY) {
        for (AStarData node : openList) {
            if (node.x == nextX && node.y == nextY) {
                int newGCost = current.gCost + 1;
                if (newGCost < node.gCost) {
                    node.gCost = newGCost;
                    node.fCost = node.gCost + node.hCost;
                    node.parentIndex = closedList.size() - 1;
                }
                break;
            }
        }
    }

    private static int calculateHCost(int x, int y, int endX, int endY) {
        return Math.abs(endX - x) + Math.abs(endY - y);
    }

    private static int getNextIndex() {
        int lowestIndex = 0;
        for (int i = 0; i < openList.size(); i++) {
            if (openList.get(i).fCost < openList.get(lowestIndex).fCost) {
                lowestIndex = i;
            }
        }
        AStarData current = openList.get(lowestIndex);
        closedList.add(current);
        openList.remove(current);
        return closedList.size() - 1;
    }

    private static List<int[]> buildPath(int currentIndex) {
        List<int[]> path = new ArrayList<>();
        while (currentIndex != -1) {
            AStarData current = closedList.get(currentIndex);
            path.add(new int[] { current.x, current.y });
            currentIndex = current.parentIndex;
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        int startx = 0;
        int starty = 0;
        int endx = 5;
        int endy = 5;
        for (int[] i : findShortestPath(startx, starty, endx, endy)) {
            System.out.println(Arrays.toString(i));
        }

    }
}
