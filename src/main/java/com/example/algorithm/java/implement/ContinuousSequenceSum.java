package com.example.algorithm.java.implement;

public class ContinuousSequenceSum {
    public int[] solution(int[] sequence, int k) {
        int left = 0;
        int right = 0;
        int minLength = Integer.MAX_VALUE;
        int currentSum = 0;
        int[] answer = new int[2];

        while (right < sequence.length && left <= right) {
            if (left == right) {
                currentSum = sequence[left];
            }

            if (currentSum == k) {
                int currentLength = right - left + 1;

                if (currentLength < minLength) {
                    minLength = currentLength;
                    answer[0] = left;
                    answer[1] = right;
                }

                currentSum -= sequence[left];

                currentSum = checkCondition(sequence, right, currentSum);

                if (left == right) {
                    break;
                }

                left++;
                right++;
            } else if (currentSum > k) {
                currentSum -= sequence[left];
                left++;
            } else {
                currentSum = checkCondition(sequence, right, currentSum);
                right++;
            }
        }

        return answer;
    }

    private int checkCondition(int[] sequence, int right, int currentSum) {
        if (right + 1 < sequence.length) {
            currentSum += sequence[right + 1];
        }
        return currentSum;
    }
}
