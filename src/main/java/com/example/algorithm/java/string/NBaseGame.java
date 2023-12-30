package com.example.algorithm.java.string;

public class NBaseGame {
    public String solution(int n, int t, int m, int p) {
        StringBuilder answer = new StringBuilder();

        int length = t * m;

        for (int i = 0; answer.length() < length; i++) {
            answer.append(Integer.toString(i, n));
        }

        StringBuilder result = new StringBuilder();

        for (int i = p - 1; result.length() < t; i += m) {
            result.append(answer.charAt(i));
        }

        return result.toString().toUpperCase();
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals("0111", solution(2,4,2,1));
    //     Assertions.assertEquals("02468ACE11111111", solution(16,16,2,1));
    //     Assertions.assertEquals("13579BDF01234567", solution(16,16,2,2));
    // }
}
