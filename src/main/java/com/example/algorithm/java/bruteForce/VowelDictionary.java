package com.example.algorithm.java.bruteForce;

public class VowelDictionary {
    public int solution(String word) {
        int answer = 0;
        int[] lengths = { 781, 156, 31, 6, 1 };
        String vowels = "AEIOU";

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = vowels.indexOf(ch) + 1;

            answer += (index - 1) * lengths[i] + 1;
        }

        return answer;
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals(6, solution("AAAAE"));
    //     Assertions.assertEquals(10, solution("AAAE"));
    //     Assertions.assertEquals(1563, solution("I"));
    //     Assertions.assertEquals(1189, solution("EIO"));
    // }
}
