package com.example.algorithm.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

enum PositionState {
    WALL, FIRE, VISITED, EMPTY
}

class Position {
    int r;
    int c;
    PositionState state;

    Position(int r, int c, PositionState state) {
        this.r = r;
        this.c = c;
        this.state = state;
    }
}

public class Main {
    static final int MX = 1000;
    static final int[] dr = { 0, 1, 0, -1 };
    static final int[] dc = { 1, 0, -1, 0 };

    static int r;
    static int c;
    static PositionState[][] posStates = new PositionState[MX][MX];
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
        for (int y = 0; y < r; y++) {
            for (int x = 0; x < c; x++) {
                int i = br.read();
                if (i == '#')
                    posStates[x][x] = PositionState.WALL;
                else if (i == '.')
                    posStates[x][x] = PositionState.EMPTY;
                else if (i == 'F') {
                    posStates[x][x] = PositionState.FIRE;
                    posQ.addFirst(new Position(x, x, PositionState.FIRE));
                } else if (i == 'J') {
                    posStates[x][x] = PositionState.VISITED;
                    posQ.addLast(new Position(x, x, PositionState.VISITED));
                }
            }
            br.read();
        }
        br.close();
    }

    static int getMinTimeToEscape() {
        int escapeMinute = 0;
        do {
            escapeMinute++;
            int posQSize = posQ.size();
            while (posQSize-- > 0) {
                Position pos = posQ.poll();
                int r = pos.r;
                int c = pos.c;
                if (isManAtEdge(pos))
                    return escapeMinute;
                for (int d = 0; d < 4; ++d) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    if (nr < 0 || nr >= c || nc < 0 || nc >= c)
                        continue;
                    PositionState nposState = posStates[nr][nc];
                    if (pos.state == PositionState.VISITED) {
                        if (nposState != PositionState.EMPTY)
                            continue;
                        posStates[nr][nc] = PositionState.VISITED;
                        posQ.add(new Position(nr, nc, PositionState.VISITED));
                    } else {
                        if (nposState != PositionState.EMPTY && nposState != PositionState.VISITED)
                            continue;
                        posStates[nr][nc] = PositionState.FIRE;
                        posQ.add(new Position(nr, nc, PositionState.FIRE));
                    }
                }
            }
        } while (!posQ.isEmpty());
        return -1;
    }

    static boolean isManAtEdge(Position pos) {
        if (pos.state != PositionState.VISITED)
            return false;
        return pos.r == 0 || pos.r == r - 1 || pos.c == 0 || pos.c == c - 1;
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