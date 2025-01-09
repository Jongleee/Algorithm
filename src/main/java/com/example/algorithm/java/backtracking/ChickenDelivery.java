package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ChickenDelivery {
    
    private static class Dist implements Comparable<Dist> {
        int idx, distance;

        Dist(int idx, int dist) {
            this.idx = idx;
            this.distance = dist;
        }

        @Override
        public int compareTo(Dist o) {
            return distance - o.distance;
        }
    }

    private static final int MAX_CHICKEN = 13;
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] homes = new int[n * n];
        int[] chickens = new int[MAX_CHICKEN];
        int homeCount = 0, chickenCount = 0;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int cell = Integer.parseInt(st.nextToken());
                if (cell == 1) {
                    homes[homeCount++] = i * n + j;
                } else if (cell == 2) {
                    chickens[chickenCount++] = i * n + j;
                }
            }
        }

        Dist[][] distances = calculateDistances(homes, homeCount, chickens, chickenCount, n);
        boolean[] selected = new boolean[chickenCount];
        int result = findMinChickenDistance(0, 0, m, homeCount, chickenCount, distances, selected);

        System.out.println(result);
    }

    private static Dist[][] calculateDistances(int[] homes, int homeCount, int[] chickens, int chickenCount, int n) {
        Dist[][] distances = new Dist[homeCount][chickenCount];
        for (int i = 0; i < homeCount; i++) {
            for (int j = 0; j < chickenCount; j++) {
                int distance = Math.abs(homes[i] / n - chickens[j] / n) + Math.abs(homes[i] % n - chickens[j] % n);
                distances[i][j] = new Dist(j, distance);
            }
            Arrays.sort(distances[i]);
        }
        return distances;
    }

    private static int findMinChickenDistance(int start, int depth, int m, int homeCount, int chickenCount,
            Dist[][] distances, boolean[] selected) {
        if (depth == m) {
            return calculateTotalChickenDistance(homeCount, distances, selected);
        }

        int minDistance = INF;
        for (int i = start; i < chickenCount; i++) {
            selected[i] = true;
            minDistance = Math.min(minDistance,
                    findMinChickenDistance(i + 1, depth + 1, m, homeCount, chickenCount, distances, selected));
            selected[i] = false;
        }
        return minDistance;
    }

    private static int calculateTotalChickenDistance(int homeCount, Dist[][] distances, boolean[] selected) {
        int totalDistance = 0;
        for (int i = 0; i < homeCount; i++) {
            for (Dist dist : distances[i]) {
                if (selected[dist.idx]) {
                    totalDistance += dist.distance;
                    break;
                }
            }
        }
        return totalDistance;
    }
}

/*
5 1
1 2 0 2 1
1 2 0 2 1
1 2 0 2 1
1 2 0 2 1
1 2 0 2 1

32


5 1
1 2 0 0 0
1 2 0 0 0
1 2 0 0 0
1 2 0 0 0
1 2 0 0 0

11
*/