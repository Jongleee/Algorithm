import java.util.ArrayDeque;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[] queue1, int[] queue2) {
        Queue<Integer> q1 = new ArrayDeque<>();
        Queue<Integer> q2 = new ArrayDeque<>();
        long sum1 = 0;
        long sum2 = 0;

        for (int tmp : queue1) {
            q1.add(tmp);
            sum1 += tmp;
        }

        for (int tmp : queue2) {
            q2.add(tmp);
            sum2 += tmp;
        }

        long totalSum = sum1 + sum2;
        if (totalSum % 2 != 0) {
            return -1;
        }

        long targetSum = totalSum / 2;
        int cnt1 = 0;
        int cnt2 = 0;
        int limit = queue1.length * 2;

        while (cnt1 <= limit && cnt2 <= limit) {
            if (sum1 == targetSum) {
                return cnt1 + cnt2;
            }

            if (sum1 > targetSum) {
                int temp = q1.poll();
                sum1 -= temp;
                sum2 += temp;
                q2.add(temp);
                cnt1++;
            } else {
                int temp = q2.poll();
                sum2 -= temp;
                sum1 += temp;
                q1.add(temp);
                cnt2++;
            }
        }

        return -1;
    }

    @Test
    void 정답() {
        int[] qf1 = { 1, 1 };
        int[] qs1 = { 1, 5 };
        Assertions.assertEquals(-1, solution(qf1, qs1));
        int[] qf2 = { 3, 2, 7, 2 };
        int[] qs2 = { 4, 6, 5, 1 };
        Assertions.assertEquals(2, solution(qf2, qs2));
    }
}
