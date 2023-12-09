import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    private static final int MOD = 1000000007;

    public int solution(int n, int[] money) {
        Arrays.sort(money);

        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int coin : money) {
            for (int amount = coin; amount <= n; amount++) {
                dp[amount] = (dp[amount] + dp[amount - coin]) % MOD;
            }
        }

        return dp[n];
    }

    @Test
    void 정답() {
        int[] money = { 1, 2, 5 };
        Assertions.assertEquals(4, solution(5, money));
    }
}
