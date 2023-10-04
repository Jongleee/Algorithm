import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArray {
    public int[][] solution(int n) {
        List<int[]> moveList = generateMoves(n, 1, 3, 2);
        int[][] answer = new int[moveList.size()][];

        for (int i = 0; i < moveList.size(); i++) {
            answer[i] = moveList.get(i);
        }

        return answer;
    }

    private List<int[]> generateMoves(int n, int start, int to, int mid) {
        List<int[]> moveList = new ArrayList<>();

        if (n == 1) {
            moveList.add(new int[] { start, to });
        } else {
            moveList.addAll(generateMoves(n - 1, start, mid, to));
            moveList.add(new int[] { start, to });
            moveList.addAll(generateMoves(n - 1, mid, to, start));
        }

        return moveList;
    }

    @Test
    public void 정답() {
        Assertions.assertArrayEquals(new int[][] { { 1, 2 }, { 1, 3 }, { 2, 3 } }, solution(2));
        Assertions.assertArrayEquals(
                new int[][] { { 1, 3 }, { 1, 2 }, { 3, 2 }, { 1, 3 }, { 2, 1 }, { 2, 3 }, { 1, 3 } }, solution(3));
    }
}
