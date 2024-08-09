package com.example.algorithm.java.bellmanFord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TimeMachine {
    static class Bus {
        int u;
        int v;
        int val;

        Bus(int u, int v, int val) {
            this.u = u;
            this.v = v;
            this.val = val;
        }
    }

    static int n;
    static Bus[] edges;
    static long[] distances;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        edges = new Bus[m];
        distances = new long[n + 1];

        for (int i = 1; i <= n; i++) {
            distances[i] = INF;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int val = Integer.parseInt(st.nextToken());
            edges[i] = new Bus(u, v, val);
        }

        if (bellmanFord(1)) {
            System.out.println("-1");
        } else {
            for (int i = 2; i <= n; i++) {
                if (distances[i] == INF) {
                    System.out.println("-1");
                } else {
                    System.out.println(distances[i]);
                }
            }
        }
    }

    static boolean bellmanFord(int start) {
        distances[start] = 0;

        for (int i = 1; i <= n; i++) {
            boolean updated = false;
            for (Bus bus : edges) {
                if (distances[bus.u] != INF && distances[bus.v] > distances[bus.u] + bus.val) {
                    distances[bus.v] = distances[bus.u] + bus.val;
                    updated = true;

                    if (i == n) {
                        return true;
                    }
                }
            }
            if (!updated) {
                break;
            }
        }
        return false;
    }
}

/*
3 4
1 2 4
1 3 3
2 3 -1
3 1 -2

4
3


3 4
1 2 4
1 3 3
2 3 -4
3 1 -2

-1


3 2
1 2 4
1 2 3

3
-1
*/