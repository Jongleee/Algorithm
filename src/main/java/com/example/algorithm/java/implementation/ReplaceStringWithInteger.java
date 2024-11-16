package com.example.algorithm.java.implementation;

public class ReplaceStringWithInteger {
    public int solution(String s) {
        int answer = 0;
        char temp = s.charAt(0);
        int startIndex = (temp == '-' || temp == '+') ? 1 : 0;

        for (int i = startIndex; i < s.length(); i++) {
            char ch = s.charAt(i);

            int digit = ch - '0';
            answer = answer * 10 + digit;
        }

        if (temp == '-')
            answer *= -1;

        return answer;
    }

    // @Test
    // void 정답() {
    //     String[] k = { "1234", "-1234" };
    //     int[] result = { 1234, -1234 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(k[i]));
    //     }
    // }
}
