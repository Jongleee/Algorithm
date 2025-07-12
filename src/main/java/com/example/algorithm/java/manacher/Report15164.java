package com.example.algorithm.java.manacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Report15164 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        long result = countPalindromes(transform(input));

        System.out.println(result);
    }

    private static String transform(String input) {
        StringBuilder sb = new StringBuilder("#");
        for (char ch : input.toCharArray()) {
            sb.append(ch).append("#");
        }
        return sb.toString();
    }

    private static long countPalindromes(String s) {
        int length = s.length();
        int[] radius = new int[length];
        int right = 0, center = 0;
        long total = 0;

        for (int i = 0; i < length; i++) {
            if (i <= right) {
                radius[i] = Math.min(radius[2 * center - i], right - i);
            }

            while (i - radius[i] - 1 >= 0 &&
                    i + radius[i] + 1 < length &&
                    s.charAt(i - radius[i] - 1) == s.charAt(i + radius[i] + 1)) {
                radius[i]++;
            }

            if (i + radius[i] > right) {
                right = i + radius[i];
                center = i;
            }

            total += (radius[i] + 1) / 2;
        }

        return total;
    }
}

/*
ABCBA

7
*/