import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValue {
    private static Map<String, String> dict = new HashMap<>();

    public String solution(String m, String[] musicinfos) {
        String answer = "(None)";

        initializeDictionary();
        m = convert(m);

        int maxTime = 0;
        for (String musicInfo : musicinfos) {
            String[] info = musicInfo.split(",");
            int time = getTime(info[0], info[1]);
            String music = convert(info[3]);

            music = repeatMusic(music, time);

            if (music.contains(m) && time > maxTime) {
                maxTime = time;
                answer = info[2];
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

    private String convert(String str) {
        for (Map.Entry<String, String> entry : dict.entrySet()) {
            str = str.replace(entry.getKey(), entry.getValue());
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

    @Test
    public void 정답() {
        Assertions.assertEquals("HELLO",
                solution("ABCDEFG", new String[] { "12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF" }));

        Assertions.assertEquals("FOO",
                solution("CC#BCC#BCC#BCC#B", new String[] { "03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B" }));

        Assertions.assertEquals("WORLD",
                solution("ABC", new String[] { "12:00,12:14,HELLO,C#DEFGAB", "13:00,13:05,WORLD,ABCDEF" }));
    }
}
