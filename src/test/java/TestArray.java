import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public int[] solution(int[] progresses, int[] speeds) {
        Queue<Integer> daysToComplete = new LinkedList<>();

        for (int i = 0; i < speeds.length; i++) {
            int remainingDays = (100 - progresses[i]) / speeds[i];
            if ((100 - progresses[i]) % speeds[i] != 0) {
                remainingDays++;
            }
            daysToComplete.offer(remainingDays);
        }

        List<Integer> resultList = new ArrayList<>();
        int currentTask = daysToComplete.poll();
        int count = 1;

        while (!daysToComplete.isEmpty()) {
            int nextTask = daysToComplete.poll();
            if (currentTask >= nextTask) {
                count++;
            } else {
                resultList.add(count);
                count = 1;
                currentTask = nextTask;
            }
        }

        resultList.add(count);

        int[] result = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }

        return result;
    }

    @Test
    void 정답() {
        int[] p1 = { 93, 30, 55 };
        int[] s1 = { 1, 30, 5 };
        Assertions.assertArrayEquals(new int[] { 2, 1 }, solution(p1, s1));
        int[] p2 = { 95, 90, 99, 99, 80, 99 };
        int[] s2 = { 1, 1, 1, 1, 1, 1 };
        Assertions.assertArrayEquals(new int[] { 1, 3, 2 }, solution(p2, s2));
    }
}
