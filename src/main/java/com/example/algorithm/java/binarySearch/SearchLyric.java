package com.example.algorithm.java.binarySearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchLyric {
    public static List<Integer> solution(String[] words, String[] queries) {
        Map<Integer, List<String>> frontMap = new HashMap<>();
        Map<Integer, List<String>> backMap = new HashMap<>();

        for (String word : words) {
            int len = word.length();
            frontMap.computeIfAbsent(len, ArrayList::new).add(word);
            backMap.computeIfAbsent(len, ArrayList::new).add(reverse(word));
        }

        frontMap.values().forEach(Collections::sort);
        backMap.values().forEach(Collections::sort);

        List<Integer> answer = new ArrayList<>();
        for (String query : queries) {
            List<String> list;
            if (query.charAt(0) == '?') {
                list = backMap.get(query.length());
                query = reverse(query);
            } else {
                list = frontMap.get(query.length());
            }

            if (list == null) {
                answer.add(0);
            } else {
                String upperBoundQuery = query.replace('?', Character.MAX_VALUE);
                String lowerBoundQuery = query.replace("?", "");
                int upperBound = findBound(list, upperBoundQuery);
                int lowerBound = findBound(list, lowerBoundQuery);

                answer.add(upperBound - lowerBound);
            }
        }

        return answer;
    }

    private static int findBound(List<String> list, String str) {
        int start = 0;
        int end = list.size();

        while (start < end) {
            int mid = (start + end) / 2;

            if (list.get(mid).compareTo(str) >= 0) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        return start;
    }

    private static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static void main(String[] args) {
        String [] words ={"frodo", "front", "frost", "frozen", "frame", "kakao"  };
        String [] queries = {"fro??", "????o", "fr???", "fro???", "pro?"};
        System.out.println(solution(words, queries));//[3, 2, 4, 1, 0]
    }
}
