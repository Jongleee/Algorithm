package com.example.algorithm.java.string;

public class ImportMiddleCharacter {
    public String solution(String s) {
        return s.substring((s.length() - 1) / 2, s.length() / 2 + 1);
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals("c", solution("abcde"));
    //     Assertions.assertEquals("we", solution("qwer"));
    // }
}
