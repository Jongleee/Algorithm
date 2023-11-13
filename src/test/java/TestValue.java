import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[] a, int[] b) {
        int answer = 0;
        Arrays.sort(a);
        Arrays.sort(b);
        int indexB = a.length - 1;
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] < b[indexB]) {
                answer++;
                indexB--;
            }
        }
        return answer;
    }

    @Test
    void 정답() {
        int[] a1 = { 5, 1, 3, 7 };
        int[] b1 = { 2, 2, 6, 8 };
        int[] a2 = { 2, 2, 2, 2 };
        int[] b2 = { 1, 1, 1, 1 };
        Assertions.assertEquals(3, solution(a1, b1));
        Assertions.assertEquals(0, solution(a2, b2));
    }
}
