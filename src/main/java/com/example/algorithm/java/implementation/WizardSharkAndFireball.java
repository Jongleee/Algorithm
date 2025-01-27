package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class WizardSharkAndFireball {
    static int n, m, k;
    static int[] dr = { -1, -1, 0, 1, 1, 1, 0, -1 };
    static int[] dc = { 0, 1, 1, 1, 0, -1, -1, -1 };
    static Cell[][] map;
    static Queue<FireBall> fireBalls;
    static Queue<Cell> cells;

    static class FireBall {
        int r, c, m, s, d, moveCnt;

        FireBall(int r, int c, int m, int s, int d, int moveCnt) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
            this.moveCnt = moveCnt;
        }
    }

    static class Cell {
        int r, c, firstFireBallDirc, fireBallNum, dircSum, massSum, speedSum, moveCnt;

        Cell(int r, int c) {
            this.r = r;
            this.c = c;
            clear();
        }

        void addFireBall(FireBall fireBall) {
            if (fireBallNum == 0) {
                firstFireBallDirc = fireBall.d;
            }
            dircSum |= (fireBall.d & 1) == 0 ? 2 : 1;
            massSum += fireBall.m;
            speedSum += fireBall.s;
            moveCnt = fireBall.moveCnt + 1;
            fireBallNum++;
        }

        void divideFireBalls(Queue<FireBall> fireBalls) {
            if (fireBallNum > 1) {
                int mass = massSum / 5;
                if (mass > 0) {
                    int speed = speedSum / fireBallNum;
                    int dirc = (dircSum == 3) ? 1 : 0;
                    for (int i = 0; i < 4; i++) {
                        fireBalls.add(new FireBall(r, c, mass, speed, dirc + 2 * i, moveCnt));
                    }
                }
            } else if (fireBallNum == 1) {
                fireBalls.add(new FireBall(r, c, massSum, speedSum, firstFireBallDirc, moveCnt));
            }
            clear();
        }

        private void clear() {
            firstFireBallDirc = 0;
            fireBallNum = 0;
            dircSum = 0;
            massSum = 0;
            speedSum = 0;
            moveCnt = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        setInput();
        System.out.println(solve());
    }

    static void setInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new Cell[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] = new Cell(i, j);
            }
        }

        fireBalls = new ArrayDeque<>();
        cells = new ArrayDeque<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            fireBalls.add(new FireBall(r, c, m, s, d, 0));
        }
    }

    static void moveFireBalls() {
        while (!fireBalls.isEmpty()) {
            FireBall cur = fireBalls.poll();
            cur.r = (cur.r + dr[cur.d] * (cur.s % n) + n) % n;
            cur.c = (cur.c + dc[cur.d] * (cur.s % n) + n) % n;
            map[cur.r][cur.c].addFireBall(cur);
            cells.add(map[cur.r][cur.c]);
        }
    }

    static void divideFireBalls() {
        while (!cells.isEmpty()) {
            Cell cur = cells.poll();
            if (cur.fireBallNum > 0) {
                cur.divideFireBalls(fireBalls);
            }
        }
    }

    static int solve() {
        int totalMass = 0;
        for (int i = 0; i < k; i++) {
            moveFireBalls();
            divideFireBalls();
        }
        while (!fireBalls.isEmpty()) {
            totalMass += fireBalls.poll().m;
        }
        return totalMass;
    }
}

/*
7 5 3
1 3 5 2 4
2 3 5 2 6
5 2 9 1 7
6 2 1 3 5
4 4 2 4 2

9


4 2 3
1 1 5 2 2
1 4 7 1 6

0
*/