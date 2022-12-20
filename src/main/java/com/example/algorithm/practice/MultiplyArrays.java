package com.example.algorithm.practice;

import java.util.Arrays;

public class MultiplyArrays {
    public static int[][] solution(int[][] arr1, int[][] arr2) {
        int[][] answer = new int [arr1.length][arr1[0].length];
        for(int i = 0; i < arr1.length; i++){
            for(int j = 0; j < arr1[0].length; j++){
                int sum = 0;
                for(int k = 0; k < arr2.length; k++){
                    sum += arr1[i][k] * arr2[k][j];
                    }
                answer[i][j] = sum;
            }
        }
        return answer;
    }
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(solution(new int [][]{{2, 3, 2}, {4, 2, 4}, {3, 1, 4}}, 
        new int [][]{{5, 4, 3}, {2, 4, 1}, {3, 1, 1}})));
    }
}