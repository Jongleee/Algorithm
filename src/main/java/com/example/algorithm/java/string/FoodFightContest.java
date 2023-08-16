package com.example.algorithm.java.string;

public class FoodFightContest {
    public String solution(int[] food) {
        StringBuilder answer = new StringBuilder();

        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();

        for (int i = 1; i < food.length; i++) {
            if (food[i] != 0) {
                int halfCount = food[i] / 2;
                left.append(String.valueOf(i).repeat(halfCount));
                right.insert(0, String.valueOf(i).repeat(halfCount));
            }
        }

        answer.append(left).append("0").append(right);

        return answer.toString();
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals("1223330333221", solution(new int[] { 1, 3, 4, 6 }));
    //     Assertions.assertEquals("111303111", solution(new int[] { 1, 7, 1, 2 }));
    // }
}
