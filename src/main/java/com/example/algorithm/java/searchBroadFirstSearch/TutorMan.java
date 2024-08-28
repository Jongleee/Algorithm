package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class TutorMan {
    static int[] dx = { 0, -1, 1, 0 };
    static int[] dy = { 1, 0, 0, -1 };
    static int rangeE;
    static int rangeO;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][n * 2];
        int lastTile = n * n - n / 2;
        rangeE = 2 * n;
        rangeO = 2 * n - 1;

        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < rangeE; j += 2) {
                    String s = br.readLine();
                    map[i][j] = s.charAt(0) - '0';
                    map[i][j + 1] = s.charAt(2) - '0';
                }
            } else {
                for (int j = 1; j < rangeO; j += 2) {
                    String s = br.readLine();
                    map[i][j] = s.charAt(0) - '0';
                    map[i][j + 1] = s.charAt(2) - '0';
                }
            }
        }

        int[][] pathInfo = new int[lastTile + 1][2];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        pathInfo[1][0] = 1;
        pathInfo[1][1] = 0;
        queue.offer(1);
        boolean pathFound = false;

        lastTile = findLastTile(n, map, lastTile, pathInfo, queue, pathFound);
        int pathLength = pathInfo[lastTile][0];
        sb.insert(0, lastTile);
        while (true) {
            lastTile = pathInfo[lastTile][1];
            if (lastTile == 0) {
                break;
            }
            sb.insert(0, " ").insert(0, lastTile);
        }

        sb.insert(0, "\n").insert(0, pathLength);
        System.out.print(sb.toString());
        br.close();
    }

    private static int findLastTile(int n, int[][] map, int lastTile, int[][] pathInfo,
            ArrayDeque<Integer> queue, boolean pathFound) {
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int x = (current - 1) / rangeO * 2 + (current - 1) % rangeO / n;
            int y = (current - 1) % rangeO % n * 2;
            if (x % 2 == 1) {
                y++;
            }

            for (int direction = 1; direction < 4; direction++) {
                int nx = x + dx[direction];
                if (nx < 0 || nx == n) {
                    continue;
                }

                int ny = y + dy[direction];
                if (ny < 0) {
                    continue;
                }

                int num = nx / 2 * rangeO + nx % 2 * n + 1 + (ny - 1) / 2;
                if (map[nx][ny] == map[x][y] && pathInfo[num][0] == 0) {
                    pathInfo[num][0] = pathInfo[current][0] + 1;
                    pathInfo[num][1] = current;
                    queue.add(num);
                    if (num == lastTile) {
                        pathFound = true;
                        break;
                    }
                }
            }

            if (pathFound) {
                break;
            }

            y++;
            for (int direction = 0; direction < 3; direction++) {
                int nx = x + dx[direction];
                if (nx < 0 || nx == n) {
                    continue;
                }

                int ny = y + dy[direction];
                if (ny == rangeE) {
                    continue;
                }

                int num = nx / 2 * rangeO + nx % 2 * n + 1 + ny / 2;
                if (map[nx][ny] == map[x][y] && pathInfo[num][0] == 0) {
                    pathInfo[num][0] = pathInfo[current][0] + 1;
                    pathInfo[num][1] = current;
                    queue.add(num);
                    if (num == lastTile) {
                        pathFound = true;
                        break;
                    }
                }
            }
            if (pathFound) {
                break;
            }
        }
        while (true) {
            if (pathInfo[lastTile][0] != 0) {
                break;
            }
            lastTile--;
        }
        return lastTile;
    }
}

/*
5
1 4
4 5
3 4
5 4
5 2
4 2
5 6
4 4
6 5
2 4
5 1
6 1
1 6
2 3
4 2
5 3
1 2
5 5
4 1
2 2
4 3
2 3
3 4

7
1 2 7 12 17 22 23


4
1 5
5 3
5 5
5 6
5 3
6 4
4 5
2 5
2 4
4 3
2 4
5 2
1 4
1 6

7
1 5 8 12 9 10 13
*/