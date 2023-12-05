import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[] cookie) {
        int answer = 0;
        for (int i = 0; i < cookie.length - 1; i++) {
            int firstIndex = i;
            int secondIndex = i + 1;
            int firstSum = cookie[firstIndex];
            int secondSum = cookie[secondIndex];

            while (true) {
                if (firstSum == secondSum) {
                    answer = Math.max(answer, secondSum);
                }

                if (firstSum <= secondSum && firstIndex > 0) {
                    firstSum += cookie[--firstIndex];
                } else if (firstSum > secondSum && secondIndex < cookie.length - 1) {
                    secondSum += cookie[++secondIndex];
                } else {
                    break;
                }
            }
        }
        return answer;
    }

    @Test
    void 정답() {
        int[] c1 = { 1,1,2,3};
        int[] c2 = { 1,2, 4, 5 };
        Assertions.assertEquals(3, solution(c1));
        Assertions.assertEquals(0, solution(c2));
    }
}
