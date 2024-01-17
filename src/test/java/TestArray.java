import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    private final int max = 32421;
    private int[][] dp;
    private ArrayList<Integer> score;
    private ArrayList<Integer> multiScore;

    public int[] solution(int target) {
        dp = new int[target + 1][2];
        initialize(target);
        return solve(target);
    }

    private void initialize(int t) {
        dp = new int[t + 1][2];

        for (int i = 1; i <= t; i++)
            dp[i][0] = max;

        score = new ArrayList<>();
        score.add(50);
        for (int i = 1; i < 21; i++)
            score.add(i);

        multiScore = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            for (int j = 2; j < 4; j++) {
                if (i * j <= 20)
                    continue;
                multiScore.add(i * j);
            }
        }
    }

    private void setMin(int[] a, int[] b) {
        if (a[0] > b[0] || (a[0] == b[0] && a[1] < b[1])) {
            a[0] = b[0];
            a[1] = b[1];
        }
    }

    private int[] solve(int remain) {
        if (remain == 0)
            return new int[] { 0, 0 };

        if (remain < 0)
            return new int[] { max, max };

        if (dp[remain][0] != max)
            return dp[remain];

        int[] result = new int[] { max, max };

        for (int s : score) {
            int[] temp = solve(remain - s);
            setMin(result, new int[] { temp[0] + 1, temp[1] + 1 });
        }

        for (int s : multiScore) {
            int[] temp = solve(remain - s);
            setMin(result, new int[] { temp[0] + 1, temp[1] });
        }

        dp[remain][0] = result[0];
        dp[remain][1] = result[1];
        return dp[remain];
    }

    @Test
    void 정답() {
        Assertions.assertArrayEquals(new int[] { 1, 0 }, solution(24));
        Assertions.assertArrayEquals(new int[] { 2, 2 }, solution(58));
    }
}
