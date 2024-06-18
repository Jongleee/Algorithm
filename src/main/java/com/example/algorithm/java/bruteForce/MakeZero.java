package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MakeZero {
    static int n;
    static StringBuilder sb;
    static final char[] OPERATORS = {' ', '+', '-'};
    static char[] chosenOperators;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        for (int tc = 0; tc < t; tc++) {
            n = Integer.parseInt(br.readLine());
            chosenOperators = new char[n - 1];
            generateExpressions(0);
            sb.append("\n");
        }
        System.out.print(sb);
    }

    private static void generateExpressions(int depth) {
        if (depth == n - 1) {
            if (evaluateExpression()) {
                buildExpression();
            }
            return;
        }

        for (char operator : OPERATORS) {
            chosenOperators[depth] = operator;
            generateExpressions(depth + 1);
        }
    }

    private static boolean evaluateExpression() {
        int result = 0;
        int currentNumber = 1;
        int sign = 1;

        for (int i = 0; i < n - 1; i++) {
            char operator = chosenOperators[i];
            if (operator == ' ') {
                currentNumber = currentNumber * 10 + (i + 2);
            } else {
                result += sign * currentNumber;
                currentNumber = i + 2;
                sign = (operator == '+') ? 1 : -1;
            }
        }
        result += sign * currentNumber;

        return result == 0;
    }

    private static void buildExpression() {
        StringBuilder expression = new StringBuilder();
        for (int i = 0; i < n - 1; i++) {
            expression.append(i + 1).append(chosenOperators[i]);
        }
        expression.append(n);
        sb.append(expression).append("\n");
    }
}

/*
2
3
7

1+2-3

1+2-3+4-5-6+7
1+2-3-4+5+6-7
1-2 3+4+5+6+7
1-2 3-4 5+6 7
1-2+3+4-5+6-7
1-2-3-4-5+6+7
 */