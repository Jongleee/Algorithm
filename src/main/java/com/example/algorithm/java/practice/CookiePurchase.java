package com.example.algorithm.java.practice;

public class CookiePurchase {
    public static int solution(int[] cookie) {
        int answer = 0;
        for (int i = 0; i < cookie.length - 1; i++) {
            int firstIndex = i;
            int secondIndex = i + 1;
            int firstSum = cookie[firstIndex];
            int secondSum = cookie[secondIndex];
            while (true) {
                if (firstSum == secondSum && answer < secondSum)
                    answer = Math.max(answer, secondSum);
                else if (firstSum <= secondSum && firstIndex > 0)
                    firstSum += cookie[--firstIndex];
                else if (firstSum > secondSum && secondIndex < cookie.length - 1)
                    secondSum += cookie[++secondIndex];
                else
                    break;

            }
        }
        return answer;
    }

    public static void main(String[] args) {
        int[] c1 = { 1, 1, 2, 3 };
        int[] c2 = { 1, 2, 4, 5 };
        System.out.println(solution(c1));
        System.out.println(solution(c2));
    }
}
