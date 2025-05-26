package com.example.algorithm.java.bitmaskBruteForce;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TurnOffLights {
    public static int[] map;
    public static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        map = new int[10];
        for (int i = 0; i < 10; i++) {
            String line = br.readLine();
            for (int j = 0; j < 10; j++) {
                if (line.charAt(j) == 'O') {
                    map[i] |= 1 << j;
                }
            }
        }

        for (int state = 0; state < (1 << 10); state++) {
            int firstRow = map[0] ^ state ^ (state << 1) ^ (state >> 1);
            int count = Integer.bitCount(state);
            int nextRow = map[1] ^ state;
            dfs(2, firstRow, count, nextRow);
        }

        System.out.println(result == Integer.MAX_VALUE ? -1 : result);
    }

    private static void dfs(int depth, int temp, int count, int next) {
        if (temp >= (1 << 10)) {
            temp ^= (1 << 10);
        }

        if (result < count) {
            return;
        }

        if (depth == 10) {
            count += Integer.bitCount(temp);
            int last = next ^ temp ^ (temp << 1) ^ (temp >> 1);
            if (last == 0 || last == 1024) {
                result = count;
            }
        } else {
            int newRow = map[depth] ^ temp;
            count += Integer.bitCount(temp);
            int nextTemp = next ^ temp ^ (temp << 1) ^ (temp >> 1);
            dfs(depth + 1, nextTemp, count, newRow);
        }
    }
}

/*
#O########
OOO#######
#O########
####OO####
###O##O###
####OO####
##########
########O#
#######OOO
########O#

4
*/