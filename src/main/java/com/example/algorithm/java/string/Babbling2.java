package com.example.algorithm.java.string;

public class Babbling2 {
    public int solution(String[] babbling) {
        int answer = 0;
        String[] words = {"aya", "ye", "woo", "ma"};
    
        for (String bb : babbling) {
            boolean containsDuplicate = false;
            for (String word : words) {
                if (bb.contains(word + word)) {
                    containsDuplicate = true;
                    break;
                }
            }
            if (containsDuplicate) {
                continue;
            }
            bb = bb.replaceAll("aya|ye|woo|ma", "");
            if (bb.isEmpty()) {
                answer++;
            }
        }
        return answer;
    }
    
    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(1, solution(new String[] { "aya", "yee", "u", "maa" }));
    //     Assertions.assertEquals(2, solution(new String[] { "ayaye", "uuu", "yeye", "yemawoo", "ayaayaa" }));
    // }
}
