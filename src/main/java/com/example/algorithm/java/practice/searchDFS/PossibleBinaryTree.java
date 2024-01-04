package com.example.algorithm.java.practice.searchDFS;

public class PossibleBinaryTree {
    private int[] answer;

    public int[] solution(long[] numbers) {
        answer = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            answer[i] = 1;
            long number = numbers[i];
            int numberLength = (int) Math.floor(Math.log(number) / Math.log(2)) + 1;
            int exp = 1;
            int treeLength = 0;
            while (treeLength < numberLength) {
                treeLength = (int) Math.pow(2, exp++) - 1;
            }

            boolean[] node = new boolean[treeLength];
            int index = treeLength - 1;

            while (number > 0) {
                long div = number / 2;
                long mod = number % 2;
                number = div;
                node[index--] = (mod == 1);

                if (div == 0)
                    break;
            }

            solve(node, 0, treeLength - 1, false, i);
        }
        return answer;
    }

    private void solve(boolean[] node, int s, int e, boolean isEnd, int i) {
        int mid = (s + e) / 2;
        boolean currentNode = node[mid];

        if (isEnd && currentNode) {
            answer[i] = 0;
            return;
        }

        if (s != e) {
            solve(node, s, mid - 1, !currentNode, i);
            solve(node, mid + 1, e, !currentNode, i);
        }
    }

    // @Test
    // void 정답() {
    //     long[] numbers1 = { 7, 42, 5 };
    //     long[] numbers2 = { 63, 111, 95 };
    //     int[] result = { 1, 1, 0 };
    //     Assertions.assertArrayEquals(result, solution(numbers1));
    //     Assertions.assertArrayEquals(result, solution(numbers2));
    // }
}
