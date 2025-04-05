package com.example.algorithm.java.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class PostfixNotation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String expression = br.readLine();
        System.out.println(convertToPostfix(expression));
    }

    private static String convertToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (char ch : expression.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                result.append(ch);
            } else if (ch == '(') {
                operatorStack.push(ch);
            } else if (ch == ')') {
                while (!operatorStack.isEmpty()) {
                    char top = operatorStack.pop();
                    if (top == '(')
                        break;
                    result.append(top);
                }
            } else {
                handleOperatorPrecedence(result, operatorStack, ch);
                operatorStack.push(ch);
            }
        }

        while (!operatorStack.isEmpty()) {
            result.append(operatorStack.pop());
        }

        return result.toString();
    }

    private static void handleOperatorPrecedence(StringBuilder result, Stack<Character> operatorStack, char ch) {
        while (!operatorStack.isEmpty() &&
                getPriority(operatorStack.peek()) >= getPriority(ch)) {
            result.append(operatorStack.pop());
        }
    }

    private static int getPriority(char operator) {
        if (operator == '*' || operator == '/') {
            return 2;
        } else if (operator == '+' || operator == '-') {
            return 1;
        }
        return 0;
    }
}

/*
A+B

AB+


A+B*C-D/E

ABC*+DE/-


A+B*C

ABC*+
*/