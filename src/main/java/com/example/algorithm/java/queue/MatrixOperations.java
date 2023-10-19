package com.example.algorithm.java.queue;

import java.util.ArrayDeque;

public class MatrixOperations {
    private int numRows;
    private int numCols;
    private ArrayDeque<Integer> leftCol;
    private ArrayDeque<Integer> rightCol;
    private ArrayDeque<ArrayDeque<Integer>> middleRows;

    public int[][] solution(int[][] rc, String[] operations) {
        initialize(rc);

        for (String op : operations) {
            if (op.charAt(0) == 'R') {
                rotate();
            }
            if (op.charAt(0) == 'S') {
                shiftRow();
            }
        }

        return getSolution();
    }

    private void initialize(int[][] rc) {
        numRows = rc.length;
        numCols = rc[0].length;

        leftCol = new ArrayDeque<>();
        rightCol = new ArrayDeque<>();
        middleRows = new ArrayDeque<>();

        for (int i = 0; i < numRows; i++) {
            leftCol.add(rc[i][0]);
            rightCol.add(rc[i][numCols - 1]);

            ArrayDeque<Integer> tmp = new ArrayDeque<>();
            for (int j = 1; j < numCols - 1; j++) {
                tmp.add(rc[i][j]);
            }
            middleRows.add(tmp);
        }
    }

    private int[][] getSolution() {
        int[][] ans = new int[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            ans[i][0] = leftCol.pollFirst();
            ans[i][numCols - 1] = rightCol.pollFirst();
        }

        int i = 0;
        for (ArrayDeque<Integer> row : middleRows) {
            for (int j = 1; j < numCols - 1; j++) {
                ans[i][j] = row.pollFirst();
            }
            i++;
        }
        return ans;
    }

    private void rotate() {
        if (numCols == 2) {
            rightCol.addFirst(leftCol.pollFirst());
            leftCol.addLast(rightCol.pollLast());
            return;
        }

        ArrayDeque<Integer> firstRow = middleRows.peekFirst();
        ArrayDeque<Integer> lastRow = middleRows.peekLast();
        firstRow.addFirst(leftCol.pollFirst());
        rightCol.addFirst(firstRow.pollLast());

        lastRow.addLast(rightCol.pollLast());
        leftCol.addLast(lastRow.pollFirst());
    }

    private void shiftRow() {
        ArrayDeque<Integer> firstRow = middleRows.pollLast();
        leftCol.addFirst(leftCol.pollLast());
        rightCol.addFirst(rightCol.pollLast());
        middleRows.addFirst(firstRow);
    }

    // @Test
    // void 정답() {
    //     int[][] rc1 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
    //     String[] o1 = { "Rotate", "ShiftRow" };
    //     Assertions.assertArrayEquals(new int[][] { { 8, 9, 6 }, { 4, 1, 2 }, { 7, 5, 3 } }, solution(rc1, o1));
    //     int[][] rc2 = { { 8, 6, 3 }, { 3, 3, 7 }, { 8, 4, 9 } };
    //     String[] o2 = { "Rotate", "ShiftRow", "ShiftRow" };
    //     Assertions.assertArrayEquals(new int[][] { { 8, 3, 3 }, { 4, 9, 7 }, { 3, 8, 6 } }, solution(rc2, o2));
    // }
}