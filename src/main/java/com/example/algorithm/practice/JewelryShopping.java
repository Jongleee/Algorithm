package com.example.algorithm.practice;

import java.util.*;

public class JewelryShopping {
    public static void main(String[] args) {
        System.out.println(
                Arrays.toString(
                        solution(new String[]{"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"}
                        )));

    }

    public static int[] solution(String[] gems) {
        Map<String, Integer> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        int len = gems.length + 1, start = 0, index = 0;

        set.addAll(Arrays.asList(gems));

        for (int i = 0; i < gems.length; i++) {
            map.put(gems[i], map.getOrDefault(gems[i], 0) + 1);
            queue.add(gems[i]);

            while (map.get(queue.peek()) > 1) {
                map.put(queue.peek(), map.get(queue.poll()) - 1);
                index++;
            }

            if (map.size() == set.size() && len > (i - index)) {
                len = i - index;
                start = index + 1;
            }
        }

        return new int[]{start, start + len};
    }

}
