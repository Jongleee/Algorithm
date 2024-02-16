import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int k, int[] tangerine) {
        Map<Integer, Integer> sizeMap = new HashMap<>();
        for (int size : tangerine) {
            sizeMap.put(size, sizeMap.getOrDefault(size, 0) + 1);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> sizeMap.get(b) - sizeMap.get(a));
        pq.addAll(sizeMap.keySet());

        int answer = 0;
        while (!pq.isEmpty() && k > 0) {
            int size = pq.poll();
            int count = Math.min(k, sizeMap.get(size));
            answer++;
            k -= count;
        }

        return answer;
    }

    @Test
    void 정답() {
        int[] tangerine1 = { 1, 3, 2, 5, 4, 5, 2, 3 };
        int[] tangerine2 = { 1, 3, 2, 5, 4, 5, 2, 3 };
        int[] tangerine3 = { 1, 1, 1, 1, 2, 2, 2, 3 };

        Assertions.assertEquals(3, solution(6, tangerine1));
        Assertions.assertEquals(2, solution(4, tangerine2));
        Assertions.assertEquals(1, solution(2, tangerine3));
    }
}
