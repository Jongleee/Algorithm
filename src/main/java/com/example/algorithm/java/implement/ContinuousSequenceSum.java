package com.example.algorithm.java.implement;

public class ContinuousSequenceSum {
    public int[] solution(int[] sequence, int k) {
        int left = 0;
        int right = 0;
        int minLength = Integer.MAX_VALUE;
        int[] answer = new int[2];
        int currentSum = 0;

        while (right < sequence.length) {
            currentSum += sequence[right];
            while (currentSum > k) {
                currentSum -= sequence[left];
                left++;
            }
            if (currentSum == k) {
                int currentLength = right - left + 1;
                if (currentLength < minLength) {
                    minLength = currentLength;
                    answer[0] = left;
                    answer[1] = right;
                }
            }
            right++;
        }

        return answer;
    }

    // @Test
    // void 정답() {
    //     int[] sequence1 = { 1, 2, 3, 4, 5 };
    //     int[] sequence2 = { 1, 1, 1, 2, 3, 4, 5 };
    //     int[] sequence3 = { 2, 2, 2, 2, 2 };

    //     int[] result1 = { 2, 3 };
    //     int[] result2 = { 6, 6 };
    //     int[] result3 = { 0, 2 };

    //     Assertions.assertArrayEquals(result1, solution(sequence1, 7));
    //     Assertions.assertArrayEquals(result2, solution(sequence2, 5));
    //     Assertions.assertArrayEquals(result3, solution(sequence3, 6));
    // }
}
