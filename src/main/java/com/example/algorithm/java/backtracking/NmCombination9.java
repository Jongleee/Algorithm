package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class NmCombination9 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] input = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            input[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(input);

        boolean[] visited = new boolean[n];
        int[] sequence = new int[m];
        StringBuilder result = new StringBuilder();

        generatePermutations(input, visited, sequence, result, n, m, 0);
        System.out.print(result);
    }

    private static void generatePermutations(int[] input, boolean[] visited, int[] sequence, StringBuilder result,
            int n, int m, int depth) {
        if (depth == m) {
            for (int value : sequence) {
                result.append(value).append(' ');
            }
            result.append('\n');
            return;
        }

        int prev = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i] || input[i] == prev) {
                continue;
            }
            visited[i] = true;
            sequence[depth] = input[i];
            prev = input[i];
            generatePermutations(input, visited, sequence, result, n, m, depth + 1);
            visited[i] = false;
        }
    }
}

/*
4 2
9 7 9 1

1 7 
1 9
7 1
7 9
9 1
9 7
9 9


4 4
1 1 1 1

1 1 1 1 
*/