package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Airport {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int g = Integer.parseInt(br.readLine());
        int[] parents = new int[g + 1];

        for (int i = 1; i <= g; i++) {
            parents[i] = i;
        }

        int p = Integer.parseInt(br.readLine());
        int dockedPlanes = 0;

        for (int i = 0; i < p; i++) {
            int planeGate = Integer.parseInt(br.readLine());

            int dockingGate = find(parents, planeGate);
            if (dockingGate == 0) {
                break;
            }

            dockedPlanes++;
            union(parents, dockingGate, dockingGate - 1);
        }

        System.out.println(dockedPlanes);
        br.close();
    }

    private static void union(int[] parents, int a, int b) {
        a = find(parents, a);
        b = find(parents, b);
        parents[a] = b;
    }

    private static int find(int[] parents, int x) {
        if (x == parents[x]) {
            return x;
        }
        parents[x] = find(parents, parents[x]);
        return parents[x];
    }
}

/*
4
3
4
1
1

2


4
6
2
2
3
3
4

3
*/