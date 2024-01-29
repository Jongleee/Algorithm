import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public long solution(int r1, int r2) {
        long answer = 0;
        for (long i = 1; i < r2; i++) {
            int num2 = (int) Math.sqrt((double) r2 * r2 - i * i);
            if (i >= r1) {
                answer += 4 * (num2 + 1);
            } else {
                int num1 = (int) Math.sqrt((double) r1 * r1 - i * i);
                answer += 4 * (num2 - num1);
                if (Math.sqrt((double) r1 * r1 - i * i) % 1 == 0) {
                    answer += 4;
                }
            }
        }
        answer += 4;
        return answer;
    }

    @Test
    void 정답() {
        Assertions.assertEquals(20, solution(2, 3));
        Assertions.assertEquals(6281440,solution(999999, 1000000));
    }
}
