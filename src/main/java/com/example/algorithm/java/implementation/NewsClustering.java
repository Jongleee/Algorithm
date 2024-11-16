package com.example.algorithm.java.implementation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NewsClustering {
    public int solution(String str1, String str2) {
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();

        cluster(str1.toLowerCase(), map1);
        cluster(str2.toLowerCase(), map2);

        int union = 0;
        int intersection = 0;

        Set<String> set = new HashSet<>(map1.keySet());
        set.addAll(map2.keySet());

        for (String key : set) {
            int a = map1.getOrDefault(key, 0);
            int b = map2.getOrDefault(key, 0);
            union += Math.max(a, b);
            intersection += Math.min(a, b);
        }

        if (union == 0) {
            return 65536;
        }

        return (int) (((double) intersection / (double) union) * 65536);
    }

    private void cluster(String str, Map<String, Integer> map) {
        for (int i = 0; i <= str.length() - 2; i++) {
            String s = str.substring(i, i + 2);
            if (Character.isLetter(s.charAt(0)) && Character.isLetter(s.charAt(1))) {
                map.put(s, map.getOrDefault(s, 0) + 1);
            }
        }
    }

    // @Test
    // void 정답() {
    //     String[] str1 = { "FRANCE", "handshake", "aa1+aa2", "E=M*C^2" };
    //     String[] str2 = { "french", "shake hands", "AAAA12", "e=m*c^2" };
    //     int[] expected = { 16384, 65536, 43690, 65536 };

    //     for (int i = 0; i < expected.length; i++) {
    //         Assertions.assertEquals(expected[i], solution(str1[i], str2[i]));
    //     }
    // }
}
