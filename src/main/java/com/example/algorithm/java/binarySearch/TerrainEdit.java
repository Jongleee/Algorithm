package com.example.algorithm.java.binarySearch;

public class TerrainEdit {
    public long solution(int[][] land, int p, int q) {
        long answer;
        long maxHeight = 0;
        long minHeight = Long.MAX_VALUE;

        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[i].length; j++) {
                maxHeight = Math.max(maxHeight, land[i][j]);
                minHeight = Math.min(minHeight, land[i][j]);
            }
        }

        answer = calculateCost(land, maxHeight, p, q);

        long front = minHeight;
        long rear = maxHeight;

        while (front <= rear) {
            long mid = (front + rear) / 2;
            long cost1 = calculateCost(land, mid, p, q);
            long cost2 = calculateCost(land, mid + 1, p, q);

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

    private long calculateCost(int[][] land, long height, int p, int q) {
        long cost = 0;

        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[i].length; j++) {
                if (land[i][j] < height) {
                    cost += (height - land[i][j]) * p;
                } else if (land[i][j] > height) {
                    cost += (land[i][j] - height) * q;
                }
            }
        }

        return cost;
    }

    // @Test
    // void 정답() {
    // int[][] land1 = { { 1, 2 }, { 2, 3 } };
    // int[][] land2 = { { 4, 4, 3 }, { 3, 2, 2 }, { 2, 1, 0 } };

    // Assertions.assertEquals(5, solution(land1, 3, 2));
    // Assertions.assertEquals(33, solution(land2, 5, 3));
    // }
}
