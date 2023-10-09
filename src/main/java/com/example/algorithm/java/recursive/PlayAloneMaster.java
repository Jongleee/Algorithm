package com.example.algorithm.java.recursive;

public class PlayAloneMaster {
    public int solution(int[] cards) {
        int[] maxCircuitSizes = { 0, 0 };

        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == -1) {
                continue;
            }
            int currentCircuitSize = calculateCircuitSize(cards, i);
            if (currentCircuitSize > maxCircuitSizes[0]) {
                maxCircuitSizes[1] = maxCircuitSizes[0];
                maxCircuitSizes[0] = currentCircuitSize;
            } else if (currentCircuitSize > maxCircuitSizes[1]) {
                maxCircuitSizes[1] = currentCircuitSize;
            }
            if (maxCircuitSizes[0] == cards.length) {
                return 0;
            }
        }

        return maxCircuitSizes[0] * maxCircuitSizes[1];
    }

    private int calculateCircuitSize(int[] cards, int index) {
        if (cards[index] == -1) {
            return 0;
        }
        int nextIndex = cards[index] - 1;
        cards[index] = -1;
        return calculateCircuitSize(cards, nextIndex) + 1;
    }

    // @Test
    // public void 정답() {
    //     int[] c1 = { 8, 6, 3, 7, 2, 5, 1, 4 };
    //     Assertions.assertEquals(12, solution(c1));
    // }
}
