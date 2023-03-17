package com.example.algorithm.java.binarySearch;

public class TerrainEdit {
    public static long solution(int[][] land, int P, int Q) {
        long answer = -1;
        long maxHeight = 0;
        long minHeight = Long.MAX_VALUE;
    
        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[i].length; j++) {
                maxHeight = Math.max(maxHeight, land[i][j]);
                minHeight = Math.min(minHeight, land[i][j]);
            }
        }
    
        long front = minHeight;
        long rear = maxHeight;
        while (front <= rear) {
            long mid = (front + rear) / 2;
    
            long cost1 = getCost(land, mid, P, Q);
            long cost2 = getCost(land, mid + 1, P, Q);
    
            if (cost1 <= cost2) {
                answer = cost1;
                rear = mid - 1;
            } else {
                answer = cost2;
                front = mid + 1;
            }
        }
    
        return answer;
    }
    
    private static long getCost(int[][] land, long height, int P, int Q) {
        long cost = 0;
        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[i].length; j++) {
                if (land[i][j] < height) {
                    cost += (height - land[i][j]) * P;
                } else if (land[i][j] > height) {
                    cost += (land[i][j] - height) * Q;
                }
            }
        }
    
        return cost;
    }

    public static void main(String[] args) {
        int[][] l1 = { { 1, 2 }, { 2, 3 } };
        int p1 = 3;
        int q1 = 2;// 5
        int[][] l2 = { { 4, 4, 3 }, { 3, 2, 2 }, { 2, 1, 0 } };
        int p2 = 5;
        int q2 = 3;// 33
        System.out.println(solution(l1, p1, q1));
        System.out.println(solution(l2, p2, q2));

    }
}
