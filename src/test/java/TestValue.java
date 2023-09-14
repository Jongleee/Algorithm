import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValue {
    boolean solution(String s) {
        return isCorrect(s);
    }

    private boolean isCorrect(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (!stack.isEmpty() && stack.peek() == '(') {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    @Test
    public void 정답() {
        Assertions.assertEquals(true, solution("()()"));
        Assertions.assertEquals(true, solution("(())()"));
        Assertions.assertEquals(false, solution(")()("));
        Assertions.assertEquals(false, solution("(()("));
    }
}
