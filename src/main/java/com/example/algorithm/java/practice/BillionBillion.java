package com.example.algorithm.java.practice;

import java.util.Arrays;

public class BillionBillion {
    public static int[] solution(int e, int[] starts) {
        int[] numbers = new int[e + 1];
        int[] answer = new int[starts.length];

        for (int i = 1; i <= e; i++) {
            for (int j = i; j <= e; j += i) {
                numbers[j] += 1;
            }
        }

        int[] maxNumber = new int[e + 1];
        for (int i = 0; i < maxNumber.length; i++) {
            maxNumber[i] = e;
        }

        for (int i = e - 1; i > 0; i--) {
            if (numbers[i] >= numbers[maxNumber[i + 1]]) {
                maxNumber[i] = i;
            } else {
                maxNumber[i] = maxNumber[i + 1];
            }
        }
        for (int i = 0; i < starts.length; i++) {
            answer[i] = maxNumber[starts[i]];
        }
        return answer;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(8, new int[] { 1, 3, 7 })));
    }
    // 답:6,6,8
}
