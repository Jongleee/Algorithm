package com.example.algorithm.java.queue;

import java.util.ArrayDeque;
import java.util.Arrays;

public class MatrixOperations {
    static int numRows;
    static int numCols;
    static ArrayDeque<Integer> leftCol;
    static ArrayDeque<Integer> rightCol;
    static ArrayDeque<ArrayDeque<Integer>> middleRows;

    private static void init(int[][] rc) {
        numRows = rc.length;
        numCols = rc[0].length;

        leftCol = new ArrayDeque<>();
        rightCol = new ArrayDeque<>();
        for (int i = 0; i < numRows; i++) {
            leftCol.add(rc[i][0]);
            rightCol.add(rc[i][numCols - 1]);
        }

        middleRows = new ArrayDeque<>();
        for (int i = 0; i < numRows; i++) {
            ArrayDeque<Integer> tmp = new ArrayDeque<>();
            for (int j = 1; j < numCols - 1; j++) {
                tmp.add(rc[i][j]);
            }
            middleRows.add(tmp);
        }
    }

    private static int[][] getAnswer() {
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

    private static void rotate() {
        if (numCols == 2) {
            rightCol.addFirst(leftCol.pollFirst());
            leftCol.addLast(rightCol.pollLast());
            return;
        }
        middleRows.peekFirst().addFirst(leftCol.pollFirst());
        rightCol.addFirst(middleRows.peekFirst().pollLast());
        middleRows.peekLast().addLast(rightCol.pollLast());
        leftCol.addLast(middleRows.peekLast().pollFirst());
    }

    private static void shiftRow() {
        middleRows.addFirst(middleRows.pollLast());
        leftCol.addFirst(leftCol.pollLast());
        rightCol.addFirst(rightCol.pollLast());
    }

    public static int[][] solution(int[][] rc, String[] operations) {
        init(rc);
        for (String op : operations) {
            if (op.charAt(0) == 'R')
                rotate();
            if (op.charAt(0) == 'S')
                shiftRow();
        }

        return getAnswer();
    }

    public static void main(String[] args) {
        int[][] rc1 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        String[] o1 = { "Rotate", "ShiftRow" };
        System.out.println(Arrays.deepToString(solution(rc1, o1)));
    }
}