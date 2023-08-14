package com.example.algorithm.java.hashMap;

import java.util.HashMap;

public class MemoryScore {
    public int[] solution(String[] name, int[] yearning, String[][] photo) {
        int[] answer = new int[photo.length];

        HashMap<String, Integer> nameToYearning = new HashMap<>();
        for (int i = 0; i < name.length; i++) {
            nameToYearning.put(name[i], yearning[i]);
        }

        for (int i = 0; i < photo.length; i++) {
            int totalYearning = 0;
            for (String person : photo[i]) {
                totalYearning += nameToYearning.getOrDefault(person, 0);
            }
            answer[i] = totalYearning;
        }

        return answer;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertArrayEquals(new int[] { 19, 15, 6 },
    //             solution(new String[] { "may", "kein", "kain", "radi" }, new int[] { 5, 10, 1, 3 },
    //                     new String[][] { { "may", "kein", "kain", "radi" }, { "may", "kein", "brin", "deny" },
    //                             { "kon", "kain", "may", "coni" } }));
    //     Assertions.assertArrayEquals(new int[] { 67, 0, 55 },
    //             solution(new String[] { "kali", "mari", "don" }, new int[] { 11, 1, 55 },
    //                     new String[][] { { "kali", "mari", "don" }, { "pony", "tom", "teddy" },
    //                             { "con", "mona", "don" } }));
    // }
}
