import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public int[] solution(int n, long left, long right) {
        int length = (int) (right - left) + 1;
        int[] answer = new int[length];

        for (int i = 0; i < length; i++) {
            long offset = i + left;
            answer[i] = calculateValue(n, offset);
        }

        return answer;
    }

    private int calculateValue(int n, long offset) {
        int row = (int) (offset / n) + 1;
        int col = (int) (offset % n) + 1;
        return Math.max(row, col);
    }

    @Test
    void 정답() {
        int[] n = { 3, 4 };
        int[] left = { 2, 7 };
        int[] right = { 5, 14 };

        int[][] result = { { 3, 2, 2, 3 }, { 4, 3, 3, 3, 4, 4, 4, 4 } };

        for (int i = 0; i < result.length; i++) {
            Assertions.assertArrayEquals(result[i], solution(n[i], left[i], right[i]));
        }
    }
}
