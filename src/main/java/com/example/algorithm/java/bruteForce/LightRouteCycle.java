package com.example.algorithm.java.bruteForce;

import java.util.ArrayList;
import java.util.List;

public class LightRouteCycle {
    public int[] solution(String[] grid) {
        int rows = grid.length;
        int cols = grid[0].length();
    
        boolean[][][] visited = new boolean[rows][cols][4];
        List<Integer> distances = new ArrayList<>();
    
        int[] directionsRow = {-1, 0, 1, 0};
        int[] directionsCol = {0, -1, 0, 1};
    
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                for (int dir = 0; dir < 4; dir++) {
                    if (!visited[row][col][dir]) {
                        int distance = explore(grid, row, col, dir, visited, directionsRow, directionsCol);
                        distances.add(distance);
                    }
                }
            }
        }
    
        return distances.stream().sorted().mapToInt(Integer::intValue).toArray();
    }
    
    private static int explore(String[] grid, int row, int col, int dir, boolean[][][] visited, int[] directionsRow, int[] directionsCol) {
        int distance = 0;
        while (!visited[row][col][dir]) {
            distance++;
            visited[row][col][dir] = true;
    
            char cell = grid[row].charAt(col);
            if (cell == 'L') {
                dir = (dir + 3) % 4;
            } else if (cell == 'R') {
                dir = (dir + 1) % 4;
            }
    
            row = (row + directionsRow[dir] + grid.length) % grid.length;
            col = (col + directionsCol[dir] + grid[0].length()) % grid[0].length();
        }
        return distance;
    }
}
