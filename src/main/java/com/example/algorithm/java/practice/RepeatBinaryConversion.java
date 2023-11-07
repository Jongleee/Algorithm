package com.example.algorithm.java.practice;

public class RepeatBinaryConversion {
    public int[] solution(String s) {
        int[] answer = { 0, 0 };
        int temp = 0;

        while (s.length() > 1) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '0') {
                    answer[1]++;
                } else {
                    temp++;
                }
            }
            s = Integer.toBinaryString(temp);
            temp = 0;
            answer[0]++;
        }

        return answer;
    }

    // @Test
    // void 정답() {
    //     Assertions.assertArrayEquals(new int[] { 3, 8 }, solution("110010101001"));
    //     Assertions.assertArrayEquals(new int[] { 3, 3 }, solution("01110"));
    //     Assertions.assertArrayEquals(new int[] { 4, 1 }, solution("1111111"));
    // }
}
