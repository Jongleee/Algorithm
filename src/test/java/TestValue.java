import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[][] scores) {
        int[] standard = scores[0];
        Arrays.sort(scores, (a, b) -> a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);

        int answer = 1;
        int temp = 0;
        int standardScore = standard[0] + standard[1];

        for (int[] score : scores) {
            if (score[1] < temp) {
                if (Arrays.equals(score, standard)) {
                    return -1;
                }
            } else {
                temp = Math.max(temp, score[1]);
                if (score[0] + score[1] > standardScore) {
                    answer++;
                }
            }
        }

        return answer;
    }

    @Test
    void 정답() {
        Assertions.assertEquals(4, solution(new int[][] { { 2, 2 }, { 1, 4 }, { 3, 2 }, { 3, 2 }, { 2, 1 } }));
    }
}
