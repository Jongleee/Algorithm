import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public long solution(int k, int d) {
        long answer = 0;

        long max_x = d / k;

        for (long x = 0; x <= max_x; x++) {
            answer += (long) Math.sqrt((double) d * d - x * x * k * k) / k + 1;
        }

        return answer;
    }

    @Test
    void 정답() {
        int[] k = { 2, 1 };
        int[] d = { 4, 5 };
        int[] result = { 6, 26 };

        for (int i = 0; i < result.length; i++) {
            Assertions.assertEquals(result[i], solution(k[i], d[i]));
        }
    }
}
