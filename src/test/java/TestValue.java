import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValue {
    private static int countSolutions = 0;

    public static int solution(int n) {
        int[] board = new int[n];
        solveNQueens(board, 0);
        return countSolutions;
    }

    private static void solveNQueens(int[] board, int row) {
        int n = board.length;
        if (row == n) {
            countSolutions++;
            return;
        }

        for (int col = 0; col < n; col++) {
            board[row] = col;
            if (isSafe(board, row, col)) {
                solveNQueens(board, row + 1);
            }
        }
    }

    private static boolean isSafe(int[] board, int row, int col) {
        for (int prevRow = 0; prevRow < row; prevRow++) {
            int prevCol = board[prevRow];
            if (prevCol == col || Math.abs(prevRow - row) == Math.abs(prevCol - col)) {
                return false;
            }
        }
        return true;
    }


    @Test
    public void 정답() {
        Assertions.assertEquals(2, solution(4));
    }
}
