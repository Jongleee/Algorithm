package com.example.algorithm.java.binarySearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.StringTokenizer;

public class MoveInArray {
    static int n;
    static int left;
    static int right;
    static boolean valid;
    static int[][] altitudes;
    static boolean[][] visited;
    static final int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        altitudes = new int[n][n];
        HashSet<Integer> altitudeSet = new HashSet<>();
        StringTokenizer st;
        int answer = 200;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                altitudes[i][j] = Integer.parseInt(st.nextToken());
                altitudeSet.add(altitudes[i][j]);
            }
        }

        ArrayList<Integer> altitudeList = new ArrayList<>(altitudeSet);
        Collections.sort(altitudeList);

        int lIndex = 0;
        int rIndex = 0;
        int startAltitude = altitudes[0][0];
        while (altitudeList.get(rIndex) < startAltitude)
            rIndex++;

        while (lIndex <= rIndex && rIndex < altitudeList.size()) {
            left = altitudeList.get(lIndex);
            if (startAltitude < left)
                break;
            right = altitudeList.get(rIndex);

            visited = new boolean[n][n];
            visited[0][0] = true;
            valid = false;
            dfs(0, 0);

            if (valid) {
                answer = Math.min(answer, right - left);
                lIndex++;
            } else {
                rIndex++;
            }
        }

        bw.write(Integer.toString(answer));
        bw.flush();
    }

    static void dfs(int r, int c) {
        if (r == n - 1 && c == n - 1) {
            valid = true;
            return;
        }

        for (int[] direction : directions) {
            int nr = r + direction[0];
            int nc = c + direction[1];
            if (isInBounds(nr, nc) && !visited[nr][nc]) {
                visited[nr][nc] = true;
                int nextAltitude = altitudes[nr][nc];
                if (left <= nextAltitude && nextAltitude <= right) {
                    dfs(nr, nc);
                }
            }
        }
    }

    static boolean isInBounds(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}

/*
5
1 1 3 6 8
1 2 2 5 5
4 4 0 3 3
8 0 2 3 4
4 3 0 2 1

2
*/