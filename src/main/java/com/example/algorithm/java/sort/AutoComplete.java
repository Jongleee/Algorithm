package com.example.algorithm.java.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AutoComplete {
    public int solution(String[] words) {
        List<String> sortedWords = Arrays.asList(words);
        Collections.sort(sortedWords);

        int answer = commonPrefixLength(sortedWords.get(0), sortedWords.get(1));
        answer += (answer < sortedWords.get(0).length()) ? 1 : 0;

        for (int i = 1; i < sortedWords.size() - 1; i++) {
            int prevLen = commonPrefixLength(sortedWords.get(i), sortedWords.get(i - 1));
            int nextLen = commonPrefixLength(sortedWords.get(i), sortedWords.get(i + 1));
            int maxLength = Math.max(prevLen, nextLen);

            answer += (maxLength < sortedWords.get(i).length()) ? 1 : 0;
            answer += maxLength;
        }

        int lastLen = commonPrefixLength(sortedWords.get(sortedWords.size() - 2),
                sortedWords.get(sortedWords.size() - 1));
        answer += (lastLen < sortedWords.get(sortedWords.size() - 1).length()) ? 1 : 0;

        return answer + lastLen;
    }

    private int commonPrefixLength(String s1, String s2) {
        int length = 0;
        int minLength = Math.min(s1.length(), s2.length());

        for (int i = 0; i < minLength; i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                length++;
            } else {
                break;
            }
        }

        return length;
    }

    // @Test
    // void 정답() {
    //     String[] w1 = { "go", "gone", "guild" };
    //     String[] w2 = { "abc", "def", "ghi", "jklm" };
    //     String[] w3 = { "word", "war", "warrior", "world" };

    //     Assertions.assertEquals(7, solution(w1));
    //     Assertions.assertEquals(4, solution(w2));
    //     Assertions.assertEquals(15, solution(w3));
    // }
}
