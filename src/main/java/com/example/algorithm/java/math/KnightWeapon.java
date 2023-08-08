package com.example.algorithm.java.math;

public class KnightWeapon {
    public int solution(int number, int limit, int power) {
        int answer = 0;

        for (int i = 1; i <= number; i++) {
            int count = countDivisors(i);
            answer += count > limit ? power : count;
        }

        return answer;
    }

    private int countDivisors(int num) {
        int count = 0;
        for (int j = 1; j * j <= num; j++) {
            if (j * j == num) {
                count++;
            } else if (num % j == 0) {
                count += 2;
            }
        }
        return count;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(10, solution(5, 3, 2));
    //     Assertions.assertEquals(21, solution(10, 3, 2));
    // }
}
