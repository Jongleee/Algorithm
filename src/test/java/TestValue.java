import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[][] board) {
        int answer = 0;
        int n = board.length;

        int removeCount;
        do {
            removeCount = 0;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - 1; j++) {
                    if (canRemoveBlock(i, j, 2, 3, n, board)) {
                        removeBlock(i, j, 2, 3, board);
                        removeCount++;
                    }
                    if (canRemoveBlock(i, j, 3, 2, n, board)) {
                        removeBlock(i, j, 3, 2, board);
                        removeCount++;
                    }
                }
            }
            answer += removeCount;
        } while (removeCount != 0);

        return answer;
    }

    private boolean canRemoveBlock(int row, int col, int height, int width, int n, int[][] board) {
        if (row + height > n || col + width > n) {
            return false;
        }

        int value = -1;
        int emptyCount = 0;

        for (int i = row; i < row + height; i++) {
            for (int j = col; j < col + width; j++) {
                if (board[i][j] == 0) {
                    if (!isEmptyAbove(i, j, board) || ++emptyCount > 2) {
                        return false;
                    }
                } else if (value == -1) {
                    value = board[i][j];
                } else if (value != board[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isEmptyAbove(int row, int col, int[][] board) {
        for (int i = 0; i < row; i++) {
            if (board[i][col] != 0) {
                return false;
            }
        }

        return true;
    }

    private void removeBlock(int row, int col, int height, int width, int[][] board) {
        for (int i = row; i < row + height; i++) {
            for (int j = col; j < col + width; j++) {
                board[i][j] = 0;
            }
        }
    }

    @Test
    void 정답() {
        int[][] board = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 4, 0, 0, 0 }, { 0, 0, 0, 0, 0, 4, 4, 0, 0, 0 },
                { 0, 0, 0, 0, 3, 0, 4, 0, 0, 0 }, { 0, 0, 0, 2, 3, 0, 0, 0, 5, 5 },
                { 1, 2, 2, 2, 3, 3, 0, 0, 0, 5 }, { 1, 1, 1, 0, 0, 0, 0, 0, 0, 5 } };
        Assertions.assertEquals(2, solution(board));
    }
}
