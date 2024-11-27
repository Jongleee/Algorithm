package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class SnakeAndLadderGame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] board = new int[101];
        boolean[] visited = new boolean[101];
        int[] distance = new int[101];
        Queue<Integer> queue = new LinkedList<>();

        String[] nm = br.readLine().split(" ");
        int ladderCount = Integer.parseInt(nm[0]);
        int snakeCount = Integer.parseInt(nm[1]);

        for (int i = 0; i < ladderCount; i++) {
            String[] ladder = br.readLine().split(" ");
            board[Integer.parseInt(ladder[0])] = Integer.parseInt(ladder[1]);
        }

        for (int i = 0; i < snakeCount; i++) {
            String[] snake = br.readLine().split(" ");
            board[Integer.parseInt(snake[0])] = Integer.parseInt(snake[1]);
        }

        queue.offer(1);
        visited[1] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == 100) {
                System.out.println(distance[current]);
                return;
            }
            for (int dice = 1; dice <= 6; dice++) {
                int next = current + dice;
                if (next > 100) continue;
                if (board[next] != 0) next = board[next];
                if (!visited[next]) {
                    visited[next] = true;
                    distance[next] = distance[current] + 1;
                    queue.offer(next);
                }
            }
        }
    }
}


/*
3 7
32 62
42 68
12 98
95 13
97 25
93 37
79 27
75 19
49 47
67 17

3


4 9
8 52
6 80
26 42
2 72
51 19
39 11
37 29
81 3
59 5
79 23
53 7
43 33
77 21

5
*/