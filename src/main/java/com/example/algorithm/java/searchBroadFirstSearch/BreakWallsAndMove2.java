package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BreakWallsAndMove2 {
    static class Position {
        int row;
        int col;
        int moves;

        Position(int row, int col, int moves) {
            this.row = row;
            this.col = col;
            this.moves = moves;
        }
    }

    private static final int[] rowDir = { 1, 0, -1, 0 };
    private static final int[] colDir = { 0, 1, 0, -1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int rows = Integer.parseInt(st.nextToken());
        int cols = Integer.parseInt(st.nextToken());
        int maxBreaks = Integer.parseInt(st.nextToken());

        int[][] grid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = br.readLine();
            for (int j = 0; j < cols; j++) {
                grid[i][j] = line.charAt(j) - '0';
            }
        }

        int[][] brokenWalls = new int[rows][cols];
        for (int[] row : brokenWalls) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        ArrayDeque<Position> queue = new ArrayDeque<>();
        brokenWalls[0][0] = 0;
        queue.add(new Position(0, 0, 1));

        int result = -1;
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            int currentRow = current.row;
            int currentCol = current.col;
            int currentMoves = current.moves + 1;

            if (currentRow == rows - 1 && currentCol == cols - 1) {
                result = current.moves;
                break;
            }

            for (int dir = 0; dir < 4; dir++) {
                int newRow = currentRow + rowDir[dir];
                int newCol = currentCol + colDir[dir];

                if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols)
                    continue;

                int newBreakCount = brokenWalls[currentRow][currentCol] + grid[newRow][newCol];
                if (newBreakCount > maxBreaks || brokenWalls[newRow][newCol] <= newBreakCount)
                    continue;

                brokenWalls[newRow][newCol] = newBreakCount;
                queue.add(new Position(newRow, newCol, currentMoves));
            }
        }

        System.out.print(result);
    }
}

/*
6 4 1
0100
1110
1000
0000
0111
0000

15


6 4 2
0100
1110
1000
0000
0111
0000

9
*/