package com.example.algorithm.practice;

public class PoppingBalloons {
    public static void main(String[] args) {
        int[] a = {-16, 27, 65, -2, 58, -92, -71, -68, -61, -33};
        System.out.println(solution(a));
    }

    public static int solution(int[] a) {
        int answer = 0;
        int leftMin = 1000000001;
        int rightMin = 1000000001;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < leftMin) {
                leftMin = a[i];
                answer++;
            }
            if (a[a.length - i - 1] < rightMin) {
                rightMin = a[a.length - i - 1];
                answer++;
            }
            if (leftMin == rightMin) {
                break;
            }
        }
        if (leftMin == rightMin) {
            answer--;
        }
        return answer;
    }
}
