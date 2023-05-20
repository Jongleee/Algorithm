package com.example.algorithm.java.string;

import java.util.HashMap;

public class SongNow {
    private static HashMap<String, String> dict = new HashMap<>();

    public String solution(String m, String[] musicinfos) {
        String answer = "(None)";

        initializeDictionary();
        m = change(m);

        int maxTime = 0;
        for (String musicInfo : musicinfos) {
            String[] temp = musicInfo.split(",");
            int time = getTime(temp[0], temp[1]);
            String music = change(temp[3]);

            music = repeatMusic(music, time);

            if (music.contains(m) && time > maxTime) {
                maxTime = time;
                answer = temp[2];
            }
        }

        return answer;
    }

    private void initializeDictionary() {
        dict.put("C#", "c");
        dict.put("D#", "d");
        dict.put("F#", "f");
        dict.put("G#", "g");
        dict.put("A#", "a");
    }

    private String change(String str) {
        for (String key : dict.keySet()) {
            str = str.replace(key, dict.get(key));
        }
        return str;
    }

    private int getTime(String str1, String str2) {
        String[] time1 = str1.split(":");
        String[] time2 = str2.split(":");

        int h1 = Integer.parseInt(time1[0]) * 60;
        int h2 = Integer.parseInt(time2[0]) * 60;

        int m1 = Integer.parseInt(time1[1]);
        int m2 = Integer.parseInt(time2[1]);

        return (h2 + m2) - (h1 + m1);
    }

    private String repeatMusic(String music, int time) {
        StringBuilder repeatedMusic = new StringBuilder();
        while (repeatedMusic.length() < time) {
            repeatedMusic.append(music);
        }
        return repeatedMusic.substring(0, time);
    }
}
