package com.example.algorithm.java.math;

public class JumpTeleport {
    public int solution(int n) {
        int ans = 0;

        while (n != 0) {
            if (isEven(n)) {
                n = divideByTwo(n);
            } else {
                n = decrementByOne(n);
                ans++;
            }
        }

        return ans;
    }

    private boolean isEven(int number) {
        return number % 2 == 0;
    }

    private int divideByTwo(int number) {
        return number / 2;
    }

    private int decrementByOne(int number) {
        return number - 1;
    }
}
