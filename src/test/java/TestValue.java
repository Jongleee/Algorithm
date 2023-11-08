import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[] a) {
        int answer = 0;
        int leftMin = 1000000001;
        int rightMin = 1000000001;
        int n = a.length;

        for (int i = 0; i < n; i++) {
            if (a[i] < leftMin) {
                leftMin = a[i];
                answer++;
            }
            if (a[n - i - 1] < rightMin) {
                rightMin = a[n - i - 1];
                answer++;
            }
            if (leftMin == rightMin) {
                break;
            }
        }

        if (leftMin == rightMin) {
            answer--;
        }

        return answer;
    }

    @Test
    void 정답() {
        int[] a1 = { 9, -1, -5 };
        int[] a2 = { -16, 27, 65, -2, 58, -92, -71, -68, -61, -33 };
        Assertions.assertEquals(3, solution(a1));
        Assertions.assertEquals(6, solution(a2));
    }
}
