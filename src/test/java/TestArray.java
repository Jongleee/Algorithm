import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    final int MAX = 10_000_000;

    public int[] solution(long begin, long end) {
        int[] answer = new int[(int) (end - begin + 1)];

        for (long i = begin; i <= end; i++) {
            answer[(int) (i - begin)] = findValue(i);
        }

        return answer;
    }

    private int findValue(long num) {
        if (num == 1) {
            return 0;
        }

        List<Integer> l = new ArrayList<>();

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                l.add(i);

                if (num / i <= MAX) {
                    return (int) (num / i);
                }
            }
        }

        if (!l.isEmpty()) {
            return l.get(l.size() - 1);
        }
        return 1;
    }

    @Test
    void 정답() {
        int[] begin = { 1 };
        int[] end = { 10 };
        int[][] result = { { 0, 1, 1, 2, 1, 3, 1, 4, 3, 5 } };

        for (int i = 0; i < result.length; i++) {
            Assertions.assertArrayEquals(result[i], solution(begin[i], end[i]));
        }
    }
}
