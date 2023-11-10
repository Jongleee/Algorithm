import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int m, int n, int[][] cityMap) {
        int[][][] dp = new int[m + 1][n + 1][2];
        final int mod = 20170805;
        dp[0][0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                switch (cityMap[i][j]) {
                    case 0:
                        dp[i + 1][j][0] += (dp[i][j][0] + dp[i][j][1]) % mod;
                        dp[i][j + 1][1] += (dp[i][j][0] + dp[i][j][1]) % mod;
                        break;
                    case 2:
                        dp[i + 1][j][0] += dp[i][j][0] % mod;
                        dp[i][j + 1][1] += dp[i][j][1] % mod;
                        break;
                    default:
                        break;
                }
            }
        }
        return (dp[m - 1][n - 1][0] + dp[m - 1][n - 1][1]) % mod;
    }

    @Test
    void 정답() {
        int[][] c1 = { { 0, 2, 0, 0, 0, 2 }, { 0, 0, 2, 0, 1, 0 }, { 1, 0, 0, 2, 2, 0 } };
        Assertions.assertEquals(2, solution(3, 6, c1));
        int[][] c2 = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
        Assertions.assertEquals(6, solution(3, 3, c2));
    }
}
