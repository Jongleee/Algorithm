import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValue {
    public static int solution(int k, int[][] dungeons) {
        boolean[] visited = new boolean[dungeons.length];
        return dfs(0, k, dungeons, visited);
    }

    public static int dfs(int depth, int k, int[][] dungeons, boolean[] visited) {
        int maxDepth = depth;

        for (int i = 0; i < dungeons.length; i++) {
            if (!visited[i] && dungeons[i][0] <= k) {
                visited[i] = true;
                int subDepth = dfs(depth + 1, k - dungeons[i][1], dungeons, visited);
                maxDepth = Math.max(maxDepth, subDepth);
                visited[i] = false;
            }
        }

        return maxDepth;
    }

    @Test
    public void 정답() {
        Assertions.assertEquals(3, solution(80, new int[][] { { 80, 20 }, { 50, 40 }, { 30, 10 } }));
    }
}
