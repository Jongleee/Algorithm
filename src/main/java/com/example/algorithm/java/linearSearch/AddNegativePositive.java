package com.example.algorithm.java.linearSearch;

public class AddNegativePositive {
    public int solution(int[] absolutes, boolean[] signs) {
        int answer = 0;

        for (int i = 0; i < signs.length; i++) {
            int value = signs[i] ? absolutes[i] : -absolutes[i];
            answer += value;
        }

        return answer;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(9, solution(new int[] { 4, 7, 12 }, new boolean[] { true, false, true }));
    //     Assertions.assertEquals(0, solution(new int[] { 1, 2, 3 }, new boolean[] { false, false, true }));
    // }
}
