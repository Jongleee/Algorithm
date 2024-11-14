package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AddParentheses3 {
    static class DPValue {
        int max, min;

        DPValue(int max, int min) {
            this.max = max;
            this.min = min;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Character> operators = new ArrayList<>();
        int size = n / 2 + 2;
        DPValue[][] dp = new DPValue[size][size];

        String expression = br.readLine();
        parseExpression(expression, numbers, operators, dp);

        for (int i = 2; i < size; i++) {
            for (int j = i - 1; j > 0; j--) {
                calculateMaxMin(dp, operators, i, j);
            }
        }

        bw.write(dp[1][size - 1].max + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static void parseExpression(String expression, ArrayList<Integer> numbers, ArrayList<Character> operators,
            DPValue[][] dp) {
        for (int i = 1; i <= expression.length(); i++) {
            char ch = expression.charAt(i - 1);
            if (i % 2 == 1) {
                int num = Character.getNumericValue(ch);
                numbers.add(num);
                dp[(i / 2) + 1][(i / 2) + 1] = new DPValue(num, num);
            } else {
                operators.add(ch);
            }
        }
    }

    private static void calculateMaxMin(DPValue[][] dp, ArrayList<Character> operators,
            int x, int y) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < x - y; i++) {
            DPValue left = dp[y][y + i];
            DPValue right = dp[y + i + 1][x];
            char op = operators.get(y + i - 1);

            int[] results = computeResults(left, right, op);
            max = Math.max(max, results[0]);
            min = Math.min(min, results[1]);
        }
        dp[y][x] = new DPValue(max, min);
    }

    private static int[] computeResults(DPValue left, DPValue right, char op) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        if (op == '+') {
            max = left.max + right.max;
            min = left.min + right.min;
        } else if (op == '-') {
            max = left.max - right.min;
            min = left.min - right.max;
        } else {
            int[] candidates = {
                    left.max * right.max,
                    left.max * right.min,
                    left.min * right.max,
                    left.min * right.min
            };
            for (int result : candidates) {
                max = Math.max(max, result);
                min = Math.min(min, result);
            }
        }
        return new int[] { max, min };
    }
}

/*
19
1*2+3*4*5-6*7*8*9*9

426384


19
1-9-1-9-1-9-1-9-1-9

32
*/