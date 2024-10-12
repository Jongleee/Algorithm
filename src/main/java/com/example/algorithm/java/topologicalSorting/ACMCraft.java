package com.example.algorithm.java.topologicalSorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class ACMCraft {
    private static class FastScanner {
        private BufferedReader br;
        private StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public int nextInt() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) {
                    return -1;
                }
                st = new StringTokenizer(line);
            }
            return Integer.parseInt(st.nextToken());
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner sc = new FastScanner();
        StringBuilder sb = new StringBuilder();

        int testCases = sc.nextInt();

        while (testCases-- > 0) {
            int numberOfBuilds = sc.nextInt();
            int numberOfRules = sc.nextInt();

            int[] buildCosts = new int[numberOfBuilds + 1];
            for (int i = 1; i <= numberOfBuilds; i++) {
                buildCosts[i] = sc.nextInt();
            }

            List<List<Integer>> dependencies = new ArrayList<>();
            for (int i = 0; i <= numberOfBuilds; i++) {
                dependencies.add(new ArrayList<>());
            }

            for (int i = 0; i < numberOfRules; i++) {
                int from = sc.nextInt();
                int to = sc.nextInt();
                dependencies.get(to).add(from);
            }

            int targetBuild = sc.nextInt();

            int[] cache = new int[numberOfBuilds + 1];
            Arrays.fill(cache, -1);

            int totalCost = computeMaxCost(targetBuild, dependencies, buildCosts, cache);
            sb.append(totalCost).append('\n');
        }

        System.out.print(sb.toString());
    }

    private static int computeMaxCost(int build, List<List<Integer>> dependencies, int[] buildCosts, int[] cache) {
        if (cache[build] != -1) {
            return cache[build];
        }

        int maxCost = 0;
        for (int dependency : dependencies.get(build)) {
            int depCost = computeMaxCost(dependency, dependencies, buildCosts, cache);
            if (depCost > maxCost) {
                maxCost = depCost;
            }
        }

        cache[build] = maxCost + buildCosts[build];
        return cache[build];
    }
}

/*
5
3 2
1 2 3
3 2
2 1
1
4 3
5 5 5 5
1 2
1 3
2 3
4
5 10
100000 99999 99997 99994 99990
4 5
3 5
3 4
2 5
2 4
2 3
1 5
1 4
1 3
1 2
4
4 3
1 1 1 1
1 2
3 2
1 4
4
7 8
0 0 0 0 0 0 0
1 2
1 3
2 4
3 4
4 5
4 6
5 7
6 7
7

6
5
399990
2
0
*/