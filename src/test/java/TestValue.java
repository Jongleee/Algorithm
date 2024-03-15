import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public long solution(int w, int h) {
        long area = (long) w * h;

        if (w == 1 || h == 1) {
            return 0;
        }

        long gcd = calculateGcd(w, h);
        long overlappedArea = (w / gcd) + (h / gcd) - 1;
        return area - gcd * overlappedArea;
    }

    public long calculateGcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return calculateGcd(b, a % b);
    }

    @Test
    void 정답() {
        int[] w = { 8 };
        int[] h = { 12 };
        int[] result = { 80 };

        for (int i = 0; i < result.length; i++) {
            Assertions.assertEquals(result[i], solution(w[i], h[i]));
        }
    }
}
