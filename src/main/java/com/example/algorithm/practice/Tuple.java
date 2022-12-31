package com.example.algorithm.practice;

import java.util.*;

public class Tuple {
    public static int[] solution(String s) {
        String[] temp = s.substring(2, s.length() - 2).split("},\\{");
        ArrayList<String> result = new ArrayList<>();
        Arrays.sort(temp, (s1, s2) -> s1.length() - s2.length());
        for (int i = 0; i < temp.length; i++) {
            for (String string : temp[i].split(",")) {
                if (!result.contains(string))
                    result.add(string);
            }
        }
        int[] answer = new int[result.size()];
        for (int i = 0; i < result.size(); i++)
            answer[i] = Integer.parseInt(result.get(i));

        return answer;
    }
    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution("{{4,2,3},{3},{2,3,4,1},{2,3}}")));
    }
}
