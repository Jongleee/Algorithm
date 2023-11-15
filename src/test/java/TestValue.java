import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int n) {
        int targetBitCount = Integer.bitCount(n);

        for (int i = n + 1; i <= 1000000; i++) {
            if (Integer.bitCount(i) == targetBitCount) {
                return i;
            }
        }

        return targetBitCount;
    }

    @Test
    void 정답() {
        Assertions.assertEquals(83, solution(78));
        Assertions.assertEquals(23, solution(15));
    }
}
