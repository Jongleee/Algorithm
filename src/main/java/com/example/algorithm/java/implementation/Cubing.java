package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Cubing {
    static class Cube {
        char[][] up = new char[3][3];
        char[][] down = new char[3][3];
        char[][] front = new char[3][3];
        char[][] back = new char[3][3];
        char[][] left = new char[3][3];
        char[][] right = new char[3][3];

        public Cube() {
            for (int i = 0; i < 3; i++) {
                Arrays.fill(up[i], 'w');
                Arrays.fill(down[i], 'y');
                Arrays.fill(front[i], 'r');
                Arrays.fill(back[i], 'o');
                Arrays.fill(left[i], 'g');
                Arrays.fill(right[i], 'b');
            }
        }

        private void rotateFace(char[][] face, boolean clockwise) {
            if (clockwise) {
                char temp = face[0][0];
                face[0][0] = face[2][0];
                face[2][0] = face[2][2];
                face[2][2] = face[0][2];
                face[0][2] = temp;
                temp = face[0][1];
                face[0][1] = face[1][0];
                face[1][0] = face[2][1];
                face[2][1] = face[1][2];
                face[1][2] = temp;
            } else {
                for (int i = 0; i < 3; i++) {
                    rotateFace(face, true);
                }
            }
        }

        public void upTurn(boolean clockwise) {
            rotateFace(up, clockwise);
            if (clockwise) {
                char[] temp = front[0];
                front[0] = right[0];
                right[0] = back[0];
                back[0] = left[0];
                left[0] = temp;
            } else {
                char[] temp = front[0];
                front[0] = left[0];
                left[0] = back[0];
                back[0] = right[0];
                right[0] = temp;
            }
        }

        public void downTurn(boolean clockwise) {
            rotateFace(down, clockwise);
            if (clockwise) {
                char[] temp = front[2];
                front[2] = left[2];
                left[2] = back[2];
                back[2] = right[2];
                right[2] = temp;
            } else {
                char[] temp = front[2];
                front[2] = right[2];
                right[2] = back[2];
                back[2] = left[2];
                left[2] = temp;
            }
        }

        public void frontTurn(boolean clockwise) {
            rotateFace(front, clockwise);
            if (clockwise) {
                for (int i = 0; i < 3; i++) {
                    char temp = up[2][i];
                    up[2][i] = left[2 - i][2];
                    left[2 - i][2] = down[0][2 - i];
                    down[0][2 - i] = right[i][0];
                    right[i][0] = temp;
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    char temp = up[2][i];
                    up[2][i] = right[i][0];
                    right[i][0] = down[0][2 - i];
                    down[0][2 - i] = left[2 - i][2];
                    left[2 - i][2] = temp;
                }
            }
        }

        public void backTurn(boolean clockwise) {
            rotateFace(back, clockwise);
            if (clockwise) {
                for (int i = 0; i < 3; i++) {
                    char temp = up[0][i];
                    up[0][i] = right[i][2];
                    right[i][2] = down[2][2 - i];
                    down[2][2 - i] = left[2 - i][0];
                    left[2 - i][0] = temp;
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    char temp = up[0][i];
                    up[0][i] = left[2 - i][0];
                    left[2 - i][0] = down[2][2 - i];
                    down[2][2 - i] = right[i][2];
                    right[i][2] = temp;
                }
            }
        }

        public void leftTurn(boolean clockwise) {
            rotateFace(left, clockwise);
            if (clockwise) {
                for (int i = 0; i < 3; i++) {
                    char temp = up[i][0];
                    up[i][0] = back[2 - i][2];
                    back[2 - i][2] = down[i][0];
                    down[i][0] = front[i][0];
                    front[i][0] = temp;
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    char temp = up[i][0];
                    up[i][0] = front[i][0];
                    front[i][0] = down[i][0];
                    down[i][0] = back[2 - i][2];
                    back[2 - i][2] = temp;
                }
            }
        }

        public void rightTurn(boolean clockwise) {
            rotateFace(right, clockwise);
            if (clockwise) {
                for (int i = 0; i < 3; i++) {
                    char temp = up[i][2];
                    up[i][2] = front[i][2];
                    front[i][2] = down[i][2];
                    down[i][2] = back[2 - i][0];
                    back[2 - i][0] = temp;
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    char temp = up[i][2];
                    up[i][2] = back[2 - i][0];
                    back[2 - i][0] = down[i][2];
                    down[i][2] = front[i][2];
                    front[i][2] = temp;
                }
            }
        }

        public void print() throws IOException {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 3; column++) {
                    writer.write(up[row][column]);
                }
                writer.newLine();
            }
            writer.flush();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int testCaseCount = Integer.parseInt(reader.readLine());

        for (int testCase = 0; testCase < testCaseCount; testCase++) {
            Cube cube = new Cube();
            int commandCount = Integer.parseInt(reader.readLine());
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

            for (int commandIndex = 0; commandIndex < commandCount; commandIndex++) {
                String command = tokenizer.nextToken();
                char face = command.charAt(0);
                boolean clockwise = command.charAt(1) == '+';

                switch (face) {
                    case 'U':
                        cube.upTurn(clockwise);
                        break;
                    case 'D':
                        cube.downTurn(clockwise);
                        break;
                    case 'F':
                        cube.frontTurn(clockwise);
                        break;
                    case 'B':
                        cube.backTurn(clockwise);
                        break;
                    case 'L':
                        cube.leftTurn(clockwise);
                        break;
                    case 'R':
                        cube.rightTurn(clockwise);
                        break;
                    default:
                        break;
                }
            }

            cube.print();
        }
    }
}

/*
4
1
L-
2
F+ B+
4
U- D- L+ R+
10
L- U- L+ U- L- U- U- L+ U+ U+

rww
rww
rww
bbb
www
ggg
gwg
owr
bwb
gwo
www
rww
*/