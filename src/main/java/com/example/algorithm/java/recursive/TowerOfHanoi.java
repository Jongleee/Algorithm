package com.example.algorithm.java.recursive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TowerOfHanoi {
    public static int[][] solution(int n) {
        List<int[]> moveList = new ArrayList<>();
        generateMoves(n, 1, 3, 2, moveList);
    
        int[][] answer = new int[moveList.size()][];
        for(int i = 0; i < moveList.size(); i++){
            answer[i] = moveList.get(i);
        }
        return answer;
    }
    
    private static void generateMoves(int n, int start, int to, int mid, List<int[]> moveList) {
        if(n == 1){
            moveList.add(new int[]{start, to});
            return;
        }
        generateMoves(n-1, start, mid, to, moveList);
        moveList.add(new int[]{start, to});
        generateMoves(n-1, mid, to, start, moveList);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(solution(3)));
    }
}
