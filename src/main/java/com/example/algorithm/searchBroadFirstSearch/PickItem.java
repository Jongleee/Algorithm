package com.example.algorithm.searchBroadFirstSearch;

public class PickItem {

    static boolean[][] board = new boolean[101][101];

    int[][] direction = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        //new int[][]{{1, 1, 8, 4}, {2, 2, 4, 9}, {3, 6, 9, 8}, {6, 3, 7, 7}}, 9, 7, 6, 1

        int firstRow = characterY * 2;
        int firstCol = characterX * 2;
        int lastRow = itemY * 2;
        int lastCol = itemX * 2;

        markRectangle(rectangle);

        int totalDistance = findDistance(firstRow, firstCol, firstRow, firstCol, new boolean[101][101], 0) + 1;
        int distance = findDistance(firstRow, firstCol, lastRow, lastCol, new boolean[101][101], 0);

        return Math.min(distance, totalDistance - distance) / 2;
    }

    private void markRectangle(int[][] rectangles) {
        for (int[] rectangle : rectangles) {
            int firstRow = 2 * rectangle[1];
            int firstCol = 2 * rectangle[0];
            int secondRow = 2 * rectangle[3];
            int secondCol = 2 * rectangle[2];

            markEdge(firstRow, firstCol, secondRow, secondCol);
        }

        for (int[] rectangle : rectangles) {
            int firstRow = 2 * rectangle[1];
            int firstCol = 2 * rectangle[0];
            int secondRow = 2 * rectangle[3];
            int secondCol = 2 * rectangle[2];

            markSpace(firstRow, firstCol, secondRow, secondCol);
        }
    }

    private static void markEdge(int firstRow, int firstCol, int secondRow, int secondCol) {
        for (int row = firstRow; row <= secondRow; row++) {
            board[row][firstCol] = true;
        }
        for (int col = firstCol + 1; col <= secondCol; col++) {
            board[secondRow][col] = true;
        }
        for (int row = secondRow - 1; row >= firstRow; row--) {
            board[row][secondCol] = true;
        }
        for (int col = secondCol - 1; col > firstCol; col--) {
            board[firstRow][col] = true;
        }
    }

    private static void markSpace(int firstRow, int firstCol, int secondRow, int secondCol) {
        for (int row = firstRow + 1; row < secondRow; row++) {
            for (int col = firstCol + 1; col < secondCol; col++) {
                board[row][col] = false;
            }
        }
    }

    // DFS
    private int findDistance(int row, int col, final int dstRow, final int dstCol, final boolean[][] visited, int count) {
        if (count > 0 && row == dstRow && col == dstCol) {
            return count;
        }

        visited[row][col] = true;

        for (int i = 0; i < 4; i++) {
            int newRow = row + direction[i][0];
            int newCol = col + direction[i][1];

            if (newRow >= 0 && newRow < 101 && newCol >= 0 && newCol < 101) {
                if (board[newRow][newCol] && !visited[newRow][newCol]) {
                    return findDistance(newRow, newCol, dstRow, dstCol, visited, count + 1);
                }
            }
        }

        return count;
    }
}
