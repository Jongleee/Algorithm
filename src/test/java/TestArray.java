import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public int[] solution(int e, int[] starts) {
        int[] numbers = new int[e + 1];
        int[] answer = new int[starts.length];

        for (int i = 1; i <= e; i++) {
            for (int j = i; j <= e; j += i) {
                numbers[j] += 1;
            }
        }

        int[] maxNumber = new int[e + 1];
        Arrays.fill(maxNumber, e);

        for (int i = e - 1; i > 0; i--) {
            maxNumber[i] = (numbers[i] >= numbers[maxNumber[i + 1]]) ? i : maxNumber[i + 1];
        }

        for (int i = 0; i < starts.length; i++) {
            answer[i] = maxNumber[starts[i]];
        }

        return answer;
    }

    @Test
    void 정답() {
        int[] start = { 1, 3, 7 };
        int[] result = { 6, 6, 8 };
        Assertions.assertArrayEquals(result, solution(8, start));
    }
}
