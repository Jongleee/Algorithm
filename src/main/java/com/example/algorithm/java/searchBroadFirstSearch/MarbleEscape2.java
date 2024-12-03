package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class MarbleEscape2 {
    private static class Node {
        int redY, redX, blueY, blueX, time;

        Node(int redY, int redX, int blueY, int blueX, int time) {
            this.redY = redY;
            this.redX = redX;
            this.blueY = blueY;
            this.blueX = blueX;
            this.time = time;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        char[][] map = new char[n][m];
        int redY = 0, redX = 0, blueY = 0, blueX = 0;

        for (int i = 0; i < n; i++) {
            String row = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = row.charAt(j);
                if (map[i][j] == 'R') {
                    redY = i;
                    redX = j;
                    map[i][j] = '.';
                } else if (map[i][j] == 'B') {
                    blueY = i;
                    blueX = j;
                    map[i][j] = '.';
                }
            }
        }

        System.out.println(bfs(map, redY, redX, blueY, blueX, n, m));
    }

    private static int bfs(char[][] map, int redY, int redX, int blueY, int blueX, int n, int m) {
        int[] dy = { -1, 1, 0, 0 };
        int[] dx = { 0, 0, -1, 1 };
        boolean[][] visited = new boolean[n * m][n * m];
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(new Node(redY, redX, blueY, blueX, 1));
        visited[redY * m + redX][blueY * m + blueX] = true;

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.time > 10)
                return -1;

            for (int i = 0; i < 4; i++) {
                int newRedY = node.redY, newRedX = node.redX;
                int newBlueY = node.blueY, newBlueX = node.blueX;

                boolean redHole = false, blueHole = false;

                while (map[newRedY + dy[i]][newRedX + dx[i]] != '#') {
                    newRedY += dy[i];
                    newRedX += dx[i];
                    if (map[newRedY][newRedX] == 'O') {
                        redHole = true;
                        break;
                    }
                }

                while (map[newBlueY + dy[i]][newBlueX + dx[i]] != '#') {
                    newBlueY += dy[i];
                    newBlueX += dx[i];
                    if (map[newBlueY][newBlueX] == 'O') {
                        blueHole = true;
                        break;
                    }
                }

                if (blueHole)
                    continue;
                if (redHole)
                    return node.time;

                adjustPosition(m, visited, queue, node, i, newRedY, newRedX, newBlueY, newBlueX);
            }
        }
        return -1;
    }

    private static void adjustPosition(int m, boolean[][] visited, Queue<Node> queue, Node node, int i, int newRedY,
            int newRedX, int newBlueY, int newBlueX) {
        if (newRedY == newBlueY && newRedX == newBlueX) {
            if (i == 0) {
                if (node.redY > node.blueY)
                    newRedY++;
                else
                    newBlueY++;
            } else if (i == 1) {
                if (node.redY < node.blueY)
                    newRedY--;
                else
                    newBlueY--;
            } else if (i == 2) {
                if (node.redX > node.blueX)
                    newRedX++;
                else
                    newBlueX++;
            } else {
                if (node.redX < node.blueX)
                    newRedX--;
                else
                    newBlueX--;
            }
        }

        if (!visited[newRedY * m + newRedX][newBlueY * m + newBlueX]) {
            visited[newRedY * m + newRedX][newBlueY * m + newBlueX] = true;
            queue.add(new Node(newRedY, newRedX, newBlueY, newBlueX, node.time + 1));
        }
    }
}

/*
5 5
#####
#..B#
#.#.#
#RO.#
#####

1


10 10
##########
#R#...##B#
#...#.##.#
#####.##.#
#......#.#
#.######.#
#.#....#.#
#.#.#.#..#
#...#.O#.#
##########

-1


10 10
##########
#R#...##B#
#...#.##.#
#####.##.#
#......#.#
#.######.#
#.#....#.#
#.#.##...#
#O..#....#
##########

7
*/