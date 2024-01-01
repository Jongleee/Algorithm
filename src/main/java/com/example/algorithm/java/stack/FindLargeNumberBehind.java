package com.example.algorithm.java.stack;

import java.util.Stack;

public class FindLargeNumberBehind {
    public int[] solution(int[] numbers) {
        int[] answer = new int[numbers.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < numbers.length; i++) {
            while (!stack.isEmpty() && numbers[stack.peek()] < numbers[i]) {
                answer[stack.pop()] = numbers[i];
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            answer[stack.pop()] = -1;
        }

        return answer;
    }

    // @Test
    // void 정답() {
    //     int[] numbers1 = { 2, 3, 3, 5 };
    //     int[] result1 = { 3, 5, 5, -1 };

    //     int[] numbers2 = { 9, 1, 5, 3, 6, 2 };
    //     int[] result2 = { -1, 5, 6, 6, -1, -1 };
    //     Assertions.assertArrayEquals(result1, solution(numbers1));
    //     Assertions.assertArrayEquals(result2, solution(numbers2));
    // }
}
