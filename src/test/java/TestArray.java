import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public int[] solution(int n, int s) {
        if (n > s) {
            return new int[] { -1 };
        }

        int[] answer = new int[n];
        int quotient = s / n;
        int remainder = s % n;
        if (remainder == 0) {
            for (int i = 0; i < n; i++) {
                answer[i] = quotient;
            }
            return answer;
        }
        for (int i = 0; i < n; i++) {
            answer[i] = quotient + (i < n - remainder ? 0 : 1);
        }

        return answer;
    }

    @Test
    void 정답() {
        Assertions.assertArrayEquals(new int[] { 4, 5 }, solution(2, 9));
        Assertions.assertArrayEquals(new int[] { -1 }, solution(2, 1));
        Assertions.assertArrayEquals(new int[] { 4, 4 }, solution(2, 8));
    }
}
