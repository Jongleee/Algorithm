package com.example.algorithm.java.sort;

import java.util.Arrays;
import java.util.Comparator;

public class SortFileName {
    public String[] solution(String[] files) {
        Arrays.sort(files, Comparator.comparing(this::extractHead)
                .thenComparingInt(this::extractNumber));
        return files;
    }

    public String extractHead(String fileName) {
        return fileName.split("\\d+")[0].toLowerCase();
    }

    public int extractNumber(String fileName) {
        String numberString = fileName.substring(extractHead(fileName).length());
        StringBuilder result = new StringBuilder();
        for (char c : numberString.toCharArray()) {
            if (Character.isDigit(c) && result.length() < 5) {
                result.append(c);
            } else {
                break;
            }
        }
        return Integer.parseInt(result.toString());
    }
}
