package com.example.algorithm.java.hashMap;

import java.util.HashMap;

public class RunningRace {
    public String[] solution(String[] players, String[] callings) {
        HashMap<String, Integer> rank = new HashMap<>();

        for (int i = 0; i < players.length; i++) {
            rank.put(players[i], i);
        }

        for (String calling : callings) {
            int currentRank = rank.get(calling);
            String player = players[currentRank];

            if (currentRank > 0) {
                String frontPlayer = players[currentRank - 1];

                rank.put(player, currentRank - 1);
                rank.put(frontPlayer, currentRank);

                players[currentRank - 1] = player;
                players[currentRank] = frontPlayer;
            }
        }

        return players;
    }
    // @Test
    // public void 정답() {
    //     Assertions.assertEquals("[mumu, kai, mine, soe, poe]",
    //             Arrays.toString(solution(new String[] {"mumu", "soe", "poe", "kai", "mine"},
    //                     new String[] { "kai", "kai", "mine", "mine"})));
    // }
}
