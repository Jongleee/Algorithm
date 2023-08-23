package com.example.algorithm.java.string;

public class StringHandlingDefault {
    public boolean solution(String s) {
        if (s.length() == 4 || s.length() == 6) {
            try {
                Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(false, solution("a234"));
    //     Assertions.assertEquals(true, solution("1234"));
    // }
}
