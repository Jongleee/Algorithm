import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    private int[] result;
    private int[] lion;
    private int maxScoreDifference;

    public int[] solution(int n, int[] info) {
        result = new int[] { -1 };
        lion = new int[11];
        maxScoreDifference = 0;
        calculateOptimalLionDistribution(info, 1, n);
        return result;
    }

    public void calculateOptimalLionDistribution(int[] info, int count, int n) {
        if (count == n + 1) {
            int apeachPoint = 0;
            int lionPoint = 0;
            for (int i = 0; i <= 10; i++) {
                int score = 10 - i;
                if (info[i] + lion[i] != 0) {
                    if (info[i] < lion[i]) {
                        lionPoint += score;
                    } else {
                        apeachPoint += score;
                    }
                }
            }
            if (isBetterDistribution(apeachPoint, lionPoint)) {
                result = lion.clone();
                maxScoreDifference = lionPoint - apeachPoint;
            }
            return;
        }
        for (int j = 0; j <= 10 && lion[j] <= info[j]; j++) {
            lion[j]++;
            calculateOptimalLionDistribution(info, count + 1, n);
            lion[j]--;
        }
    }

    private boolean isBetterDistribution(int apeachPoint, int lionPoint) {
        return lionPoint > apeachPoint && lionPoint - apeachPoint >= maxScoreDifference;
    }

    @Test
    void 정답() {
        int[] i1 = { 0, 0, 0, 0, 0, 0, 0, 0, 3, 4, 3 };
        int[] i2 = { 0, 0, 1, 2, 0, 1, 1, 1, 1, 1, 1 };
        int[] i3 = { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        Assertions.assertArrayEquals(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2 }, solution(10, i1));
        Assertions.assertArrayEquals(new int[] { 1, 1, 2, 0, 1, 2, 2, 0, 0, 0, 0 }, solution(9, i2));
        Assertions.assertArrayEquals(new int[] { -1 }, solution(1, i3));
    }
}
