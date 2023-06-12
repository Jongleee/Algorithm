package com.example.algorithm.java.hashSet;

import java.util.HashSet;

public class VisitLength {
    public int solution(String dirs) {
        HashSet<String> set = new HashSet<>();
        int[][] pos = new int[2][2];

        for (char direction : dirs.toCharArray()) {
            pos[1][0] = pos[0][0];
            pos[1][1] = pos[0][1];

            boolean flag = move(pos, direction);

            if (flag)
                continue;

            set.add(posToString(pos[0]) + posToString(pos[1]));
            set.add(posToString(pos[1]) + posToString(pos[0]));
        }

        return set.size() / 2;
    }

    private boolean move(int[][] pos, char direction) {
        boolean temp = false;
        switch (direction) {
            case 'U':
                pos[0][1]++;
                if (pos[0][1] > 5) {
                    temp = true;
                    pos[0][1] = 5;
                }
                break;
            case 'D':
                pos[0][1]--;
                if (pos[0][1] < -5) {
                    temp = true;
                    pos[0][1] = -5;
                }
                break;
            case 'R':
                pos[0][0]++;
                if (pos[0][0] > 5) {
                    temp = true;
                    pos[0][0] = 5;
                }
                break;
            case 'L':
                pos[0][0]--;
                if (pos[0][0] < -5) {
                    temp = true;
                    pos[0][0] = -5;
                }
                break;
            default:
                break;
        }
        return temp;
    }

    private String posToString(int[] pos) {
        return pos[0] + "," + pos[1];
    }
}
