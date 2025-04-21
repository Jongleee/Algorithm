package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class NmCombination2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int maxNumber = Integer.parseInt(st.nextToken());
        int selectCount = Integer.parseInt(st.nextToken());

        int[] combination = new int[selectCount];
        StringBuilder sb = new StringBuilder();

        generateCombinations(maxNumber, selectCount, 1, 0, combination, sb);
        System.out.print(sb);
    }

    private static void generateCombinations(int maxNumber, int selectCount, int start, int depth, int[] combination,
            StringBuilder sb) {
        if (depth == selectCount) {
            for (int num : combination) {
                sb.append(num).append(' ');
            }
            sb.append('\n');
            return;
        }

        for (int i = start; i <= maxNumber; i++) {
            combination[depth] = i;
            generateCombinations(maxNumber, selectCount, i + 1, depth + 1, combination, sb);
        }
    }
}

/*
3 1

1 
2
3


4 2

1 2 
1 3
1 4
2 3
2 4
3 4
*/