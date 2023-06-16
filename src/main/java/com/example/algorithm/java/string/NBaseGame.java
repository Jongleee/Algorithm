package com.example.algorithm.java.string;

public class NBaseGame {
    public String solution(int n, int t, int m, int p) {
        StringBuilder answer = new StringBuilder();

        int length = t * m;

        for (int i = 0; answer.length() < length; i++) {
            answer.append(Integer.toString(i, n));
        }

        StringBuilder result = new StringBuilder();

        for (int i = p - 1; result.length() < t; i += m) {
            result.append(answer.charAt(i));
        }

        return result.toString().toUpperCase();
    }
}
