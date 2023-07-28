package com.example.algorithm.java.hashMap;

import java.util.HashMap;
import java.util.Map;

public class TestPersonalityType {
    public String solution(String[] survey, int[] choices) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < survey.length; i++) {
            int value = choices[i];

            if (value > 0 && value < 4) {
                char ch = survey[i].charAt(0);
                map.put(ch, map.getOrDefault(ch, 0) + 4 - value);
            } else if (value > 4) {
                char ch = survey[i].charAt(1);
                map.put(ch, map.getOrDefault(ch, 0) + value - 4);
            }
        }

        StringBuilder result = new StringBuilder()
                .append(map.getOrDefault('R', 0) >= map.getOrDefault('T', 0) ? 'R' : 'T')
                .append(map.getOrDefault('C', 0) >= map.getOrDefault('F', 0) ? 'C' : 'F')
                .append(map.getOrDefault('J', 0) >= map.getOrDefault('M', 0) ? 'J' : 'M')
                .append(map.getOrDefault('A', 0) >= map.getOrDefault('N', 0) ? 'A' : 'N');

        return result.toString();
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals("TCMA", solution(new String[] { "AN", "CF", "MJ", "RT", "NA"}, new int[] {5, 3, 2, 7, 5}));
    //     Assertions.assertEquals("RCJA", solution(new String[] {"TR", "RT", "TR"}, new int[] { 7,1,3}));
    // }
}
