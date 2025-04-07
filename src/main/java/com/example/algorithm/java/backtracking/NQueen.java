package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NQueen {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int boardSize = Integer.parseInt(br.readLine());
        br.close();

        int result = getNQueenCount(boardSize);
        System.out.println(result);
    }

    private static int getNQueenCount(int boardSize) {
        if (boardSize == 1) {
            return 1;
        }
        if (boardSize <= 3) {
            return 0;
        }

        int fullMask = (1 << boardSize) - 1;
        int half = boardSize / 2;
        int result = 0;

        for (int col = 0; col < half; col++) {
            int bit = 1 << col;
            result += countSolutions(boardSize, fullMask, bit, bit >> 1, bit << 1);
        }

        result *= 2;

        if ((boardSize & 1) == 1) {
            int middleBit = 1 << half;
            result += countSolutions(boardSize, fullMask, middleBit, middleBit >> 1, middleBit << 1);
        }

        return result;
    }

    private static int countSolutions(int boardSize, int fullMask, int rowMask, int leftDiagonal, int rightDiagonal) {
        if (rowMask == fullMask) {
            return 1;
        }

        int validPositions = ~(rowMask | leftDiagonal | rightDiagonal) & fullMask;
        int count = 0;

        while (validPositions != 0) {
            int bit = validPositions & -validPositions;
            validPositions ^= bit;

            count += countSolutions(
                    boardSize,
                    fullMask,
                    rowMask | bit,
                    (leftDiagonal | bit) >> 1,
                    (rightDiagonal | bit) << 1);
        }

        return count;
    }
}

/*
8

92
*/