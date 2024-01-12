import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public long solution(int n, int m, int x, int y, int[][] queries) {
        long[] coordinates = { x, y, x, y };
        for (int i = queries.length - 1; i >= 0; i--) {
            int direction = queries[i][0];
            int move = queries[i][1];
            updateCoordinates(coordinates, direction, move, n, m);
            if (coordinates[1] >= m || coordinates[3] < 0 || coordinates[0] >= n || coordinates[2] < 0)
                return 0;
        }
        return (coordinates[3] - coordinates[1] + 1) * (coordinates[2] - coordinates[0] + 1);
    }

    private void updateCoordinates(long[] coordinates, int direction, int move, int n, int m) {
        switch (direction) {
            case 0:
                coordinates[1] = findMin(coordinates[1], move);
                coordinates[3] = Math.min((long) m - 1, coordinates[3] + move);
                break;
            case 1:
                coordinates[3] = findMax(m, coordinates[3], move);
                coordinates[1] = Math.max(0, coordinates[1] - move);
                break;
            case 2:
                coordinates[0] = findMin(coordinates[0], move);
                coordinates[2] = Math.min((long) n - 1, coordinates[2] + move);
                break;
            case 3:
                coordinates[2] = findMax(n, coordinates[2], move);
                coordinates[0] = Math.max(0, coordinates[0] - move);
                break;
            default:
                break;
        }
    }

    private long findMin(long min, int move) {
        if (min > 0)
            min += move;
        return min;
    }

    private long findMax(int m, long max, int move) {
        if (max < m - 1)
            max -= move;
        return max;
    }

    @Test
    void 정답() {
        int[][] queries1 = { { 2, 1 }, { 0, 1 }, { 1, 1 }, { 0, 1 }, { 2, 1 } };
        int[][] queries2 = { { 3, 1 }, { 2, 2 }, { 1, 1 }, { 2, 3 }, { 0, 1 }, { 2, 1 } };

        Assertions.assertEquals(4, solution(2, 2, 0, 0, queries1));
        Assertions.assertEquals(2, solution(2, 5, 0, 1, queries2));
    }
}
