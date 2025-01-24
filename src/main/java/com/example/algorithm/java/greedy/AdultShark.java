package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class AdultShark {
    static final int[] DI = { 0, -1, 1, 0, 0 };
    static final int[] DJ = { 0, 0, 0, -1, 1 };
    static int n, m, k, remainShark, curTime;
    static Shark[] sharks;
    static PointInfo[][] map;

    static class Shark {
        int i, j, curDir, num;
        int[][] dirPriority = new int[5][4];

        Shark(int i, int j, int num) {
            this.i = i;
            this.j = j;
            this.num = num;
        }
    }

    static class PointInfo {
        int num, markedTime, markShark;

        PointInfo(int num) {
            this.num = num;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine(), " ");
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        remainShark = m;

        sharks = new Shark[m + 1];
        map = new PointInfo[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < n; j++) {
                int num = Integer.parseInt(st.nextToken());
                map[i][j] = new PointInfo(num);
                if (num > 0) {
                    sharks[num] = new Shark(i, j, num);
                }
            }
        }

        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i <= m; i++) {
            sharks[i].curDir = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= m; i++) {
            Shark shark = sharks[i];
            for (int j = 1; j <= 4; j++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int l = 0; l < 4; l++) {
                    shark.dirPriority[j][l] = Integer.parseInt(st.nextToken());
                }
            }
        }

        System.out.println(simulate());
    }

    static int simulate() {
        while (remainShark > 1 && curTime <= 1000) {
            markSmell();
            moveSharks();
            curTime++;
        }
        return curTime > 1000 ? -1 : curTime;
    }

    static void markSmell() {
        for (Shark shark : sharks) {
            if (shark == null)
                continue;
            map[shark.i][shark.j].markedTime = curTime + k;
            map[shark.i][shark.j].markShark = shark.num;
        }
    }

    static void moveSharks() {
        int[][] nextPositions = new int[m + 1][];
        for (Shark shark : sharks) {
            if (shark == null)
                continue;
            nextPositions[shark.num] = findNextPosition(shark);
        }

        resolveConflicts(nextPositions);
    }

    static int[] findNextPosition(Shark shark) {
        int[] priorities = shark.dirPriority[shark.curDir];
        int[] bestOption = null;

        for (int dir : priorities) {
            int ni = shark.i + DI[dir];
            int nj = shark.j + DJ[dir];
            if (!isInBounds(ni, nj))
                continue;

            if (map[ni][nj].markedTime <= curTime) {
                shark.curDir = dir;
                return new int[] { ni, nj };
            }

            if (map[ni][nj].markShark == shark.num && bestOption == null) {
                shark.curDir = dir;
                bestOption = new int[] { ni, nj };
            }
        }
        return bestOption;
    }

    static void resolveConflicts(int[][] nextPositions) {
        for (int i = 1; i <= m; i++) {
            if (sharks[i] == null)
                continue;

            Shark shark = sharks[i];
            map[shark.i][shark.j].num = 0;

            int[] nextPos = nextPositions[i];
            shark.i = nextPos[0];
            shark.j = nextPos[1];

            if (map[shark.i][shark.j].num != 0) {
                sharks[i] = null;
                remainShark--;
            } else {
                map[shark.i][shark.j].num = shark.num;
            }
        }
    }

    static boolean isInBounds(int i, int j) {
        return i >= 0 && i < n && j >= 0 && j < n;
    }
}

/*
4 2 6
1 0 0 0
0 0 0 0
0 0 0 0
0 0 0 2
4 3
1 2 3 4
2 3 4 1
3 4 1 2
4 1 2 3
1 2 3 4
2 3 4 1
3 4 1 2
4 1 2 3

26


5 4 10
0 0 0 0 3
0 0 0 0 0
1 2 0 0 0
0 0 0 0 4
0 0 0 0 0
4 4 3 1
2 3 1 4
4 1 2 3
3 4 2 1
4 3 1 2
2 4 3 1
2 1 3 4
3 4 1 2
4 1 2 3
4 3 2 1
1 4 3 2
1 3 2 4
3 2 1 4
3 4 1 2
3 2 4 1
1 4 2 3
1 4 2 3

-1
*/