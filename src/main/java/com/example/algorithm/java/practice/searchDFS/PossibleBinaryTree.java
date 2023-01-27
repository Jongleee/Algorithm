package com.example.algorithm.java.practice.searchDFS;

import java.util.Arrays;

public class PossibleBinaryTree {
    
    private static boolean[] node; 
    private static int result;


    public static int[] solution(long[] numbers) {
        int[] answer = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            result = 1;
            long number = numbers[i];
            int numberLength = (int)Math.floor(Math.log(number) / Math.log(2)) + 1;
            int exp = 1;
            int treeLength = 0;
            while(treeLength < numberLength) {
                treeLength = (int)Math.pow(2, exp++) - 1;
            }

            node = new boolean[treeLength];
            int index = treeLength - 1;
            while(true) {
                long div = number / 2;
                long mod = number % 2;
                number = div;
                node[index--] = (mod == 1);
                if (div == 1) {
                    node[index] = true;
                    break;
                } else if (div == 0)
                    break;
            }
            solve(0, treeLength - 1, false);
            answer[i] = result;
        }
        return answer;
    }


    public static void solve(int s, int e, boolean isEnd) {
        int mid = (s + e) / 2;
        boolean currentNode = node[mid];
        if (isEnd && currentNode) {
            result = 0;
            return;
        }
        if (s != e) {
            solve(s, mid-1, !currentNode);
            solve(mid+1, e, !currentNode);
        }
    }
    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new long[] {63, 111, 95})));
    }
    //1,1,0
}
