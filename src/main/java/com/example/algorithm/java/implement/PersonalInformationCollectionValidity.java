package com.example.algorithm.java.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalInformationCollectionValidity {
    public int[] solution(String today, String[] terms, String[] privacies) {
        List<Integer> answer = new ArrayList<>();

        Map<String, Integer> expireDate = new HashMap<>();

        for (String term : terms) {
            String type = term.split(" ")[0];
            int date = Integer.parseInt(term.split(" ")[1]);
            expireDate.put(type, date);
        }

        int todayInt = Integer.parseInt(today.replace(".", ""));

        for (int i = 0; i < privacies.length; i++) {
            String privacy = privacies[i];
            int date = Integer.parseInt(privacy.split(" ")[0].replace(".", ""));
            String type = privacy.split(" ")[1];

            int year = date / 10000;
            int month = (date % 10000) / 100;
            int day = date % 100;

            month += expireDate.get(type);
            day -= 1;

            if (day == 0) {
                month -= 1;
                day += 28;
            }
            if (month > 12) {
                year += (month / 12);
                month = month - 12 * (month / 12);
            }
            if (month == 0) {
                month = 12;
                year -= 1;
            }

            date = year * 10000 + month * 100 + day;

            if (date < todayInt) {
                answer.add(i);
            }
        }

        int[] answerArr = new int[answer.size()];

        for (int i = 0; i < answer.size(); i++) {
            answerArr[i] = answer.get(i) + 1;
        }

        return answerArr;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertArrayEquals(new int[] { 1, 3 }, solution("2022.05.19", new String[] { "A 6", "B 12", "C 3" },
    //             new String[] { "2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C" }));
    //     Assertions.assertArrayEquals(new int[] { 1, 4, 5 }, solution("2020.01.01", new String[] { "Z 3", "D 5" },
    //             new String[] { "2019.01.01 D", "2019.11.15 Z", "2019.08.02 D", "2019.07.01 D", "2018.12.28 Z" }));
    // }
}
