package com.example.algorithm.java.recursive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchRanking {
    static Map<String, List<Integer>> map = new HashMap<>();

    public int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];
        for (String inf : info) {
            dfs("", inf.split(" "), 0);
        }

        for (List<Integer> list : map.values()) {
            Collections.sort(list);
        }

        for (int i = 0; i < query.length; i++) {
            String[] temp = query[i].replace(" and ", "").split(" ");
            int score = Integer.parseInt(temp[1]);
            answer[i] = binarySearch(temp[0], score);
        }

        return answer;
    }

    private int binarySearch(String key, int score) {
        if (map.containsKey(key)) {
            List<Integer> list = map.get(key);
            int left = 0;
            int right = list.size() - 1;

            if (list.get(right) < score) {
                return 0;
            }

            while (left < right) {
                int mid = (left + right) / 2;

                if (list.get(mid) < score) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            return list.size() - left;
        }

        return 0;
    }

    private void dfs(String prefix, String[] info, int depth) {
        if (depth == 4) {
            int score = Integer.parseInt(info[4]);
            map.computeIfAbsent(prefix, k -> new ArrayList<>()).add(score);
            return;
        }

        dfs(prefix + "-", info, depth + 1);
        dfs(prefix + info[depth], info, depth + 1);
    }
}
