import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    private long max;
    private List<Long> operandList;
    private List<String> operatorList;

    public long solution(String expression) {
        max = 0;
        permutation(expression, 0, 3, new String[] { "+", "-", "*" }, new boolean[3], "");
        return max;
    }

    private void permutation(String expression, int startDepth, int targetDepth, String[] arr, boolean[] check,
            String result) {
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
            calculateOperators(nowOperator);
        }

        max = Math.max(max, Math.abs(operandList.get(0)));
    }

    private void calculateOperators(String nowOperator) {
        int size = operatorList.size();
        for (int i = 0; i < size; i++) {
            String op = operatorList.get(i);
            if (op.equals(nowOperator)) {
                long operand1 = operandList.remove(i);
                long operand2 = operandList.remove(i);
                long result = 0;
                switch (nowOperator) {
                    case "+":
                        result = operand1 + operand2;
                        break;
                    case "-":
                        result = operand1 - operand2;
                        break;
                    case "*":
                        result = operand1 * operand2;
                        break;
                }
                operandList.add(i, result);
                operatorList.remove(i);
                i--;
                size--;
            }
        }
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

    @Test
    void 정답() {
        Assertions.assertEquals(60420, solution("100-200*300-500+20"));
        Assertions.assertEquals(300, solution("50*6-3*2"));
    }
}
