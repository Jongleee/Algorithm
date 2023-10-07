import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArray {
    private int[] answer;

    public int[] solution(int[][] arr) {
        answer = new int[2];
        countQuads(arr, 0, 0, arr.length);
        return answer;
    }

    private void countQuads(int[][] arr, int x, int y, int size) {
        int val = arr[x][y];

        if (isUniform(arr, x, y, size, val)) {
            answer[val]++;
            return;
        }

        int halfSize = size / 2;
        countQuads(arr, x, y, halfSize);
        countQuads(arr, x, y + halfSize, halfSize);
        countQuads(arr, x + halfSize, y, halfSize);
        countQuads(arr, x + halfSize, y + halfSize, halfSize);
    }

    private boolean isUniform(int[][] arr, int x, int y, int size, int val) {
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (arr[i][j] != val) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void 정답() {
        int[][] a1 = { { 1, 1, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 1 }, { 1, 1, 1, 1 } };
        Assertions.assertArrayEquals(new int[] { 4, 9 }, solution(a1));
        int[][] a2 = { { 1, 1, 1, 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 1, 1, 1, 1 },
                { 0, 1, 0, 0, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0, 0, 1, 1 }, { 0, 0, 0, 0, 0, 0, 0, 1 },
                { 0, 0, 0, 0, 1, 0, 0, 1 }, { 0, 0, 0, 0, 1, 1, 1, 1 } };
        Assertions.assertArrayEquals(new int[] { 10, 15 }, solution(a2));
    }
}
