package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class DiceYutnori {
    static class Space {
        public static final Space LAST_SPACE = new Space(0);
        private final int value;
        private Space nextRed;
        private Space nextBlue;

        public Space(int value) {
            this.value = value;
        }

        public Space attachRed(int value) {
            return attachRed(new Space(value));
        }

        public Space attachRed(Space space) {
            nextRed = space;
            return space;
        }

        public Space attachBlue(int value) {
            return attachBlue(new Space(value));
        }

        public Space attachBlue(Space space) {
            nextBlue = space;
            return space;
        }

        public Space next(int moves) {
            return moveRecursively(0, moves);
        }

        private Space moveRecursively(int currentMove, int targetMoves) {
            if (currentMove == targetMoves) {
                return this;
            }
            if (nextRed == null) {
                return LAST_SPACE;
            }
            if (currentMove == 0 && nextBlue != null) {
                return nextBlue.moveRecursively(currentMove + 1, targetMoves);
            }
            return nextRed.moveRecursively(currentMove + 1, targetMoves);
        }

        public boolean hasOverlap(Space[] pieces) {
            int count = 0;
            for (Space piece : pieces) {
                if (this == piece) {
                    count++;
                }
            }
            return count >= 2;
        }
    }

    private static int[] dice;
    private static int minScore;
    private static Space startSpace;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        dice = new int[10];
        for (int i = 0; i < 10; i++) {
            dice[i] = Integer.parseInt(st.nextToken());
        }

        startSpace = initializeGameBoard();
        minScore = 400;

        playGame(0, new Space[4], 0, 0);

        System.out.println(400 - minScore);
    }

    private static Space initializeGameBoard() {
        Space[] spaces = new Space[41];
        for (int value = 0; value <= 40; value++) {
            spaces[value] = new Space(value);
        }

        Space tail = spaces[0];
        for (int value = 2; value <= 40; value += 2) {
            tail = tail.attachRed(spaces[value]);
        }

        spaces[10].attachBlue(13).attachRed(16).attachRed(19).attachRed(spaces[25]);
        spaces[20].attachBlue(22).attachRed(24).attachRed(spaces[25]);
        spaces[30].attachBlue(28).attachRed(27).attachRed(26).attachRed(spaces[25]);
        spaces[25].attachRed(30).attachRed(35).attachRed(spaces[40]);

        return spaces[0];
    }

    private static void playGame(int diceIndex, Space[] pieces, int pieceCount, int currentScore) {
        if (currentScore > minScore) return;
        if (diceIndex == 10) {
            minScore = Math.min(minScore, currentScore);
            return;
        }

        moveExistingPieces(diceIndex, pieces, pieceCount, currentScore);
        moveNewPiece(diceIndex, pieces, pieceCount, currentScore);
    }

    private static void moveExistingPieces(int diceIndex, Space[] pieces, int pieceCount, int currentScore) {
        for (int i = 0; i < pieceCount; i++) {
            if (pieces[i] == Space.LAST_SPACE) continue;

            Space[] nextPieces = Arrays.copyOf(pieces, 4);
            nextPieces[i] = pieces[i].next(dice[diceIndex]);

            if (nextPieces[i] != Space.LAST_SPACE && nextPieces[i].hasOverlap(nextPieces)) {
                continue;
            }

            playGame(diceIndex + 1, nextPieces, pieceCount, currentScore + getScore(nextPieces[i]));
        }
    }

    private static void moveNewPiece(int diceIndex, Space[] pieces, int pieceCount, int currentScore) {
        if (pieceCount >= 4) return;

        Space[] nextPieces = Arrays.copyOf(pieces, 4);
        nextPieces[pieceCount] = startSpace.next(dice[diceIndex]);

        if (nextPieces[pieceCount].hasOverlap(nextPieces)) {
            return;
        }

        playGame(diceIndex + 1, nextPieces, pieceCount + 1, currentScore + getScore(nextPieces[pieceCount]));
    }

    private static int getScore(Space piece) {
        return 40 - piece.value;
    }
}


/*
5 1 2 3 4 5 5 3 2 4

214


5 5 5 5 5 5 5 5 5 5

130
*/