import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSolution {
	public int solution(String s) {
		Deque<Character> stack = new ArrayDeque<>();

		for (char ch : s.toCharArray()) {
			if (!stack.isEmpty() && stack.peek() == ch) {
				stack.pop();
			} else {
				stack.push(ch);
			}
		}

		return stack.isEmpty() ? 1 : 0;
	}

	@Test
	public void 정답() {
		Assertions.assertEquals(1, solution("baabaa"));
		Assertions.assertEquals(0, solution("cdcd"));
	}
}