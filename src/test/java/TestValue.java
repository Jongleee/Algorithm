import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int n) {
        return dfs(n, 0);
    }

    private int dfs(int value, int cnt) {
        if (value < 1 || 2 * Math.log(value) / Math.log(3) < cnt) {
            return 0;
        }
        if (value == 3) {
            return (cnt == 2) ? 1 : 0;
        }

        int result = 0;
        if (value % 3 == 0 && cnt >= 2) {
            result += dfs(value / 3, cnt - 2);
        }
        result += dfs(value - 1, cnt + 1);
        return result;
    }

    @Test
    void 정답() {
        Assertions.assertEquals(1, solution(15));
        Assertions.assertEquals(0, solution(24));
        Assertions.assertEquals(1735, solution(2147483647));
    }
}
