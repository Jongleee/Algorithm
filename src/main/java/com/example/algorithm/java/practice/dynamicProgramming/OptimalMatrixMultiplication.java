package com.example.algorithm.java.practice.dynamicProgramming;

public class OptimalMatrixMultiplication {
    public static int solution(int[][] matrixSizes) {
        int length = matrixSizes.length;
        int[][] dp = new int[length][length];
        
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length-i; j++){
                int a = j;
                int b = j+i;
                if(a == b) dp[a][b] = 0;
                else{
                    for(int k = a; k < b; k++){
                        dp[a][b] = Math.min(dp[a][b], dp[a][k] + dp[k+1][b] + matrixSizes[a][0] * matrixSizes[k][1] * matrixSizes[b][1]);
                    }
                }
            }
        }
        
        return dp[0][length-1];
    }
    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{5,3},{3,10},{10,6}}));
    }
    //	270
}
