import java.util.PriorityQueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (int s : scoville) {
            queue.add(s);
        }

        while (queue.peek() < K) {
            if (queue.size() < 2)
                return -1;

            int first = queue.poll();
            int second = queue.poll();
            int mixedScoville = first + (second * 2);

            queue.add(mixedScoville);
            answer++;
        }

        return answer;
    }

    @Test
    void 정답() {
        int[] scoville = { 1, 2, 3, 9, 10, 12 };

        Assertions.assertEquals(2, solution(scoville,7));
    }
}
