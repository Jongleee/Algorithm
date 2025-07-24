package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class ChecksPostFacto {
    static class BoardPair {
        char[][] startBoard;
        char[][] endBoard;

        BoardPair(char[][] start, char[][] end) {
            this.startBoard = start;
            this.endBoard = end;
        }
    }

    private static char oppositePlayer(char player) {
        return player == 'W' ? 'B' : 'W';
    }

    private static int squareX(int square) {
        return (square - 1) % 4 * 2 + 1 - (((square - 1) / 4) % 2);
    }

    private static int squareY(int square) {
        return (square - 1) / 4;
    }

    private static char[][] copyBoard(char[][] board) {
        char[][] copy = new char[8][8];
        for (int i = 0; i < 8; i++) {
            copy[i] = Arrays.copyOf(board[i], 8);
        }
        return copy;
    }

    private static BoardPair resolveJumpConstraints(char[][] currentBoard, char[][] startBoard, char player,
            char globalStartPlayer, char[] moveTypes, List<List<Integer>> movesList, int moveIndex, int stepInMove,
            boolean forEntireBoard, int px, int py) {
        int yStart = forEntireBoard ? 0 : py;
        int yEnd = forEntireBoard ? 8 : py + 1;
        int xStart = forEntireBoard ? 0 : px;
        int xEnd = forEntireBoard ? 8 : px + 1;

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                if (Character.toUpperCase(currentBoard[y][x]) != player)
                    continue;
                for (int dx = -1; dx <= 1; dx += 2) {
                    for (int dy = -1; dy <= 1; dy += 2) {
                        if ((currentBoard[y][x] == 'w' && dy == 1) || (currentBoard[y][x] == 'b' && dy == -1))
                            continue;
                        int x2 = x + dx * 2;
                        int y2 = y + dy * 2;
                        if (x2 < 0 || x2 >= 8 || y2 < 0 || y2 >= 8)
                            continue;
                        int mx = x + dx;
                        int my = y + dy;
                        if (Character.toUpperCase(currentBoard[my][mx]) != oppositePlayer(player))
                            continue;
                        if (currentBoard[y2][x2] == '.')
                            return new BoardPair(null, null);
                        if (currentBoard[y2][x2] == '?') {
                            char[][] newStart1 = copyBoard(startBoard);
                            newStart1[y2][x2] = (y2 == 0) ? 'W' : 'w';
                            BoardPair ret1 = doit(newStart1, globalStartPlayer, moveTypes, movesList);
                            if (ret1.startBoard != null)
                                return ret1;

                            char[][] newStart2 = copyBoard(startBoard);
                            newStart2[y2][x2] = (y2 == 7) ? 'B' : 'b';
                            BoardPair ret2 = doit(newStart2, globalStartPlayer, moveTypes, movesList);
                            if (ret2.startBoard != null)
                                return ret2;

                            return new BoardPair(null, null);
                        }
                    }
                }
            }
        }
        return null;
    }

    private static BoardPair doit(char[][] startBoard, char startPlayer, char[] moveTypes,
            List<List<Integer>> movesList) {
        char[][] currentBoard = copyBoard(startBoard);
        char player = startPlayer;

        for (int moveIndex = 0; moveIndex < moveTypes.length; moveIndex++) {
            char moveType = moveTypes[moveIndex];
            List<Integer> move = movesList.get(moveIndex);

            for (int j = 0; j < move.size() - 1; j++) {
                int sq1 = move.get(j);
                int sq2 = move.get(j + 1);
                int sx = squareX(sq1);
                int sy = squareY(sq1);
                int ex = squareX(sq2);
                int ey = squareY(sq2);

                boolean promoted = ((player == 'W' && ey == 0) || (player == 'B' && ey == 7))
                        && Character.isLowerCase(currentBoard[sy][sx]);

                if (moveType == '-') {
                    BoardPair result = resolveJumpConstraints(currentBoard, startBoard, player, startPlayer, moveTypes,
                            movesList, moveIndex, j, true, 0, 0);
                    if (result != null)
                        return result;
                }

                currentBoard[ey][ex] = currentBoard[sy][sx];
                currentBoard[sy][sx] = '.';
                if (promoted)
                    currentBoard[ey][ex] = Character.toUpperCase(currentBoard[ey][ex]);

                if (moveType == 'x') {
                    int mx = (sx + ex) / 2;
                    int my = (sy + ey) / 2;
                    currentBoard[my][mx] = '.';

                    if (j == move.size() - 2 && !promoted) {
                        BoardPair result = resolveJumpConstraints(currentBoard, startBoard, player, startPlayer,
                                moveTypes, movesList, moveIndex, j, false, ex, ey);
                        if (result != null)
                            return result;
                    }
                }
            }
            player = oppositePlayer(player);
        }
        return new BoardPair(startBoard, currentBoard);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        boolean first = true;

        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            if (!st.hasMoreTokens())
                continue;
            char startPlayer = st.nextToken().charAt(0);
            int moveCount = Integer.parseInt(st.nextToken());

            char[] moveTypes = new char[moveCount];
            List<List<Integer>> movesList = new ArrayList<>();
            for (int i = 0; i < moveCount; i++) {
                String moveStr = br.readLine();
                List<Integer> move = new ArrayList<>();
                char type = '-';
                int idx = 0;
                while (idx < moveStr.length()) {
                    int num = 0;
                    while (idx < moveStr.length() && Character.isDigit(moveStr.charAt(idx))) {
                        num = num * 10 + (moveStr.charAt(idx) - '0');
                        idx++;
                    }
                    move.add(num);
                    if (idx < moveStr.length())
                        type = moveStr.charAt(idx++);
                }
                moveTypes[i] = type;
                movesList.add(move);
            }

            char[][] startBoard = new char[8][8];
            char[][] board = new char[8][8];
            int[][] startX = new int[8][8];
            int[][] startY = new int[8][8];
            for (int y = 0; y < 8; y++) {
                Arrays.fill(startBoard[y], '?');
                Arrays.fill(board[y], '?');
                for (int x = 0; x < 8; x++) {
                    startX[y][x] = x;
                    startY[y][x] = y;
                }
            }

            char player = startPlayer;
            for (int i = 0; i < moveCount; i++) {
                List<Integer> move = movesList.get(i);
                for (int j = 0; j < move.size() - 1; j++) {
                    int sx = squareX(move.get(j));
                    int sy = squareY(move.get(j));
                    int ex = squareX(move.get(j + 1));
                    int ey = squareY(move.get(j + 1));
                    boolean promoted = (player == 'W' && ey == 0) || (player == 'B' && ey == 7);

                    if (board[sy][sx] == '?') {
                        board[sy][sx] = Character.toLowerCase(player);
                        startBoard[sy][sx] = Character.toLowerCase(player);
                    }
                    if (board[ey][ex] == '?') {
                        board[ey][ex] = '.';
                        startBoard[ey][ex] = '.';
                    }

                    if ((player == 'W') ^ (ey < sy) && Character.isLowerCase(board[sy][sx])) {
                        int origX = startX[sy][sx];
                        int origY = startY[sy][sx];
                        board[sy][sx] = Character.toUpperCase(board[sy][sx]);
                        startBoard[origY][origX] = Character.toUpperCase(startBoard[origY][origX]);
                    }

                    board[ey][ex] = board[sy][sx];
                    board[sy][sx] = '.';
                    startX[ey][ex] = startX[sy][sx];
                    startY[ey][ex] = startY[sy][sx];

                    if (promoted && j == move.size() - 2)
                        board[ey][ex] = Character.toUpperCase(board[ey][ex]);

                    if (moveTypes[i] == 'x') {
                        int mx = (sx + ex) / 2;
                        int my = (sy + ey) / 2;
                        if (board[my][mx] == '?') {
                            board[my][mx] = Character.toLowerCase(oppositePlayer(player));
                            startBoard[my][mx] = Character.toLowerCase(oppositePlayer(player));
                        }
                        board[my][mx] = '.';
                    }
                }
                player = oppositePlayer(player);
            }

            BoardPair result = doit(startBoard, startPlayer, moveTypes, movesList);

            if (!first)
                System.out.println();
            first = false;

            if (result.startBoard == null)
                continue;

            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    if ((y % 2) == (x % 2)) {
                        result.startBoard[y][x] = '-';
                        result.endBoard[y][x] = '-';
                    } else {
                        if (result.startBoard[y][x] == '?')
                            result.startBoard[y][x] = '.';
                        if (result.endBoard[y][x] == '?')
                            result.endBoard[y][x] = '.';
                    }
                }
            }

            for (int y = 0; y < 8; y++) {
                System.out.println(new String(result.startBoard[y]) + " " + new String(result.endBoard[y]));
            }
        }
    }
}

/*
W 3
21-17
13x22x31x24
19x28

-.-.-.-. -.-.-.-.
.-.-.-.- .-.-.-.-
-.-.-.-. -.-.-.-.
B-.-w-.- .-.-w-.-
-.-.-W-. -.-.-.-.
w-.-.-.- .-.-.-.-
-.-w-w-. -.-.-.-W
.-.-.-.- .-.-.-.-



B 5
2-7
9x2
32-27
2x11x18
5-9

-.-b-.-. -.-.-.-.
b-b-.-.- .-.-.-.-
-w-.-.-. -b-.-.-.
w-w-b-.- w-w-.-.-
-.-.-.-. -.-W-.-.
.-.-.-.- .-.-.-.-
-.-.-.-. -.-.-B-.
.-.-.-B- .-.-.-.-
*/