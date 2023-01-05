package com.example.algorithm.java.practice;

public class BSTInstallation {
    public static void main(String[] args) {
        System.out.println(solution(11, new int[]{4, 11}, 1));
    }

    public static int solution(int n, int[] stations, int w) {
        int answer = 0;

        int start = 1;

        for (int station : stations) {
            if (start < station - w) {
                answer = getAnswer(station - w, start, w, answer);
            }
            start = station + w + 1;
        }

        if (start<= n) {
            answer = getAnswer(n + 1, start, w, answer);
        }
        return answer;
    }

    private static int getAnswer(int n, int start, int w, int answer) {
        int length = n - start;
        int count = length / (w * 2 + 1);

        if (length % (w * 2 + 1) != 0) count++;

        answer += count;
        return answer;
    }
}
