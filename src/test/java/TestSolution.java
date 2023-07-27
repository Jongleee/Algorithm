import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSolution {
    public int solution(int[][] board, int[] moves) {
        int answer = 0;

        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        for (int move : moves) {
            int columnIndex = move - 1;

            for (int row = 0; row < board.length; row++) {
                int currentValue = board[row][columnIndex];

                if (currentValue != 0) {
                    if (stack.peek() == currentValue) {
                        stack.pop();
                        answer += 2;
                    } else {
                        stack.push(currentValue);
                    }

                    board[row][columnIndex] = 0;
                    break;
                }
            }
        }

        return answer;
    }

    @Test
    public void 정답() {
        Assertions.assertEquals(4, solution(new int[][] { { 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 3 }, { 0, 2, 5, 0, 1 },
                { 4, 2, 4, 4, 2 }, { 3, 5, 1, 3, 1 } }, new int[] { 1, 5, 3, 5, 1, 2, 1, 4 }));
        Assertions.assertEquals(8, solution(new int[][] { { 3, 3, 3, 3, 3 }, { 3, 3, 3, 3, 3 }, { 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3 }, { 3, 3, 3, 3, 3 } }, new int[] { 1, 5, 3, 5, 1, 2, 1, 4 }));
        Assertions.assertEquals(0, solution(new int[][] { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } }, new int[] { 1, 5, 3, 5, 1, 2, 1, 4 }));
    }
}
