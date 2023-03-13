package com.example.algorithm.java.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AutoComplete {

    public static int solution(String[] words) {
        List<String> sortedWords = Arrays.asList(words);
        Collections.sort(sortedWords);

        int answer = len(sortedWords.get(0), sortedWords.get(1));
        answer = answer < sortedWords.get(0).length() ? answer + 1 : answer;

        for (int i = 1; i < sortedWords.size() - 1; i++) {
            int prevLen = len(sortedWords.get(i), sortedWords.get(i - 1));
            int nextLen = len(sortedWords.get(i), sortedWords.get(i + 1));
            int maxLength = Math.max(prevLen, nextLen);

            answer += maxLength < sortedWords.get(i).length() ? maxLength + 1 : maxLength;
        }

        int lastLen = len(sortedWords.get(sortedWords.size() - 2), sortedWords.get(sortedWords.size() - 1));
        answer += lastLen < sortedWords.get(sortedWords.size() - 1).length() ? lastLen + 1 : lastLen;

        return answer;
    }

    public static int len(String s1, String s2) {
        int length = 0;

        for (int i = 0; i < Math.min(s1.length(), s2.length()); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                length++;
            } else {
                break;
            }
        }

        return length;
    }

    public static void main(String[] args) {
        String[] w1 = { "go", "gone", "guild" };
        String[] w2 = { "abc", "def", "ghi", "jklm" };
        String[] w3 = { "word", "war", "warrior", "world" };

        System.out.println(solution(w1));// 7
        System.out.println(solution(w2));// 4
        System.out.println(solution(w3));// 15
    }
}
