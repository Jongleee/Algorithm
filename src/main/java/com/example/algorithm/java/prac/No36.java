package com.example.algorithm.java.prac;

public class No36 {
    class Solution {
        public String solution(int[] numbers, String hand) {
            StringBuilder answer = new StringBuilder();
            int rightLocation = 12;
            int leftLocation = 10;
            for (int i = 0; i < numbers.length; i++) {
                number0(numbers, i);
                if (numbers[i] % 3 == 0) {
                    rightLocation = right(numbers, answer, i);
                } else if (numbers[i] % 3 == 1) {
                    leftLocation = left(numbers, answer, i);
                } else {
                    int leftD = Math.abs(numbers[i] - leftLocation) / 3 + Math.abs(numbers[i] - leftLocation) % 3;
                    int rightD = Math.abs(numbers[i] - rightLocation) / 3 + Math.abs(numbers[i] - rightLocation) % 3;
                    if (rightD > leftD) {
                        leftLocation = left(numbers, answer, i);
                    } else if (rightD < leftD) {
                        rightLocation = right(numbers, answer, i);
                    } else {
                        if (hand.equals("right")) {
                            rightLocation = right(numbers, answer, i);
                        } else {
                            leftLocation = left(numbers, answer, i);
                        }
                    }
                }

            }
            return answer.toString();
        }

        private void number0(int[] numbers, int i) {
            if (numbers[i] == 0)
                numbers[i] = 11;
        }

        private int left(int[] numbers, StringBuilder answer, int i) {
            int leftLocation;
            answer.append("L");
            leftLocation = numbers[i];
            return leftLocation;
        }

        private int right(int[] numbers, StringBuilder answer, int i) {
            int rightLocation;
            answer.append("R");
            rightLocation = numbers[i];
            return rightLocation;
        }
    }
}
