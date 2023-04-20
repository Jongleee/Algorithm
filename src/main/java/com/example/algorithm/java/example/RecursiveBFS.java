package com.example.algorithm.java.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecursiveBFS {
    public static int[] solution(String[] maps) {
        boolean[][] visited = new boolean[maps.length][maps[0].length()];
        List<Integer> territories = new ArrayList<>();

        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[i].length(); j++) {
                int territorySize = getTerritorySize(i, j, visited, maps);
                if (territorySize > 0) {
                    territories.add(territorySize);
                }
            }
        }

        Collections.sort(territories);
        return territories.isEmpty() ? new int[] { -1 } : territories.stream().mapToInt(Integer::intValue).toArray();
    }

    private static int getTerritorySize(int i, int j, boolean[][] visited, String[] maps) {
        if (i < 0 || j < 0 || i >= visited.length || j >= visited[0].length || visited[i][j]
                || maps[i].charAt(j) == 'X') {
            return 0;
        }

        visited[i][j] = true;
        int territorySize = maps[i].charAt(j) - '0';
        territorySize += getTerritorySize(i - 1, j, visited, maps);
        territorySize += getTerritorySize(i + 1, j, visited, maps);
        territorySize += getTerritorySize(i, j - 1, visited, maps);
        territorySize += getTerritorySize(i, j + 1, visited, maps);
        return territorySize;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new String[] { "X591X", "X1X5X", "X231X", "1XXX1" })));
    }
}