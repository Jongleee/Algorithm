import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSolution {
	public int[] solution(int brown, int yellow) {
		int[] answer = new int[2];

		int area = brown + yellow;

		for (int row = 1; row <= area; row++) {
			int col = area / row;

			if (row > col) {
				continue;
			}

			if ((row - 2) * (col - 2) == yellow) {
				answer[0] = col;
				answer[1] = row;
				return answer;
			}
		}

		return answer;
	}

	@Test
	public void 정답() {
		Assertions.assertEquals("[4, 3]", Arrays.toString(solution(10,2)));
		Assertions.assertEquals("[3, 3]", Arrays.toString(solution(8,1)));
		Assertions.assertEquals("[8, 6]", Arrays.toString(solution(24,24)));
	}
}