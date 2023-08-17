package com.example.algorithm.java.string;

import java.util.HashMap;
import java.util.Map;

public class ClosestSameLetter {
    public int[] solution(String s) {
        int[] answer = new int[s.length()];
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (map.containsKey(c)) {
                answer[i] = i - map.get(c);
            } else {
                answer[i] = -1;
            }

            map.put(c, i);
        }

        return answer;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertArrayEquals(new int[] { -1, -1, -1, 2, 2, 2 },
    //             solution("banana"));
    //     Assertions.assertArrayEquals(new int[] { -1, -1, 1, -1, -1, -1 },
    //             solution("foobar"));
    // }
}
