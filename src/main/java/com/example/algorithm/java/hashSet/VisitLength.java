package com.example.algorithm.java.hashSet;

import java.util.HashSet;
import java.util.Set;

public class VisitLength {
    public int solution(String dirs) {
        Set<String> set = new HashSet<>();
        int[][] pos = new int[1][2];
        pos[0][0] = 0;
        pos[0][1] = 0;

        for (char direction : dirs.toCharArray()) {
            int[][] tempPos = new int[2][2];
            tempPos[0][0] = pos[0][0];
            tempPos[0][1] = pos[0][1];

            move(tempPos, direction);

            if (isValidMove(tempPos[0])) {
                String path1 = posToString(pos[0]) + posToString(tempPos[0]);
                String path2 = posToString(tempPos[0]) + posToString(pos[0]);

                set.add(path1);
                set.add(path2);

                pos[0][0] = tempPos[0][0];
                pos[0][1] = tempPos[0][1];
            }
        }

        return set.size() / 2;
    }

    private void move(int[][] pos, char direction) {
        switch (direction) {
            case 'U':
                pos[0][1]++;
                break;
            case 'D':
                pos[0][1]--;
                break;
            case 'R':
                pos[0][0]++;
                break;
            case 'L':
                pos[0][0]--;
                break;
            default:
                break;
        }
    }

    private boolean isValidMove(int[] pos) {
        return pos[0] >= -5 && pos[0] <= 5 && pos[1] >= -5 && pos[1] <= 5;
    }

    private String posToString(int[] pos) {
        return pos[0] + "," + pos[1];
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals(7, solution("ULURRDLLU"));
    //     Assertions.assertEquals(7, solution("LULLLLLLU"));
    // }
}
