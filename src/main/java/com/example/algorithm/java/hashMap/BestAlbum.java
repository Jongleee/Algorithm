package com.example.algorithm.java.hashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BestAlbum {
    public int[] solution(String[] genres, int[] plays) {
        List<String> sortedGenres = sortByTotalPlays(genres, plays);

        List<Integer> resultIndices = new ArrayList<>();
        for (String genre : sortedGenres) {
            int firstIdx = findMostPlayedSongIndex(genres, plays, genre);
            resultIndices.add(firstIdx);

            int secondIdx = findMostPlayedSongIndexExceptGivenIndex(genres, plays, genre, firstIdx);
            if (secondIdx != -1)
                resultIndices.add(secondIdx);
        }

        int[] result = new int[resultIndices.size()];
        for (int i = 0; i < resultIndices.size(); i++) {
            result[i] = resultIndices.get(i);
        }
        return result;
    }

    private List<String> sortByTotalPlays(String[] genres, int[] plays) {
        Map<String, Integer> genreTotalPlaysMap = new HashMap<>();
        for (int i = 0; i < genres.length; i++) {
            genreTotalPlaysMap.put(genres[i], genreTotalPlaysMap.getOrDefault(genres[i], 0) + plays[i]);
        }
        List<String> sortedGenres = new ArrayList<>(genreTotalPlaysMap.keySet());
        sortedGenres.sort((genre1, genre2) -> genreTotalPlaysMap.get(genre2) - genreTotalPlaysMap.get(genre1));
        return sortedGenres;
    }

    private int findMostPlayedSongIndex(String[] genres, int[] plays, String genre) {
        int maxPlays = 0;
        int mostPlayedIndex = -1;
        for (int i = 0; i < genres.length; i++) {
            if (genres[i].equals(genre) && plays[i] > maxPlays) {
                maxPlays = plays[i];
                mostPlayedIndex = i;
            }
        }
        return mostPlayedIndex;
    }

    private int findMostPlayedSongIndexExceptGivenIndex(String[] genres, int[] plays, String genre, int exceptIndex) {
        int maxPlays = 0;
        int secondMostPlayedIndex = -1;
        for (int i = 0; i < genres.length; i++) {
            if (genres[i].equals(genre) && plays[i] > maxPlays && i != exceptIndex) {
                maxPlays = plays[i];
                secondMostPlayedIndex = i;
            }
        }
        return secondMostPlayedIndex;
    }

    // @Test
    // void 정답() {
    //     String[] genres = { "classic", "pop", "classic", "classic", "pop" };
    //     int[] plays = { 500, 600, 150, 800, 2500 };

    //     Assertions.assertArrayEquals(new int[] { 4, 1, 3, 0 }, solution(genres, plays));
    // }
}
