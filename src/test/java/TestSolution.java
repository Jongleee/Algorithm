import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSolution {
	public int[] solution(int n, String[] words) {
		HashSet<String> set = new HashSet<>();
		set.add(words[0]);
		for (int i = 1; i < words.length; i++) {
			String previousWord = words[i - 1];
            String currentWord = words[i];

            char last = previousWord.charAt(previousWord.length() - 1);
            char first = currentWord.charAt(0);

            if (set.contains(currentWord) || last != first) {
                int setNum = set.size();
				return new int[] { setNum % n +1, setNum / n +1};
            }
			set.add(currentWord);
		}
		return new int[] {0,0};
	}

	@Test
	public void 정답() {
		Assertions.assertEquals("[3, 3]",
				Arrays.toString(solution(3,
						new String[] { "tank", "kick", "know", "wheel", "land", "dream", "mother", "robot", "tank" })));
		Assertions.assertEquals("[0, 0]",
				Arrays.toString(solution(5,
						new String[] { "hello", "observe", "effect", "take", "either", "recognize", "encourage",
								"ensure", "establish", "hang", "gather", "refer", "reference", "estimate",
								"executive" })));
		Assertions.assertEquals("[1, 3]",
				Arrays.toString(solution(2, new String[] { "hello", "one", "even", "never", "now", "world", "draw" })));
	}
}