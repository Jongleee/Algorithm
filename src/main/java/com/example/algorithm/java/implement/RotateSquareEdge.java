package com.example.algorithm.java.implement;

public class RotateSquareEdge {
    public int[] solution(int rows, int columns, int[][] queries) {
        int[][] square = initSquare(rows, columns);
        return rotateNums(square, queries);
    }

    public int[][] initSquare(int rows, int columns) {
        int[][] square = new int[rows][columns];
        int value = 1;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                square[i][j] = value;
                value++;
            }
        }
        
        return square;
    }

    public int[] rotateNums(int[][] square, int[][] queries) {
        int[] answer = new int[queries.length];
        int index = 0;

        for (int[] query : queries) {
            int x1 = query[0] - 1;
            int y1 = query[1] - 1;
            int x2 = query[2] - 1;
            int y2 = query[3] - 1;
            int firstNum = square[x1][y2];
            int min = firstNum;

            for (int i = y2 - 1; i >= y1; i--) {
                min = Math.min(min, square[x1][i]);
                square[x1][i + 1] = square[x1][i];
            }

            for (int i = x1 + 1; i <= x2; i++) {
                min = Math.min(min, square[i][y1]);
                square[i - 1][y1] = square[i][y1];
            }

            for (int i = y1 + 1; i <= y2; i++) {
                min = Math.min(min, square[x2][i]);
                square[x2][i - 1] = square[x2][i];
            }

            for (int i = x2 - 1; i >= x1; i--) {
                min = Math.min(min, square[i][y2]);
                square[i + 1][y2] = square[i][y2];
            }

            square[x1 + 1][y2] = firstNum;
            answer[index] = min;
            index++;
        }

        return answer;
    }
}
