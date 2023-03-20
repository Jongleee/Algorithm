package com.example.algorithm.java.hashMap;

import java.util.ArrayList;
import java.util.HashMap;

public class BestAlbum {

    public int[] solution(String[] genres, int[] plays) {
        ArrayList<String> genre = makeGenre(genres, plays);

        return extracted(genres, plays, genre);
    }

    private int[] extracted(String[] genres, int[] plays, ArrayList<String> genre) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < genre.size(); i++) {
            String g = genre.get(i);

            // 해당 장르의 음악 중에서 play횟수가 가장 큰 인덱스를 찾는다
            int max;
            int firstIdx = findFirstIdx(genres, plays, g);

            // 해당 장르의 음악 중에서 play횟수가 두번째로 큰 인덱스를 찾는다.
            max = 0;
            int secondIdx = findSecondIdx(genres, plays, g, max, firstIdx);

            list.add(firstIdx);
            if (secondIdx >= 0)
                list.add(secondIdx); // secondIdx는 존재 할수도, 안할수도 있다.
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    private int findSecondIdx(String[] genres, int[] plays, String g, int max, int firstIdx) {
        int secondIdx = -1;
        for (int j = 0; j < genres.length; j++) {
            if (g.equals(genres[j]) && max < plays[j] && j != firstIdx) {
                max = plays[j];
                secondIdx = j;
            }
        }
        return secondIdx;
    }

    private int findFirstIdx(String[] genres, int[] plays, String g) {
        int max = 0;
        int firstIdx = -1;
        for (int j = 0; j < genres.length; j++) {
            if (g.equals(genres[j]) && max < plays[j]) {
                max = plays[j];
                firstIdx = j;
            }
        }
        return firstIdx;
    }

    private ArrayList<String> makeGenre(String[] genres, int[] plays) {
        HashMap<String, Integer> map = new HashMap<>();

        for(int i=0; i<genres.length; i++){
            map.put(genres[i], map.getOrDefault(genres[i], 0)+plays[i]);
        }


        ArrayList<String> genre = new ArrayList<>();
        for(String key : map.keySet()) {
            genre.add(key);
        }
        genre.sort((o1, o2) -> map.get(o2) - map.get(o1)); //key값에 해당하는 value를 내림차순으로 정렬한다.
        return genre;
    }
}
