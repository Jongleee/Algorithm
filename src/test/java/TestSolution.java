import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSolution {
	public int solution(int n, int a, int b) {
		int answer = 0;
		while (a != b) {
			a = a / 2 + a % 2;
			b = b / 2 + b % 2;
			answer++;
		}
		return answer;
	}

	@Test
	public void 정답() {
		Assertions.assertEquals(3, solution(8, 4, 7));
	}
}