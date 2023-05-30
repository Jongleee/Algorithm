package com.example.algorithm.java.recursive;

public class QuadCompression {

    private int[] answer;

    public int[] solution(int[][] arr) {
        answer = new int[2];
        quad(arr, 0, 0, arr.length);
        return answer;
    }

    private void quad(int[][] arr, int x, int y, int size) {
        if (zip(arr, x, y, size, arr[x][y])) {
            if (arr[x][y] == 1)
                answer[1]++;
            else
                answer[0]++;
            return;
        }
        quad(arr, x, y, size / 2);
        quad(arr, x, y + size / 2, size / 2);
        quad(arr, x + size / 2, y, size / 2);
        quad(arr, x + size / 2, y + size / 2, size / 2);
    }

    private boolean zip(int[][] arr, int x, int y, int size, int val) {
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (arr[i][j] != val) {
                    return false;
                }
            }
        }
        return true;
    }
}
