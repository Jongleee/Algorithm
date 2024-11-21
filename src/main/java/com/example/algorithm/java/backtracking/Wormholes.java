package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Wormholes {
    static class Wormhole implements Comparable<Wormhole> {
        int x, y, connectedIndex;

        Wormhole(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void connect(int index) {
            this.connectedIndex = index;
        }

        @Override
        public int compareTo(Wormhole o) {
            return this.x - o.x;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        List<Wormhole> wormholes = new ArrayList<>();
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            wormholes.add(new Wormhole(x, y));
        }
        Collections.sort(wormholes);

        int result = findInfiniteLoops(wormholes, visited, 0, 0);
        System.out.println(result);
    }

    private static int findInfiniteLoops(List<Wormhole> wormholes, boolean[] visited, int count, int index) {
        if (count == wormholes.size()) {
            for (int i = 0; i < wormholes.size(); i++) {
                if (detectInfiniteLoop(wormholes, i)) {
                    return 1;
                }
            }
            return 0;
        }

        int totalLoops = 0;
        for (int i = index; i < wormholes.size(); i++) {
            if (visited[i])
                continue;

            visited[i] = true;
            for (int j = i + 1; j < wormholes.size(); j++) {
                if (visited[j])
                    continue;

                visited[j] = true;
                wormholes.get(i).connect(j);
                wormholes.get(j).connect(i);

                totalLoops += findInfiniteLoops(wormholes, visited, count + 2, i + 1);

                visited[j] = false;
            }
            visited[i] = false;
        }
        return totalLoops;
    }

    private static boolean detectInfiniteLoop(List<Wormhole> wormholes, int startIndex) {
        boolean[] visited = new boolean[wormholes.size()];
        int currentIndex = startIndex;

        while (true) {
            if (visited[currentIndex])
                return true;

            visited[currentIndex] = true;
            Wormhole connected = wormholes.get(wormholes.get(currentIndex).connectedIndex);

            currentIndex = findNextWormhole(wormholes, connected.x, connected.y);
            if (currentIndex == -1)
                return false;
        }
    }

    private static int findNextWormhole(List<Wormhole> wormholes, int x, int y) {
        for (int i = 0; i < wormholes.size(); i++) {
            if (wormholes.get(i).y == y && wormholes.get(i).x > x) {
                return i;
            }
        }
        return -1;
    }
}

/*
4
0 0
1 0
1 1
0 1

2
*/