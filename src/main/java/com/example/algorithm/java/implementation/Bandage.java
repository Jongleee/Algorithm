package com.example.algorithm.java.implementation;

public class Bandage {
    public int solution(int[] bandage, int health, int[][] attacks) {
        int castTime = bandage[0];
        int healingPerSecond = bandage[1];
        int additionalHealing = bandage[2];
        int maxHealth = health;
        int preAttackTime = attacks[0][0];

        for (int[] attack : attacks) {
            int attackTime = attack[0];
            int damage = attack[1];
            int timeBetween = attackTime - preAttackTime - 1;

            if (timeBetween > 0) {
                health += (timeBetween * healingPerSecond);
                health += ((timeBetween / castTime) * additionalHealing);
                if (health > maxHealth) {
                    health = maxHealth;
                }
            }

            health -= damage;

            if (health <= 0) {
                return -1;
            }

            preAttackTime = attackTime;
        }

        return health;
    }

    // @Test
    // void 정답() {
    //     int[][] bandage = { { 5, 1, 5 }, { 3, 2, 7 }, { 4, 2, 7 }, { 1, 1, 1 } };
    //     int[] health = { 30, 20, 20, 5 };
    //     int[][][] attacks = { { { 2, 10 }, { 9, 15 }, { 10, 5 }, { 11, 5 } }, { { 1, 15 }, { 5, 16 }, { 8, 6 } },
    //             { { 1, 15 }, { 5, 16 }, { 8, 6 } }, { { 1, 2 }, { 3, 2 } } };
    //     int[] result = { 5, -1, -1, 3 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(bandage[i], health[i], attacks[i]));
    //     }
    // }
}
