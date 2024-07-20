package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class SwanLake {
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static int rows;
    static int cols;
    static char[][] board;
    static boolean[][] visitedSwan;
    static boolean[][] visitedWater;
    static ArrayDeque<Place> swanQueue = new ArrayDeque<>();
    static ArrayDeque<Place> waterQueue = new ArrayDeque<>();
    static Place swanEnd;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());
        
        visitedSwan = new boolean[rows][cols];
        visitedWater = new boolean[rows][cols];
        board = new char[rows][cols];

        initializeQueues(br);

        int days = 0;
        while (!swanFound()) {
            meltIce();
            days++;
        }
        System.out.println(days);
    }

    private static void initializeQueues(BufferedReader br) throws IOException {
        for (int i = 0; i < rows; i++) {
            board[i] = br.readLine().toCharArray();
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'L') {
                    board[i][j] = '.';
                    if (swanQueue.isEmpty()) {
                        swanQueue.add(new Place(i, j));
                        visitedSwan[i][j] = true;
                    } else {
                        swanEnd = new Place(i, j);
                    }
                }
                if (board[i][j] == '.') {
                    waterQueue.add(new Place(i, j));
                    visitedWater[i][j] = true;
                }
            }
        }
    }

    private static boolean swanFound() {
        swanQueue = bfs(swanQueue, visitedSwan, false);
        return visitedSwan[swanEnd.x][swanEnd.y];
    }

    private static void meltIce() {
        waterQueue = bfs(waterQueue, visitedWater, true);
    }

    private static ArrayDeque<Place> bfs(ArrayDeque<Place> queue, boolean[][] visited, boolean melt) {
        ArrayDeque<Place> newQueue = new ArrayDeque<>();
        while (!queue.isEmpty()) {
            Place current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];
                if (isValid(nx, ny) && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    if (board[nx][ny] == 'X') {
                        newQueue.add(new Place(nx, ny));
                        if (melt) board[nx][ny] = '.';
                    } else {
                        queue.add(new Place(nx, ny));
                    }
                }
            }
        }
        return newQueue;
    }

    private static boolean isValid(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    static class Place {
        int x;
        int y;
        Place(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

/*
10 2
.L
..
XX
XX
XX
XX
XX
XX
..
.L

3


4 11
..XXX...X..
.X.XXX...L.
....XXX..X.
X.L..XXX...
2


8 17
...XXXXXX..XX.XXX
....XXXXXXXXX.XXX
...XXXXXXXXXXXX..
..XXXXX.LXXXXXX..
.XXXXXX..XXXXXX..
XXXXXXX...XXXX...
..XXXXX...XXX....
....XXXXX.XXXL...

2
 */