package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TicTacToe {
    static char[][] board;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder resultBuilder = new StringBuilder();
        
        while (true) {
            String input = br.readLine();
            if (input.equals("end")) {
                break;
            }
            
            board = new char[3][3];
            int xCount = 0;
            int oCount = 0;
            
            for (int i = 0; i < 9; i++) {
                char current = input.charAt(i);
                board[i / 3][i % 3] = current;
                if (current == 'X') {
                    xCount++;
                } else if (current == 'O') {
                    oCount++;
                }
            }

            boolean valid = (xCount == oCount + 1 && ((xCount + oCount == 9 && !isBingo('O')) || (isBingo('X') && !isBingo('O'))))
                            || (xCount == oCount && isBingo('O') && !isBingo('X'));

            resultBuilder.append(valid ? "valid\n" : "invalid\n");
        }
        
        System.out.print(resultBuilder);
        br.close();
    }

    public static boolean isBingo(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }

        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }
}
/*
XOXOXOXOX
OXOXOXOXO
XXOOOXXOX
XO.OX...X
X.OO..X..
OOXXXOOXO
end

valid
invalid
valid
valid
invalid
invalid
 */