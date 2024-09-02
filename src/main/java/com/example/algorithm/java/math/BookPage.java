package com.example.algorithm.java.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BookPage {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] digitCount = new int[10];

        int factor = 1;

        while (n >= factor) {
            int lowerDigits = n % factor;
            int currentDigit = (n / factor) % 10;
            int higherDigits = n / (factor * 10);

            for (int i = 0; i < 10; i++) {
                digitCount[i] += higherDigits * factor;
            }

            for (int i = 0; i < currentDigit; i++) {
                digitCount[i] += factor;
            }
            digitCount[currentDigit] += lowerDigits + 1;

            digitCount[0] -= factor;

            factor *= 10;
        }

        StringBuilder sb = new StringBuilder();
        for (int count : digitCount) {
            sb.append(count).append(" ");
        }
        System.out.println(sb.toString().trim());
    }
}

/*
543212345

429904664 541008121 540917467 540117067 533117017 473117011 429904664 429904664 429904664 429904664


999

189 300 300 300 300 300 300 300 300 300
*/