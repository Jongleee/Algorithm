import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public double[] solution(int k, int[][] ranges) {
        List<Double> collatz = generateCollatzSequence(k);
        Double[] collatzNumber = toDouble(collatz);

        double[] areaSum = calculateAreaSum(collatzNumber);
        double[] answer = new double[ranges.length];

        for (int i = 0; i < ranges.length; i++) {
            int start = ranges[i][0];
            int end = collatzNumber.length + ranges[i][1] - 1;

            if (end > start) {
                answer[i] = areaSum[end] - areaSum[start];
            } else if (end == start) {
                answer[i] = 0;
            } else {
                answer[i] = -1;
            }
        }

        return answer;
    }

    private Double[] toDouble(List<Double> collatz) {
        return collatz.toArray(new Double[0]);
    }

    private List<Double> generateCollatzSequence(int k) {
        List<Double> collatz = new ArrayList<>();
        while (k > 1) {
            collatz.add((double) k);
            k = (k % 2 == 0) ? k / 2 : 3 * k + 1;
        }
        collatz.add(1.0);
        return collatz;
    }

    private double[] calculateAreaSum(Double[] collatzNumber) {
        double[] areaSum = new double[collatzNumber.length];
        areaSum[0] = 0;

        for (int i = 1; i < collatzNumber.length; i++) {
            areaSum[i] = (collatzNumber[i - 1] + collatzNumber[i]) / 2 + areaSum[i - 1];
        }

        return areaSum;
    }

    @Test
    void 정답() {
        int[][] r1 = { { 0, 0 }, { 0, -1 }, { 2, -3 }, { 3, -3 } };
        int[][] r2 = { { 0, 0 }, { 1, -2 }, { 3, -3 } };
        Assertions.assertArrayEquals(new double[] { 33.0, 31.5, 0.0, -1.0 }, solution(5, r1));
        Assertions.assertArrayEquals(new double[] { 47.0, 36.0, 12.0 }, solution(3, r2));
    }
}
