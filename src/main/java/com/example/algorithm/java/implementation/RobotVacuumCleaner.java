package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RobotVacuumCleaner {
    private static final char WALL = '1';
    private static final char EMPTY = '0';
    private static final char CLEANED = '2';

    private static int cleanRoom(int position, int direction, int[] delta, char[] map) {
        int count = 1;
        map[position] = CLEANED;

        while (true) {
            boolean cleaned = false;

            for (int i = 0; i < 4; i++) {
                direction = turnLeft(direction);
                int nextPos = position + delta[direction];

                if (map[nextPos] == EMPTY) {
                    map[nextPos] = CLEANED;
                    position = nextPos;
                    count++;
                    cleaned = true;
                    break;
                }
            }

            if (!cleaned) {
                int backPos = position + delta[(direction + 2) & 3];
                if (map[backPos] == WALL) {
                    break;
                }
                position = backPos;
            }
        }
        return count;
    }

    private static int turnLeft(int direction) {
        return (direction + 3) & 3;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int rows = Integer.parseInt(st.nextToken());
        int cols = Integer.parseInt(st.nextToken());
        int mapWidth = cols * 2;

        st = new StringTokenizer(br.readLine(), " ");
        int startRow = Integer.parseInt(st.nextToken());
        int startCol = Integer.parseInt(st.nextToken());
        int direction = Integer.parseInt(st.nextToken());

        char[] map = new char[rows * mapWidth];
        br.read(map, 0, rows * mapWidth - 1);

        int cleanedArea = cleanRoom(startRow * mapWidth + (startCol * 2), direction,
                new int[] { -mapWidth, 2, mapWidth, -2 }, map);
        System.out.print(cleanedArea);
    }
}

/*

*/