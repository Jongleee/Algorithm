package com.example.algorithm.java.implementation;

import java.util.Arrays;
import java.util.HashMap;

public class MaxGift {
    public int solution(String[] friends, String[] gifts) {
        int n = friends.length;
        int[][] history = new int[n][n];
        int[] giftScore = new int[n];
        int[] nextMonthGift = new int[n];

        HashMap<String, Integer> friendIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            friendIndex.put(friends[i], i);
        }

        for (String gift : gifts) {
            String[] tmp = gift.split(" ");
            int giverIndex = friendIndex.get(tmp[0]);
            int takerIndex = friendIndex.get(tmp[1]);

            history[giverIndex][takerIndex]++;

            giftScore[giverIndex]++;
            giftScore[takerIndex]--;
        }

        for (int giver = 0; giver < n; giver++) {
            for (int taker = giver + 1; taker < n; taker++) {
                int give = history[giver][taker];
                int take = history[taker][giver];

                if (give > take) {
                    nextMonthGift[giver]++;
                } else if (give < take) {
                    nextMonthGift[taker]++;
                } else {
                    if (giftScore[giver] > giftScore[taker]) {
                        nextMonthGift[giver]++;
                    } else if (giftScore[giver] < giftScore[taker]) {
                        nextMonthGift[taker]++;
                    }
                }
            }
        }

        return Arrays.stream(nextMonthGift)
                .max()
                .getAsInt();
    }

    // @Test
    // void 정답() {
    //     String[][] friends = { { "muzi", "ryan", "frodo", "neo" },
    //             { "joy", "brad", "alessandro", "conan", "david" },
    //             { "a", "b", "c" }
    //     };
    //     String[][] gifts = {
    //             { "muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi" },
    //             { "alessandro brad", "alessandro joy", "alessandro conan", "david alessandro", "alessandro david" },
    //             { "a b", "b a", "c a", "a c", "a c", "c a" }
    //     };

    //     int[] result = { 2, 4, 0 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(friends[i], gifts[i]));
    //     }
    // }
}
