package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Snake {
    static class Line {
        int left, right, bottom, top;

        Line(int left, int right, int bottom, int top) {
            this.left = left;
            this.right = right;
            this.bottom = bottom;
            this.top = top;
        }
    }

    static class Input {
        int t;
        int dir;

        Input(int t, int dir) {
            this.t = t;
            this.dir = dir;
        }
    }

    static Line[] lines;
    static int dir;
    static int border;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        border = Integer.parseInt(br.readLine());
        int n = Integer.parseInt(br.readLine());
        Input[] inputs = new Input[n + 2];

        for (int i = 0; i < n; i++) {
            String[] str = br.readLine().split(" ");
            char direction = str[1].charAt(0);
            inputs[i] = new Input(Integer.parseInt(str[0]), direction == 'R' ? 0 : 1);
        }

        System.out.println(play(inputs, n));
    }

    static long play(Input[] inputs, int n) {
        lines = new Line[3005];
        dir = 0;
        int[] dx = { 0, 1, 0, -1 };
        int[] dy = { 1, 0, -1, 0 };

        int x = 0, y = 0, lineIndex = 1;
        long t = 0, deadTime = 0;

        for (int i = 0; i < n + 1; i++) {
            int newX = 0, newY = 0, turnTime = 0, turnDir = 0;

            if (i == n) {
                switch (dir) {
                    case 0:
                        newY = border + 1;
                        turnTime = newY - y;
                        break;

                    case 1:
                        newX = border + 1;
                        turnTime = newX - x;
                        break;

                    case 2:
                        newY = -border - 1;
                        turnTime = y - newY;
                        break;

                    case 3:
                        newX = -border - 1;
                        turnTime = x - newX;
                        break;

                    default:
                        break;
                }
                lineIndex--;
            } else {
                turnTime = inputs[i].t;
                turnDir = inputs[i].dir;
                newX = x + dx[dir] * turnTime;
                newY = y + dy[dir] * turnTime;
            }

            t += turnTime;
            deadTime = updateDeadTime(x, y, lineIndex, t, deadTime, newX, newY);

            deadTime = checkCollision(lineIndex, t, deadTime);

            if (deadTime != 0)
                return deadTime;

            if (turnDir == 0) {
                dir = (dir + 1) % 4;
            } else {
                dir = (dir + 3) % 4;
            }

            x = newX;
            y = newY;
            lineIndex++;
        }

        return deadTime;
    }

    private static long updateDeadTime(int x, int y, int lineIndex, long t, long deadTime,
            int newX, int newY) {
        int diff = 0;

        switch (dir) {
            case 0:
                lines[lineIndex] = new Line(y, newY, x, x);
                diff = newY - border;
                break;

            case 1:
                lines[lineIndex] = new Line(y, y, newX, x);
                diff = newX - border;
                break;

            case 2:
                lines[lineIndex] = new Line(newY, y, x, x);
                diff = -border - newY;
                break;

            case 3:
                lines[lineIndex] = new Line(y, y, x, newX);
                diff = -border - newX;
                break;

            default:
                break;
        }

        if (diff > 0) {
            deadTime = t - diff + 1;
        }
        return deadTime;
    }

    private static long checkCollision(int lineIndex, long t, long deadTime) {
        for (int j = 1; j < lineIndex - 1; j++) {
            int newTop = lines[lineIndex].top, newBottom = lines[lineIndex].bottom;
            int newLeft = lines[lineIndex].left, newRight = lines[lineIndex].right;
            int oldTop = lines[j].top, oldBottom = lines[j].bottom;
            int oldLeft = lines[j].left, oldRight = lines[j].right;

            if (newTop > oldBottom || newBottom < oldTop || newRight < oldLeft || newLeft > oldRight) {
                continue;
            }

            int diff = 0;
            switch (dir) {
                case 0:
                    diff = newRight - oldLeft;
                    break;

                case 1:
                    diff = newBottom - oldTop;
                    break;

                case 2:
                    diff = oldRight - newLeft;
                    break;

                case 3:
                    diff = oldBottom - newTop;
                    break;

                default:
                    break;
            }

            deadTime = (deadTime == 0) ? t - diff : Math.min(t - diff, deadTime);
        }
        return deadTime;
    }
}

/*
3
4
2 L
2 L
1 L
5 R

7


3
3
2 L
4 L
4 R

6
*/