package com.example.algorithm.java.recursive;

public class PlayAloneMaster {
    public int solution(int[] cards) {
        int[] max = { 0, 0 };

        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == -1) {
                continue;
            }
            int circleSize = getCircuitSize(cards, i);
            if (max[0] < circleSize) {
                max[1] = max[0];
                max[0] = circleSize;
            } else if (max[1] < circleSize) {
                max[1] = circleSize;
            }
            if (max[0] == cards.length)
                return 0;
        }

        return max[0] * max[1];
    }

    private int getCircuitSize(int[] cards, int index) {
        if (cards[index] == -1) {
            return 0;
        }
        int nextIndex = cards[index] - 1;
        cards[index] = -1;
        return getCircuitSize(cards, nextIndex) + 1;
    }
}
