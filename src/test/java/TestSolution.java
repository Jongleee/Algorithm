import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSolution {

    private static final int INF = 1000000;

    public int solution(int n, int m, int[][] edgeList, int k, int[] gpsLog) {
        int[][] road = new int[n + 1][n + 1];

        for (int[] edge : edgeList) {
            int s = edge[0], e = edge[1];
            road[s][e] = 1;
            road[e][s] = 1;
        }

        int[][] dp = new int[k][n + 1];
        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][gpsLog[0]] = 0;

        for (int i = 1; i < k; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j]);
                for (int node = 1; node <= n; node++) {
                    if (road[j][node] == 1) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][node]);
                    }
                }
                if (j != gpsLog[i]) {
                    dp[i][j]++;
                }
            }
        }

        return dp[k - 1][gpsLog[k - 1]] < INF ? dp[k - 1][gpsLog[k - 1]] : -1;
    }

    @Test
    public void 정답() {
        Assertions.assertEquals(1, solution(7, 10, new int[][] { { 1, 2 }, { 1, 3 }, { 2, 3 }, { 2, 4 }, { 3, 4 },
                { 3, 5 }, { 4, 6 }, { 5, 6 }, { 5, 7 }, { 6, 7 } }, 6, new int[] { 1, 2, 3, 3, 6, 7 }));

        Assertions.assertEquals(0, solution(
                7, 10,
                new int[][] { { 1, 2 }, { 1, 3 }, { 2, 3 }, { 2, 4 }, { 3, 4 }, { 3, 5 }, { 4, 6 }, { 5, 6 }, { 5, 7 },
                        { 6, 7 } },
                6, new int[] { 1, 2, 4, 6, 5, 7 }));
    }
}
