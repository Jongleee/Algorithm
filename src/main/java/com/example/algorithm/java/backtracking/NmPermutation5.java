package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class NmPermutation5 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int total = Integer.parseInt(st.nextToken());
        int selectCount = Integer.parseInt(st.nextToken());

        int[] numbers = new int[total];
        int[] selected = new int[selectCount];
        boolean[] visited = new boolean[total];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < total; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(numbers);

        StringBuilder sb = new StringBuilder();
        generatePermutations(numbers, visited, selected, 0, sb);
        System.out.print(sb);
    }

    private static void generatePermutations(int[] numbers, boolean[] visited, int[] selected, int depth,
            StringBuilder sb) {
        if (depth == selected.length) {
            for (int num : selected) {
                sb.append(num).append(' ');
            }
            sb.append('\n');
            return;
        }

        for (int i = 0; i < numbers.length; i++) {
            if (visited[i])
                continue;

            visited[i] = true;
            selected[depth] = numbers[i];
            generatePermutations(numbers, visited, selected, depth + 1, sb);
            visited[i] = false;
        }
    }
}

/*
3 1
4 5 2

2 
4
5


4 2
9 8 7 1

1 7 
1 8
1 9
7 1
7 8
7 9
8 1
8 7
8 9
9 1
9 7
9 8
*/