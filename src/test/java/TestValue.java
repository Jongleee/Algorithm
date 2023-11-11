import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(String s) {
        int answer = 0;
        for (int i = 0; i < s.length(); i++) {
            StringBuilder sb = new StringBuilder(s);
            String substring = s.substring(0, i);
            sb.delete(0, i);
            sb.append(substring);
            if (isCorrect(sb.toString())) {
                answer += 1;
            }
        }

        return answer;
    }

    private boolean isCorrect(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (stack.isEmpty()) {
                stack.push(c);
            } else {
                switch (c) {
                    case ']':
                        popCondition(stack, '[');
                        break;
                    case '}':
                        popCondition(stack, '{');
                        break;
                    case ')':
                        popCondition(stack, '(');
                        break;
                    default:
                        stack.push(c);
                }
            }
        }
        return stack.isEmpty();
    }

    private void popCondition(Stack<Character> stack, char openingBracket) {
        if (!stack.isEmpty() && stack.peek() == openingBracket) {
            stack.pop();
        } else {
            stack.push(openingBracket == '(' ? ')' : (openingBracket == '{' ? '}' : ']'));
        }
    }

    @Test
    void 정답() {
        Assertions.assertEquals(3, solution("[](){}"));
        Assertions.assertEquals(2, solution("}]()[{"));
        Assertions.assertEquals(0, solution("[)(]"));
        Assertions.assertEquals(0, solution("}}}"));
    }
}
