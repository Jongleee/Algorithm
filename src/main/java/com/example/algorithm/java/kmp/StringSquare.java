package com.example.algorithm.java.kmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StringSquare {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputLine;

        while (true) {
            inputLine = br.readLine();
            if (inputLine.equals(".")) {
                break;
            }

            int maxRepetition = calculateMaxRepetition(inputLine);
            System.out.println(maxRepetition);
        }
    }

    private static int calculateMaxRepetition(String s) {
        char[] characters = s.toCharArray();
        int length = characters.length;
        int[] failure = buildFailureFunction(characters, length);

        int patternLength = length - failure[length - 1];
        if (patternLength == 0) {
            return 1;
        }
        return (length % patternLength == 0) ? (length / patternLength) : 1;
    }

    private static int[] buildFailureFunction(char[] characters, int length) {
        int[] failure = new int[length];
        failure[0] = 0;

        int currentIndex = 1;
        int prefixLength = 0;

        while (currentIndex < length) {
            if (characters[currentIndex] == characters[prefixLength]) {
                prefixLength++;
                failure[currentIndex] = prefixLength;
                currentIndex++;
            } else if (prefixLength > 0) {
                prefixLength = failure[prefixLength - 1];
            } else {
                failure[currentIndex] = 0;
                currentIndex++;
            }
        }

        return failure;
    }
}

/*
abcd
aaaa
ababab
.

1
4
3
*/