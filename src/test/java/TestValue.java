import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        int distance = Math.abs(x - r) + Math.abs(c - y);
        k -= distance;

        if (k < 0 || k % 2 != 0 || m < y) {
            return "impossible";
        }

        return calculateMovements(n, m, x, r, y, c, k);
    }

    private String calculateMovements(int n, int m, int x, int r, int y, int c, int k) {
        int[] movements = new int[4];
        StringBuilder moves = new StringBuilder();
        if (x - r > 0) {
            movements[1] = x - r;
        } else {
            movements[0] = r - x;
        }

        if (y - c > 0) {
            movements[2] = y - c;
        } else {
            movements[3] = c - y;
        }
        int down = movements[0];
        moves.append("d".repeat(down));
        down = Math.min(k / 2, n - (x + down));
        moves.append("d".repeat(down));
        int up = movements[1] + down;
        k -= 2 * down;

        int left = movements[2];
        moves.append("l".repeat(left));
        left = Math.min(k / 2, y - left - 1);
        moves.append("l".repeat(left));
        int right = movements[3] + left;
        k -= 2 * left;

        moves.append("rl".repeat(k / 2));
        moves.append("r".repeat(right));
        moves.append("u".repeat(up));

        return moves.toString();
    }

    @Test
    void 정답() {
        Assertions.assertEquals("dllrl", solution(3, 4, 2, 3, 3, 1, 5));
        Assertions.assertEquals("dr", solution(2, 2, 1, 1, 2, 2, 2));
    }
}
