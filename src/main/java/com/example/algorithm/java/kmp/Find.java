package com.example.algorithm.java.kmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Find {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text = br.readLine();
        String pattern = br.readLine();

        int[] partialMatch = buildPartialMatchTable(pattern);
        SearchResult result = kmpSearch(text, pattern, partialMatch);

        System.out.println(result.getCount());
        System.out.println(result.getPositions());
    }

    private static int[] buildPartialMatchTable(String pattern) {
        int length = pattern.length();
        int[] partialMatch = new int[length];
        partialMatch[0] = 0;

        int prefixIndex = 0;
        for (int currentIndex = 1; currentIndex < length; currentIndex++) {
            while (prefixIndex > 0 && pattern.charAt(currentIndex) != pattern.charAt(prefixIndex)) {
                prefixIndex = partialMatch[prefixIndex - 1];
            }

            if (pattern.charAt(currentIndex) == pattern.charAt(prefixIndex)) {
                prefixIndex++;
                partialMatch[currentIndex] = prefixIndex;
            } else {
                partialMatch[currentIndex] = 0;
            }
        }

        return partialMatch;
    }

    private static SearchResult kmpSearch(String text, String pattern, int[] partialMatch) {
        int textLength = text.length();
        int patternLength = pattern.length();
        int matchCount = 0;
        StringBuilder positionsBuilder = new StringBuilder();

        int textIndex = 0;
        int patternIndex = 0;

        while (textIndex < textLength) {
            if (text.charAt(textIndex) == pattern.charAt(patternIndex)) {
                textIndex++;
                patternIndex++;

                if (patternIndex == patternLength) {
                    matchCount++;
                    positionsBuilder.append(textIndex - patternLength + 1).append(' ');
                    patternIndex = partialMatch[patternIndex - 1];
                }
            } else {
                if (patternIndex != 0) {
                    patternIndex = partialMatch[patternIndex - 1];
                } else {
                    textIndex++;
                }
            }
        }

        return new SearchResult(matchCount, positionsBuilder.toString());
    }

    private static class SearchResult {
        private final int count;
        private final String positions;

        public SearchResult(int count, String positions) {
            this.count = count;
            this.positions = positions;
        }

        public int getCount() {
            return count;
        }

        public String getPositions() {
            return positions;
        }
    }
}

/*
ABC ABCDAB ABCDABCDABDE
ABCDABD

1
16
*/