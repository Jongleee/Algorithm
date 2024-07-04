package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Fire {
    static final int WALL = -1;
    static final int FIRE = -2;
    static final int EMPTY = 0;
    static final int VISITED = 1;

    static final int MX = 1000;
    static final int[] dx = { 0, 1, 0, -1 };
    static final int[] dy = { 1, 0, -1, 0 };

    static int r;
    static int c;
    static int[][] posStates = new int[MX][MX];
    static Deque<Position> posQ = new ArrayDeque<>();

    public static void main(String[] args) throws IOException {
        readMaze();
        int minTimeToEscape = getMinTimeToEscape();
        String result = minTimeToEscape == -1 ? "IMPOSSIBLE" : Integer.toString(minTimeToEscape);
        System.out.println(result);
    }

    static void readMaze() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        for (int y = 0; y < r; ++y) {
            for (int x = 0; x < c; ++x) {
                char ch = (char) br.read();
                if (ch == '#')
                    posStates[y][x] = WALL;
                else if (ch == '.')
                    posStates[y][x] = EMPTY;
                else if (ch == 'F') {
                    posStates[y][x] = FIRE;
                    posQ.addFirst(new Position(x, y, FIRE));
                } else {
                    posStates[y][x] = VISITED;
                    posQ.addLast(new Position(x, y, VISITED));
                }
            }
            br.read();
        }
    }

    static int getMinTimeToEscape() {
        int escapeMinute = 0;
        do {
            escapeMinute++;
            int posQSize = posQ.size();
            while (posQSize-- > 0) {
                Position pos = posQ.poll();
                int x = pos.x;
                int y = pos.y;
                if (isManAtEdge(pos))
                    return escapeMinute;
                for (int d = 0; d < 4; ++d) {
                    int nx = x + dx[d];
                    int ny = y + dy[d];
                    if (ny < 0 || ny >= r || nx < 0 || nx >= c)
                        continue;
                    int nposState = posStates[ny][nx];
                    if (pos.state == VISITED) {
                        if (nposState != EMPTY)
                            continue;
                        posStates[ny][nx] = VISITED;
                        posQ.add(new Position(nx, ny, VISITED));
                    } else {
                        if (nposState != EMPTY && nposState != VISITED)
                            continue;
                        posStates[ny][nx] = FIRE;
                        posQ.add(new Position(nx, ny, FIRE));
                    }
                }
            }
        } while (!posQ.isEmpty());
        return -1;
    }

    static boolean isManAtEdge(Position pos) {
        if (pos.state != VISITED)
            return false;
        return pos.y == 0 || pos.y == r - 1 || pos.x == 0 || pos.x == c - 1;
    }

    static class Position {
        int x;
        int y;
        int state;
    
        Position(int x, int y, int state) {
            this.x = x;
            this.y = y;
            this.state = state;
        }
    }
}


/*
4 4
####
#JF#
#..#
#..#

3
 */