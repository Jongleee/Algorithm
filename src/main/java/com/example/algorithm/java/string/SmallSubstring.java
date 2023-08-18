package com.example.algorithm.java.string;

public class SmallSubstring {
    public int solution(String t, String p) {
        int len = p.length();
        long num = Long.parseLong(p);
        int result = 0;

        for (int i = 0; i <= t.length() - len; i++) {
            long diff = Long.parseLong(t.substring(i, i + len));
            if (diff <= num) {
                result++;
            }
        }
        return result;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(2, solution("3141592", "271"));
    //     Assertions.assertEquals(8, solution("500220839878", "7"));
    //     Assertions.assertEquals(3, solution("10203", "15"));
    // }
}
