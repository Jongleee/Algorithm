package com.example.algorithm.java.string;

public class CodeBetweenTwo {
    public String solution(String s, String skip, int index) {
        StringBuilder answerBuilder = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int j = 0;
            while (j < index) {
                c += 1;
                if (c > 'z') {
                    c -= 26;
                }
                if (skip.contains(String.valueOf(c))) {
                    j--;
                }
                j++;
            }
            answerBuilder.append(c);
        }

        return answerBuilder.toString();
    }

    // @Test
    // public void 정답() {
    // Assertions.assertEquals("happy",
    // solution("aukks", "wbqd", 5));
    // }
}
