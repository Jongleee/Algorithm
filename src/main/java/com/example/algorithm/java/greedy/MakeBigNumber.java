package com.example.algorithm.java.greedy;

public class MakeBigNumber {
    public String solution(String number, int k) {
        StringBuilder sb = new StringBuilder();
        int maxIndex = 0;

        for (int i = 0; i < number.length() - k; i++) {
            int maxDigit = 0;
            for (int j = maxIndex; j <= k + i; j++) {
                int digit = number.charAt(j) - '0';
                if (digit > maxDigit) {
                    maxDigit = digit;
                    maxIndex = j + 1;
                }
                if (maxDigit == 9)
                    break;
            }
            sb.append(maxDigit);
        }

        return sb.toString();
    }
}
