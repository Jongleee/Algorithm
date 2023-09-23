import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArray {
    private int[] distances;
    private List<Integer>[] routes;
    private int limit = 1000000;

    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        initializeGraph(n, roads);
        distances = new int[n + 1];
        Arrays.fill(distances, limit);

        bfs(destination);

        int[] answer = new int[sources.length];
        for (int i = 0; i < sources.length; i++) {
            answer[i] = (distances[sources[i]] < limit) ? distances[sources[i]] : -1;
        }
        return answer;
    }

    private void initializeGraph(int n, int[][] roads) {
        routes = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            routes[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            int start = road[0];
            int end = road[1];
            routes[start].add(end);
            routes[end].add(start);
        }
    }

    private void bfs(int destination) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(destination);
        distances[destination] = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next : routes[current]) {
                if (distances[next] > distances[current] + 1) {
                    distances[next] = distances[current] + 1;
                    queue.add(next);
                }
            }
        }
    }

    @Test
    public void 정답() {
        Assertions.assertArrayEquals(new int[] { 2, -1, 0 },
                solution(3, new int[][] { { 1, 2 }, { 2, 3 } }, new int[] { 2, 3 }, 1));
        Assertions.assertArrayEquals(new int[] { 2, -1, 0 },
                solution(5, new int[][] { { 1, 2 }, { 1, 4 }, { 2, 4 }, { 2, 5 }, { 4, 5 } }, new int[] { 1, 3, 5 }, 5));
    }
}
