package com.example.algorithm.java.implement;

import java.util.LinkedList;
import java.util.List;

public class MaximizeEquation {
    private long max = 0;
    private List<Long> operandList;
    private List<String> operatorList;
    public long solution(String expression) {
        permutation(expression, 0, 3, new String[] { "+", "-", "*" }, new boolean[3], "");
        return max;
    }

    private void permutation(String expression, int startDepth, int targetDepth, String[] arr, boolean[] check, String result) {
        if (startDepth == targetDepth) {
            calculate(expression, result);
        } else {
            for (int i = 0; i < arr.length; i++) {
                if (!check[i]) {
                    check[i] = true;
                    permutation(expression, startDepth + 1, targetDepth, arr, check, result + arr[i]);
                    check[i] = false;
                }
            }
        }
    }

    private void calculate(String expression, String operators) {
        operandList = new LinkedList<>();
        operatorList = new LinkedList<>();

        StringBuilder nextValue = init(expression);

        operandList.add(Long.parseLong(nextValue.toString()));

        for (int i = 0; i < 3; i++) {
            String nowOperator = String.valueOf(operators.charAt(i));

            while (!operatorList.isEmpty()) {
                int index = operatorList.indexOf(nowOperator);

                if (index == -1) {
                    break;
                } else {
                    switch (nowOperator) {
                        case "+":
                            operandList.add(index, operandList.get(index) + operandList.get(index + 1));
                            break;
                        case "-":
                            operandList.add(index, operandList.get(index) - operandList.get(index + 1));
                            break;
                        case "*":
                            operandList.add(index, operandList.get(index) * operandList.get(index + 1));
                            break;
                        default:
                            break;
                    }

                    operandList.remove(index + 1);
                    operandList.remove(index + 1);

                    operatorList.remove(index);
                }
            }
        }

        max = Math.max(max, Math.abs(operandList.get(0)));
    }

    private StringBuilder init(String expression) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '+' || ch == '-' || ch == '*') {
                operandList.add(Long.parseLong(result.toString()));
                result = new StringBuilder();
                operatorList.add(String.valueOf(ch));
            } else {
                result.append(ch);
            }
        }
        return result;
    }
}
