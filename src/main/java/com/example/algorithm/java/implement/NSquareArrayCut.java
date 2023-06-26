package com.example.algorithm.java.implement;

public class NSquareArrayCut {
    public int[] solution(int n, long left, long right) {
        int length = (int) (right - left) + 1;
        int[] answer = new int[length];

        for (int i = 0; i < length; i++) {
            long offset =  i + left;
            int row = (int) (offset / n) + 1;
            int col = (int) (offset % n) + 1;
            answer[i] = Math.max(row, col);
        }

        return answer;
    }
}
