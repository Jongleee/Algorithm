import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSolution {
	public String solution(String s) {
		String[] words = s.split(" ");

		StringBuilder answerBuilder = new StringBuilder();

		for (String word : words) {
			if (word.isEmpty()) {
				answerBuilder.append(" ");
			} else {
				String capitalized = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
				answerBuilder.append(capitalized).append(" ");
			}
		}

		String answer = answerBuilder.toString();
		if (s.substring(s.length() - 1, s.length()).equals(" ") )
			return answer;
		return answer.trim();
	}

	@Test
	public void 정답() {
		Assertions.assertEquals("3people Unfollowed Me", solution("3people unFollowed me"));
		Assertions.assertEquals("3people Unfollowed Me ", solution("3people unFollowed me "));
		Assertions.assertEquals("For The Last Week", solution("for the last week"));
	}
}