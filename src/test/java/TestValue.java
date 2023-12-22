import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[][] targets) {
        Arrays.sort(targets, Comparator.comparingInt(target -> target[1]));

        int lastLocation = -1;
        int missileCnt = 0;

        for (int[] target : targets) {
            if (targetIsAfterLastLocation(target, lastLocation)) {
                missileCnt++;
                lastLocation = target[1];
            }
        }

        return missileCnt;
    }

    private boolean targetIsAfterLastLocation(int[] target, int lastLocation) {
        return target[0] >= lastLocation;
    }

    @Test
    void 정답() {
        int[][] targets = 	{{4, 5}, {4, 8}, {10, 14}, {11, 13}, {5, 12}, {3, 7}, {1, 4}};

        Assertions.assertEquals(3, solution(targets));
    }
}
