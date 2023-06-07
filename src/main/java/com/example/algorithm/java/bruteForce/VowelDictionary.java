package com.example.algorithm.java.bruteForce;

import java.util.ArrayList;
import java.util.List;

public class VowelDictionary {
    private List<String> dictionary;
    private final String[] alphabetArr = { "A", "E", "I", "O", "U" };

    public int solution(String word) {
        dictionary = new ArrayList<>();
        generateDictionary("");
        return dictionary.indexOf(word);
    }

    private void generateDictionary(String currentWord) {
        dictionary.add(currentWord);

        if (currentWord.length() >= alphabetArr.length) {
            return;
        }

        for (String alphabet : alphabetArr) {
            generateDictionary(currentWord + alphabet);
        }
    }
}
