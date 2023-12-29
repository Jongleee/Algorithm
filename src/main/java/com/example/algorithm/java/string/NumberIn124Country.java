package com.example.algorithm.java.string;

public class NumberIn124Country {
    public String solution(int n) {
        StringBuilder ternaryDigits = new StringBuilder();

        while (n != 0) {
            if (n % 3 != 0) {
                ternaryDigits.append(n % 3);
                n /= 3;
            } else {
                ternaryDigits.append(4);
                n = n / 3 - 1;
            }
        }


        return ternaryDigits.reverse().toString();
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals("1", solution(1));
    //     Assertions.assertEquals("2", solution(2));
    //     Assertions.assertEquals("4", solution(3));
    //     Assertions.assertEquals("11", solution(4));
    // }
}
