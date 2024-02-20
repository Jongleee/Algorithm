import java.util.PriorityQueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public long solution(int n, int[] works) {
        long answer = 0;

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

        for (int work : works) {
            maxHeap.offer(work);
        }

        while (n > 0) {
            int maxWork = maxHeap.poll();
            if (maxWork == 0)
                break;
            maxHeap.offer(maxWork - 1);
            n--;
        }

        for (int work : maxHeap) {
            answer += (long) work * work;
        }

        return answer;
    }

    @Test
    void 정답() {
        int[] works1 = { 4, 3, 3 };
        int[] works2 = { 2, 1, 2 };
        int[] works3 = { 1, 1 };

        Assertions.assertEquals(12, solution(4, works1));
        Assertions.assertEquals(6, solution(1, works2));
        Assertions.assertEquals(0, solution(3, works3));
    }
}
