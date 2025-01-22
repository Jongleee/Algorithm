package com.example.algorithm.java.bitmask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MonominoDomino2 {
    private static int score = 0;

    public static void main(String[] args) throws IOException {
        int count = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int bb = 0, gb = 0;

        for (int i = 0; i < n; i++) {
            String[] input = br.readLine().split(" ");
            int t = Integer.parseInt(input[0]);
            int r = Integer.parseInt(input[1]);
            int c = Integer.parseInt(input[2]);

            bb = processBlock(bb, t, r, true);
            gb = processBlock(gb, t, c, false);

            bb = clearFullLines(bb);
            gb = clearFullLines(gb);

            bb = clearLightRows(bb);
            gb = clearLightRows(gb);
        }

        for (int i = 0; i < 16; i++) {
            if ((bb & 1 << i) != 0)
                count++;
            if ((gb & 1 << i) != 0)
                count++;
        }
        System.out.println(score);
        System.out.println(count);
    }

    private static int processBlock(int board, int t, int pos, boolean isBlue) {
        int mask = 0;
        if (t == 1) {
            mask = 1 << pos;
        } else if (t == 2) {
            mask = isBlue ? (1 << pos) | (1 << (pos + 4)) : (1 << pos) | (1 << (pos + 1));
        } else {
            mask = isBlue ? (1 << pos) | (1 << (pos + 1)) : (1 << pos) | (1 << (pos + 4));
        }
        return addBlock(board, mask);
    }

    private static int clearFullLines(int board) {
        int fullLineMask = 15;
        for (int t = 0; t < 2; t++) {
            for (int i = 0; i < 4; i++) {
                int lineMask = fullLineMask << (4 * i);
                if ((board & lineMask) != lineMask)
                    continue;

                int temp = (board & ((1 << (4 * i)) - 1)) << 4;
                board -= (board & ((1 << (4 * (i + 1))) - 1));
                board |= temp;
                board = board >> 4;
                score++;
                break;
            }
        }
        return board;
    }

    private static int clearLightRows(int board) {
        while ((board & (15 << 16)) != 0) {
            board >>= 4;
        }
        return board;
    }

    private static int addBlock(int board, int mask) {
        int shiftedMask = mask << 20;
        while (((shiftedMask >> 4) & board) == 0 && shiftedMask != mask) {
            shiftedMask >>= 4;
        }
        return board | shiftedMask;
    }
}

/*
5
1 1 1
2 3 0
3 2 2
3 2 3
3 1 3

1
12


7
1 1 1
2 3 0
3 2 2
3 2 3
3 1 3
2 0 0
3 2 0

1
18
*/