package com.example.algorithm.java.string;

public class NumberIn124Country {
    public static String solution(int n) {
        StringBuilder temp = new StringBuilder();
        while (n != 0) {
            if (n % 3 != 0) {
                temp.append(n % 3);
                n /= 3;
            } else {
                temp.append("4");
                n = n / 3 - 1;
            }
        }
        return temp.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(solution(10));
    }
}
