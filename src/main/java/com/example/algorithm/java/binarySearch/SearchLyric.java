package com.example.algorithm.java.binarySearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchLyric {
    public List<Integer> solution(String[] words, String[] queries) {
        Map<Integer, List<String>> frontMap = new HashMap<>();
        Map<Integer, List<String>> backMap = new HashMap<>();

        for (String word : words) {
            int len = word.length();
            frontMap.computeIfAbsent(len, list -> new ArrayList<>()).add(word);
            backMap.computeIfAbsent(len, list -> new ArrayList<>()).add(reverse(word));
        }

        frontMap.values().forEach(Collections::sort);
        backMap.values().forEach(Collections::sort);

        List<Integer> answer = new ArrayList<>();
        for (String query : queries) {
            List<String> wordList;

            if (query.charAt(0) == '?') {
                wordList = backMap.get(query.length());
                query = reverse(query);
            } else {
                wordList = frontMap.get(query.length());
            }

            if (wordList == null) {
                answer.add(0);
            } else {
                String upperBoundQuery = query.replace('?', Character.MAX_VALUE);
                String lowerBoundQuery = query.replace("?", "");
                int upperBound = findBound(wordList, upperBoundQuery);
                int lowerBound = findBound(wordList, lowerBoundQuery);

                answer.add(upperBound - lowerBound);
            }
        }

        return answer;
    }

    private int findBound(List<String> wordList, String query) {
        int start = 0;
        int end = wordList.size();

        while (start < end) {
            int mid = (start + end) / 2;

            if (wordList.get(mid).compareTo(query) >= 0) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        return start;
    }

    private String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    // @Test
    // void 정답() {
    //     String[] words = { "frodo", "front", "frost", "frozen", "frame", "kakao" };
    //     String[] queries = { "fro??", "????o", "fr???", "fro???", "pro?" };
    //     List<Integer> result = new ArrayList<>(Arrays.asList(3, 2, 4, 1, 0));

    //     Assertions.assertEquals(result, solution(words, queries));
    // }
}
