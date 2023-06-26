package com.example.algorithm.java.implement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NewsClustering {
    public int solution(String str1, String str2) {
        Set<String> set = new HashSet<>();
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();

        cluster(str1.toLowerCase(), map1, set);
        cluster(str2.toLowerCase(), map2, set);

        int union = 0;
        int untersection = 0;

        for (String key : set) {
            int a = map1.getOrDefault(key, 0);
            int b = map2.getOrDefault(key, 0);
            union += Math.max(a, b);
            untersection += Math.min(a, b);
        }

        if (union == 0) {
            return 65536;
        }

        return (int) (((double) untersection / (double) union) * 65536);
    }

    static void cluster(String str, Map<String, Integer> map, Set<String> set) {
        for (int i = 0; i <= str.length() - 2; i++) {
            boolean flag = true;
            String s = str.substring(i, i + 2);

            for (int j = 0; j < 2; j++) {
                if (s.charAt(j) < 'a' || s.charAt(j) > 'z') {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                set.add(s);
                map.put(s, map.getOrDefault(s, 0) + 1);
            }
        }
    }
}
