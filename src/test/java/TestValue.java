import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(String[][] board, int h, int w) {
        int count = 0;
        String color = board[h][w];

        int[][] directions = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };

        for (int[] dir : directions) {
            int tempH = h + dir[0];
            int tempW = w + dir[1];

            if (isValidMove(tempH, tempW, board)) {
                if (color.equals(board[tempH][tempW])) {
                    count += 1;
                }
            }
        }
        return count;
    }

    private boolean isValidMove(int h, int w, String[][] board) {
        return h >= 0 && h < board.length && w >= 0 && w < board[0].length;
    }

    @Test
    void 정답() {
        String[][][] board = {
                { { "blue", "red", "orange", "red" }, { "red", "red", "blue", "orange" },
                        { "blue", "orange", "red", "red" }, { "orange", "orange", "red", "blue" } },
                { { "yellow", "green", "blue" }, { "blue", "green", "yellow" }, { "yellow", "blue", "blue" } } };
        int[] h = { 1, 0 };
        int[] w = { 1, 1 };
        int[] result = { 2, 1 };

        for (int i = 0; i < result.length; i++) {
            Assertions.assertEquals(result[i], solution(board[i], h[i], w[i]));
        }
    }
}
