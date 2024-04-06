import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    List<Map<Long, Integer>> levels;
    long[] sum;
    static final int MOD = 1000000007;

    public int[] solution(int[] a, int[] s) {
        int[] answer = new int[s.length];
        int end = 0;

        for (int index = 0; index < s.length; index++) {
            int groupSize = s[index];
            int start = end;
            end = start + groupSize;
            sum = new long[groupSize + 1];
            sum[0] = 1;

            levels = new ArrayList<>();
            for (int i = 0; i <= groupSize; i++) {
                levels.add(new HashMap<>());
            }

            levels.get(0).put(-1L, -1);

            for (int i = 1; i <= groupSize; i++) {
                sum[i] = connect(a[start + i - 1], i, i - 1);
            }

            answer[index] = (int) ((sum[groupSize]) % MOD);
        }

        return answer;
    }

    long connect(long cellSize, int currentLevel, int parentLevel) {
        Map<Long, Integer> level = levels.get(currentLevel);
        level.computeIfAbsent(cellSize, k -> parentLevel);

        long result = sum[parentLevel];

        if (levels.get(parentLevel).containsKey(cellSize)) {
            result += connect(cellSize * 2, currentLevel, levels.get(parentLevel).get(cellSize));
            result %= MOD;
        }

        return result;
    }

    @Test
    void 정답() {
        int[][] a = { { 1, 1, 1, 1, 1, 1, 2, 5, 8, 2, 1, 1, 4, 8, 8, 8, 12, 6, 6 } };
        int[][] s = { { 4, 3, 1, 5, 6 } };

        int[][] result = { { 6, 3, 1, 5, 9 } };
        for (int i = 0; i < result.length; i++) {
            Assertions.assertArrayEquals(result[i], solution(a[i], s[i]));
        }
    }
}
