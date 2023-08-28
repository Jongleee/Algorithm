import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValue {
    public int solution(int[] numbers) {
        int temp = 0;
        for (int i : numbers) {
            temp += i;
        }
        return 45 - temp;
    }

    @Test
    public void 정답() {
        Assertions.assertEquals(14, solution(new int[] { 1, 2, 3, 4, 6, 7, 8, 0 }));
        Assertions.assertEquals(6, solution(new int[] { 5, 8, 4, 0, 6, 7, 9 }));
    }
}
