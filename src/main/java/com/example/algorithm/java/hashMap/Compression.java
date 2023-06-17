package com.example.algorithm.java.hashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compression {
    public int[] solution(String msg) {
        List<Integer> list = new ArrayList<>();
        int num = 1;
        Map<String, Integer> map = new HashMap<>();

        for (int i = 65; i <= 90; i++) {
            String c = String.valueOf((char) i);
            map.put(c, num++);
        }

        for (int i = 0; i < msg.length();) {
            int j = msg.length() - i;
            while (!map.containsKey(msg.substring(i, i + j))) {
                j--;
                if (j == 0) {
                    break;
                }
            }
            String s = msg.substring(i, i + j);

            if (i + j == msg.length()) {
                list.add(map.get(s));
            } else {
                s = msg.substring(i, i + j + 1);
                list.add(map.get(s.substring(0, s.length() - 1)));
            }
            map.put(s, num++);
            i += j;
        }

        int[] answer = new int[list.size()];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = list.get(i);
        }

        return answer;
    }
}
