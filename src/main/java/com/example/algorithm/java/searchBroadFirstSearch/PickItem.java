package com.example.algorithm.java.searchBroadFirstSearch;

public class PickItem {
    private static final int MAX = 101;
    private boolean[][] board = new boolean[MAX][MAX];
    private int[][] direction = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public int solution(int[][] rectangles, int characterX, int characterY, int itemX, int itemY) {
        initializeBoard(rectangles);
        int startRow = characterY * 2;
        int startCol = characterX * 2;
        int endRow = itemY * 2;
        int endCol = itemX * 2;

        int totalDistance = findDistance(startRow, startCol, startRow, startCol, new boolean[MAX][MAX], 0) + 1;
        int distance = findDistance(startRow, startCol, endRow, endCol, new boolean[MAX][MAX], 0);

        return Math.min(distance, totalDistance - distance) / 2;
    }

    private void initializeBoard(int[][] rectangles) {
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

    private void markEdge(int firstRow, int firstCol, int secondRow, int secondCol) {
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

    private void markSpace(int firstRow, int firstCol, int secondRow, int secondCol) {
        for (int row = firstRow + 1; row < secondRow; row++) {
            for (int col = firstCol + 1; col < secondCol; col++) {
                board[row][col] = false;
            }
        }
    }

    private int findDistance(int row, int col, int dstRow, int dstCol, boolean[][] visited, int count) {
        if (count > 0 && row == dstRow && col == dstCol) {
            return count;
        }

        visited[row][col] = true;

        for (int i = 0; i < 4; i++) {
            int newRow = row + direction[i][0];
            int newCol = col + direction[i][1];

            if (newRow >= 0 && newRow < MAX && newCol >= 0 && newCol < MAX) {
                if (board[newRow][newCol] && !visited[newRow][newCol]) {
                    return findDistance(newRow, newCol, dstRow, dstCol, visited, count + 1);
                }
            }
        }

        return count;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(17,
    //             solution(new int[][] { { 1, 1, 7, 4 }, { 3, 2, 5, 5 }, { 4, 3, 6, 9 }, { 2, 6, 8, 8 } }, 1, 3, 7, 8));
    //     Assertions.assertEquals(11,
    //             solution(new int[][] { { 1, 1, 8, 4 }, { 2, 2, 4, 9 }, { 3, 6, 9, 8 }, { 6, 3, 7, 7 } }, 9, 7, 6, 1));
    // }
}
