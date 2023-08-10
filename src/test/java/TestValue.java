import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValue {
    public int solution(int k, int m, int[] score) {
        Arrays.sort(score);
        int answer = 0;

        for (int i = score.length - 1; i >= 0; i--) {
            int multiplier = (score.length - i) % m;
            if (multiplier == 0) {
                answer += score[i] * m;
            }
        }

        return answer;
    }

    @Test
    public void 정답() {
        Assertions.assertEquals(8, solution(3, 4, new int[] { 1, 2, 3, 1, 2, 3, 1 }));
        Assertions.assertEquals(33, solution(4, 3, new int[] { 4, 1, 2, 2, 4, 4, 4, 4, 1, 2, 4, 2 }));
    }
}
