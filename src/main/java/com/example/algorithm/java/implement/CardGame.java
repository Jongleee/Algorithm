package com.example.algorithm.java.implement;

public class CardGame {
    private static int numberOfCards;

    public int solution(int initialCoins, int[] cards) {
        numberOfCards = cards.length;

        boolean[] hand = new boolean[numberOfCards + 1];
        boolean[] paid = new boolean[numberOfCards + 1];
        int coinsLeft = initialCoins;

        for (int i = 0; i < numberOfCards / 3; i++) {
            hand[cards[i]] = true;
            paid[cards[i]] = true;
        }

        int answer = 1;
        for (int i = numberOfCards / 3; i < numberOfCards; i += 2) {
            if (coinsLeft > 0) {
                hand[cards[i]] = true;
                hand[cards[i + 1]] = true;
            }

            boolean pass = false;
            int minCost = 3;
            int cardThrown = -1;

            for (int j = 1; j <= numberOfCards; j++) {
                if (!hand[j]) {
                    continue;
                }

                if (hand[numberOfCards + 1 - j]) {
                    int cost = (paid[j] ? 0 : 1) + (paid[numberOfCards + 1 - j] ? 0 : 1);
                    if (coinsLeft < cost || minCost <= cost) {
                        continue;
                    }

                    pass = true;
                    cardThrown = j;
                    minCost = cost;
                }
            }

            if (!pass) {
                break;
            }

            hand[cardThrown] = false;
            hand[numberOfCards + 1 - cardThrown] = false;
            coinsLeft -= minCost;

            answer++;
        }

        return answer;
    }

    // @Test
    // void 정답() {
    //     int[] coin = { 4, 3, 2, 10 };
    //     int[][] cards = { { 3, 6, 7, 2, 1, 10, 5, 9, 8, 12, 11, 4 },
    //             { 1, 2, 3, 4, 5, 8, 6, 7, 9, 10, 11, 12 },
    //             { 5, 8, 1, 2, 9, 4, 12, 11, 3, 10, 6, 7 },
    //             { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 } };

    //     int[] result = { 5, 2, 4, 1 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(coin[i], cards[i]));
    //     }
    // }
}
