package com.example.algorithm.java.practice;

public class BilliardPractice {
    public int[] solution(int m, int n, int startX, int startY, int[][] balls) {
        int[] answer = new int[balls.length];

        for (int i = 0; i < balls.length; i++) {
            int[] ball = balls[i];
            int diffX = startX - ball[0];
            int diffY = startY - ball[1];

            int left = calculateDistance(startX + ball[0], diffY);
            int right = calculateDistance((m - startX) + (m - ball[0]), diffY);
            int top = calculateDistance(diffX, (n - startY) + (n - ball[1]));
            int bottom = calculateDistance(diffX, startY + ball[1]);

            int temp = Math.min(left, right);
            if (diffX == 0) {
                answer[i] = diffY > 0 ? Math.min(temp, top) : Math.min(temp, bottom);
            } else if (diffY == 0) {
                temp = Math.min(top, bottom);
                answer[i] = diffX > 0 ? Math.min(right, temp) : Math.min(left, temp);
            } else {
                answer[i] = Math.min(temp, Math.min(top, bottom));
            }
        }

        return answer;
    }

    private int calculateDistance(int a, int b) {
        return a * a + b * b;
    }

    // @Test
    // void 정답() {
    //     int[][] balls = { { 7, 7 }, { 2, 7 }, { 7, 3 } };
    //     int[] result = { 52, 37, 116 };
    //     Assertions.assertArrayEquals(result, solution(10, 10, 3, 7, balls));
    // }
}
