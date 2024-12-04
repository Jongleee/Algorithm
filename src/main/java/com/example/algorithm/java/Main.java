package com.example.algorithm.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static class Line {
        int left, right, bottom, top;

        Line(int left, int bottom, int right, int top) {
            this.left = left;
            this.right = right;
            this.bottom = bottom;
            this.top = top;
        }
    }

    static class Input {
        int time, dir;

        Input(int time, int dir) {
            this.time = time;
            this.dir = dir;
        }
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int border = Integer.parseInt(br.readLine());
        int numTurns = Integer.parseInt(br.readLine());

        Input[] inputs = new Input[numTurns + 2];
        for (int i = 0; i < numTurns; i++) {
            String[] str = br.readLine().split(" ");
            char direction = str[1].charAt(0);
            int dir = (direction == 'R') ? 0 : 1;
            inputs[i] = new Input(Integer.parseInt(str[0]), dir);
        }

        System.out.println(play(inputs, border, numTurns));
    }

    static long play(Input[] inputs, int border, int numTurns) {
        Line[] lines = new Line[3005];
        int[] dx = { 0, 1, 0, -1 };
        int[] dy = { 1, 0, -1, 0 };
        long time = 0;
        int x = 0, y = 0, dir = 0;
        long deadTime = 0;
        int lineIndex = 1;

        for (int i = 0; i <= numTurns; i++) {
            int newX = x, newY = y, turnTime = 0, turnDir = 0;

            if (i == numTurns) {
                turnTime = calculateTurnTime(dir, x, y, border);
                newX = x + dx[dir] * turnTime;
                newY = y + dy[dir] * turnTime;
                lineIndex--;
            } else {
                turnTime = inputs[i].time;
                turnDir = inputs[i].dir;
                newX = x + dx[dir] * turnTime;
                newY = y + dy[dir] * turnTime;
            }

            time += turnTime;
            if (isOutOfBounds(dir, newX, newY, border)) {
                deadTime = calculateDeadTime(dir, time, newX, newY, border);
            }

            lines[lineIndex] = new Line(x, y, newX, newY);

            for (int j = 1; j < lineIndex; j++) {
                if (checkCollision(lines[lineIndex], lines[j], dir)) {
                    deadTime = Math.min(deadTime == 0 ? time : deadTime, time);
                }
            }

            if (deadTime != 0)
                return deadTime;

            dir = (turnDir == 0) ? (dir + 1) % 4 : (dir + 3) % 4;
            x = newX;
            y = newY;
            lineIndex++;
        }

        return deadTime;
    }

    static int calculateTurnTime(int dir, int x, int y, int border) {
        if (dir == 0)
            return border + 1 - y;
        if (dir == 2)
            return y - (-border - 1);
        if (dir == 1)
            return border + 1 - x;
        return x - (-border - 1);
    }

    static boolean isOutOfBounds(int dir, int x, int y, int border) {
        if (dir == 0)
            return y > border;
        if (dir == 1)
            return x > border;
        if (dir == 2)
            return y < -border;
        return x < -border;
    }

    static long calculateDeadTime(int dir, long time, int newX, int newY, int border) {
        if (dir == 0)
            return time - (newY - border) + 1;
        if (dir == 1)
            return time - (newX - border) + 1;
        if (dir == 2)
            return time - (-border - newY) + 1;
        return time - (-border - newX) + 1;
    }

    static boolean checkCollision(Line line1, Line line2, int dir) {
        int top1 = line1.top, bottom1 = line1.bottom, left1 = line1.left, right1 = line1.right;
        int top2 = line2.top, bottom2 = line2.bottom, left2 = line2.left, right2 = line2.right;

        if (top1 > bottom2 || bottom1 < top2)
            return false;
        if (right1 < left2 || left1 > right2)
            return false;

        if (dir == 0)
            return right1 >= left2;
        if (dir == 1)
            return bottom1 >= top2;
        if (dir == 2)
            return left1 <= right2;
        return top1 <= bottom2;
    }
}

/*

*/