package com.example.algorithm.java.slidingWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExchangeString {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        int minSwaps;
        int aCount = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'a') {
                aCount++;
            }
        }

        int bCount = 0;
        for (int i = 0; i < aCount; i++) {
            if (input.charAt(i) == 'b') {
                bCount++;
            }
        }
        minSwaps = bCount;

        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i - 1) == 'b') {
                bCount--;
            }
            if (input.charAt((i + aCount - 1) % input.length()) == 'b') {
                bCount++;
            }
            minSwaps = Math.min(minSwaps, bCount);
        }

        System.out.println(minSwaps);
    }
}

/*
abababababababa

3


ba

0


aaaabbbbba

0


abab

1


aabbaaabaaba

2


aaaa

0
 */