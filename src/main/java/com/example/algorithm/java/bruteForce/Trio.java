package com.example.algorithm.java.bruteForce;

public class Trio {
    public int solution(int[] number) {
        int answer = 0;
        int n = number.length;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (number[i] + number[j] + number[k] == 0) {
                        answer++;
                    }
                }
            }
        }

        return answer;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(2, solution(new int[] { -2, 3, 0, 2, -5 }));
    //     Assertions.assertEquals(5, solution(new int[] { -3, -2, -1, 0, 1, 2, 3 }));
    //     Assertions.assertEquals(0, solution(new int[] { -1, 1, -1, 1 }));
    // }
}
