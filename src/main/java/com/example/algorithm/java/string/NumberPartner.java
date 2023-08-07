package com.example.algorithm.java.string;

public class NumberPartner {
    public String solution(String X, String Y) {
        StringBuilder sb = new StringBuilder();

        int[] xArr = new int[10];
        int[] yArr = new int[10];

        for (char x : X.toCharArray()) {
            xArr[x - '0']++;
        }
        for (char y : Y.toCharArray()) {
            yArr[y - '0']++;
        }

        for (int i = 9; i >= 0; i--) {
            while (xArr[i] > 0 && yArr[i] > 0) {
                sb.append(i);
                xArr[i]--;
                yArr[i]--;
            }
        }

        if (sb.length() == 0) {
            return "-1";
        }
        if (sb.charAt(0) == '0') {
            return "0";
        }

        return sb.toString();
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals("-1", solution("100", "2345"));
    //     Assertions.assertEquals("0", solution("100", "203045"));
    //     Assertions.assertEquals("10", solution("100", "123450"));
    //     Assertions.assertEquals("321", solution("12321", "42531"));
    //     Assertions.assertEquals("552", solution("5525", "1255"));
    // }
}
