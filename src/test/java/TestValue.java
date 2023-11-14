import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int distance, int[][] scope, int[][] times) {
        List<Integer> validTimes = findValidTimes(scope, times);
        Collections.sort(validTimes);

        if (!validTimes.isEmpty()) {
            return validTimes.get(0);
        }
        return distance;
    }

    private List<Integer> findValidTimes(int[][] scope, int[][] times) {
        List<Integer> validTimes = new ArrayList<>();

        for (int i = 0; i < scope.length; i++) {
            Arrays.sort(scope[i]);

            int start = scope[i][0];
            int end = scope[i][1];
            int workTime = times[i][0];
            int restTime = times[i][1];

            while (start <= end) {
                int timeCheck = start % (workTime + restTime);
                if (timeCheck > 0 && timeCheck <= workTime) {
                    validTimes.add(start);
                    break;
                }
                start++;
            }
        }

        return validTimes;
    }

    @Test
    void 정답() {
        Assertions.assertEquals(8,
                solution(10, new int[][] { { 3, 4 }, { 5, 8 } }, new int[][] { { 2, 5 }, { 4, 3 } }));
    }
}
