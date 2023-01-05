package com.example.algorithm.java.prac;

import java.util.*;
import java.util.Map.Entry;

class Solution4 {

    public static String solution(String[] participant, String[] completion) {
        // 두 집단의 비교를 위하여 먼저 오름차순으로 각 배열을 정렬
        Arrays.sort(participant);
        Arrays.sort(completion);
        // 반복문으로 정렬된 값의 비교에서 일치하지 않는 경우를 찾아줌
        for (int i = 0; i < completion.length; i++) {
            if (!participant[i].equals(completion[i])) {
                return participant[i];
            }
        }
        // 위의 반복문에서 일치하지 않는 값이 없다면 배열의 마지막 값이 답이 됨
        return participant[participant.length - 1];
    }
    public static void main(String[] args) {
        System.out.println(solution(new String[]{"marina", "josipa", "nikola", "vinko", "filipa"},
        new String[]{"josipa", "filipa", "marina", "nikola"}));
        //vinko
    }
}

class Solution4hash {
    public String solution(String[] participant, String[] completion) {
        HashMap<String, Integer> map = new HashMap<>();
        String answer = "";
        for (String key : participant)
            map.put(key, map.getOrDefault(key, 0) + 1);

        for (String key : completion)
            map.put(key, map.get(key) - 1);

        for (Entry<String, Integer> key : map.entrySet()) {
            if (key.getValue() != 0)
                return key.getKey();
        }
        return answer;
    }
}