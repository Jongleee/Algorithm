import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int distance, int[] rocks, int n) {
        Arrays.sort(rocks);
        return binarySearch(distance, rocks, n);
    }

    private int binarySearch(int distance, int[] rocks, int n) {
        int left = 1;
        int right = distance;
        int answer = 0;

        while (left <= right) {
            int mid = (left + right) / 2;
            int removedRockCnt = countRemovedRocks(rocks, mid, distance);

            if (removedRockCnt <= n) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return answer;
    }

    private int countRemovedRocks(int[] rocks, int mid, int distance) {
        int before = 0;
        int end = distance;
        int removeCnt = 0;

        for (int rock : rocks) {
            if (rock - before < mid) {
                removeCnt++;
            } else {
                before = rock;
            }
        }

        if (end - before < mid) {
            removeCnt++;
        }

        return removeCnt;
    }

    @Test
    void 정답() {
        int[] rocks = { 2, 14, 11, 21, 17 };

        Assertions.assertEquals(4, solution(26, rocks, 2));
    }
}
