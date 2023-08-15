package com.example.algorithm.java.math;

public class CokeProblem {
    
    public int solution(int a, int b, int n) {
        int answer = 0;

        while (n >= a) {
            int temp = n / a;
            answer += temp * b;
            n = temp * b + (n % a);
        }

        return answer;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(19, solution(2, 1, 20));
    //     Assertions.assertEquals(9, solution(3, 1, 20));
    // }
}
