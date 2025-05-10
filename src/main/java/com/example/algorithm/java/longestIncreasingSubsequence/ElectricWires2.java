package com.example.algorithm.java.longestIncreasingSubsequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ElectricWires2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] connections = new int[500001];
        int[] lis = new int[500001];
        int[] visited = new int[500001];
        int[] lisIndex = new int[500001];

        int lisSize = -1;

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            connections[a] = b;
        }

        for (int i = 0; i <= 500000; i++) {
            if (connections[i] == 0)
                continue;
            int idx = findLISPosition(lis, 0, lisSize, connections[i]);
            lis[idx] = connections[i];
            lisIndex[i] = idx + 1;
            if (idx > lisSize) {
                lisSize = idx;
            }
        }

        int seqLen = lisSize + 1;
        int minValue = Integer.MAX_VALUE;

        for (int i = 500000; i >= 1; i--) {
            if (lisIndex[i] == 0)
                continue;
            if (seqLen == lisIndex[i] && connections[i] < minValue) {
                visited[i] = 1;
                minValue = connections[i];
                seqLen--;
            }
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder result = new StringBuilder();
        int removeCount = 0;

        for (int i = 0; i <= 500000; i++) {
            if (visited[i] == 1 || connections[i] == 0)
                continue;
            result.append(i).append("\n");
            removeCount++;
        }

        sb.append(removeCount).append("\n");
        sb.append(result);
        System.out.print(sb.toString());
    }

    private static int findLISPosition(int[] lis, int left, int right, int target) {
        int result = right + 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (lis[mid] <= target) {
                left = mid + 1;
            } else {
                result = mid;
                right = mid - 1;
            }
        }
        return result;
    }
}

/*
8
1 8
3 9
2 2
4 1
6 4
10 10
9 7
7 6

3
1
2
3
*/