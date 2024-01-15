import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    private int count;

    public int solution(int n) {
        count = 0;
        dfs(0, 0, n);
        return count;
    }

    public void dfs(int left, int right, int n) {
        if (left > n || right > n || left < right) {
            return;
        }

        if (left == n && right == n) {
            count++;
            return;
        }

        dfs(left + 1, right, n);
        dfs(left, right + 1, n);
    }

    @Test
    void 정답() {
        Assertions.assertEquals(2, solution(2));
        Assertions.assertEquals(5, solution(3));
    }
}
