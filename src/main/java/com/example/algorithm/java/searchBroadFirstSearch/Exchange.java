package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Exchange {
    static class Info {
        int[] num;
        int depth;

        Info(int[] num, int depth) {
            this.num = num;
            this.depth = depth;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        char[] input = st.nextToken().toCharArray();
        int k = Integer.parseInt(st.nextToken());
        int length = input.length;

        if (length <= 1) {
            System.out.println(-1);
            return;
        }

        int[] num = new int[length + 1];
        for (int i = 1; i <= length; i++) {
            num[i] = input[i - 1] - '0';
        }

        boolean[][] visited = new boolean[k + 1][1_000_001];
        int maxNum = -1;
        Queue<Info> queue = new LinkedList<>();
        queue.offer(new Info(num, 0));

        while (!queue.isEmpty()) {
            Info current = queue.poll();
            if (current.depth >= k) {
                maxNum = Math.max(maxNum, getInt(current.num, length));
                continue;
            }

            for (int i = 1; i <= length; i++) {
                for (int j = i + 1; j <= length; j++) {
                    if (i == 1 && current.num[j] == 0)
                        continue;
                    int[] swapped = current.num.clone();
                    swap(i, j, swapped);
                    int numValue = getInt(swapped, length);
                    if (!visited[current.depth + 1][numValue]) {
                        visited[current.depth + 1][numValue] = true;
                        queue.offer(new Info(swapped, current.depth + 1));
                    }
                }
            }
        }

        System.out.println(maxNum);
    }

    public static void swap(int a, int b, int[] arr) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static int getInt(int[] arr, int length) {
        int result = 0;
        int multiplier = 1;
        for (int i = length; i > 0; i--) {
            result += arr[i] * multiplier;
            multiplier *= 10;
        }
        return result;
    }
}

/*
132 3

312


16375 1

76315


432 1

423


90 4

-1
*/