import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValue {
    public static int solution(int[][] clockHands) {
        int n = clockHands.length;
        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < Math.pow(4, n); i++) {
            int[][] copyArr = copy2DArray(clockHands);
            int count = 0;
            int a = i;

            for (int j = 0; j < n; j++) {
                int cnt = a % 4;
                a /= 4;
                rotateClockHands(copyArr, 0, j, cnt);
                count += cnt;
            }

            for (int row = 1; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    int cnt = (4 - copyArr[row - 1][col]) % 4;
                    rotateClockHands(copyArr, row, col, cnt);
                    count += cnt;
                }
            }

            if (Arrays.equals(copyArr[n - 1], new int[n])) {
                return count;
            }
        }
        return answer;
    }

    private static int[][] copy2DArray(int[][] original) {
        int n = original.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    private static void rotateClockHands(int[][] clockHands, int row, int col, int cnt) {
        int n = clockHands.length;
        clockHands[row][col] = (clockHands[row][col] + cnt) % 4;
        if (row > 0)
            clockHands[row - 1][col] = (clockHands[row - 1][col] + cnt) % 4;
        if (col > 0)
            clockHands[row][col - 1] = (clockHands[row][col - 1] + cnt) % 4;
        if (row < n - 1)
            clockHands[row + 1][col] = (clockHands[row + 1][col] + cnt) % 4;
        if (col < n - 1)
            clockHands[row][col + 1] = (clockHands[row][col + 1] + cnt) % 4;
    }

    @Test
    public void 정답() {
        Assertions.assertEquals(3,
                solution(new int[][] { { 0, 3, 3, 0 }, { 3, 2, 2, 3 }, { 0, 3, 2, 0 }, { 0, 3, 3, 3 } }));
    }
}
