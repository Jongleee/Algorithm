import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[][] data, int column, int rowBegin, int rowEnd) {
        column--;
        rowBegin--;

        int finalColumn = column;
        Arrays.sort(data, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[finalColumn] == o2[finalColumn])
                    return o2[0] - o1[0];
                return o1[finalColumn] - o2[finalColumn];
            }
        });

        int result = 0;

        for (int i = rowBegin; i < rowEnd; i++) {
            int sumSi = calculateSumSi(data[i], i + 1);
            result ^= sumSi;
        }

        return result;
    }

    private int calculateSumSi(int[] row, int rowNumber) {
        int sum = 0;
        for (int i = 0; i < row.length; i++) {
            sum += row[i] % rowNumber;
        }
        return sum;
    }

    @Test
    void 정답() {
        int[][][] data = { { { 2, 2, 6 }, { 1, 5, 10 }, { 4, 2, 9 }, { 3, 8, 3 } } };

        for (int i = 0; i < data.length; i++) {
            Assertions.assertEquals(4, solution(data[i], 2, 2, 3));
        }
    }
}
