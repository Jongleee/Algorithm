package com.example.algorithm.java.practice;

public class MazeEscapeCommand {
    public static String solution(int n, int m, int x, int y, int r, int c, int k) {
        String answer = "";
        int down = 0;
        int left = 0;
        int right = 0;
        int up = 0;
        int distance = Math.abs(x - r) + Math.abs(c - y);
        k -= distance;
        if (k < 0 || k % 2 != 0)
            return "impossible";
        if (x - r > 0)
            up = x - r;
        else
            down = r - x;
        if (y - c > 0)
            left = y - c;
        else
            right = c - y;

        answer += "d".repeat(down);
        down = Math.min(k / 2, n - (x + down));
        answer += "d".repeat(down);
        up += down;
        k -= 2 * down;

        answer += "l".repeat(left);
        left = Math.min(k / 2, y - left -1);
        answer += "l".repeat(left);
        right += left;
        k -= 2 * left;

        answer += "rl".repeat(k / 2);
        answer += "r".repeat(right);
        answer += "u".repeat(up);

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(3, 4, 2, 3, 3, 1, 5));
    }

    // "dllrl"
}
