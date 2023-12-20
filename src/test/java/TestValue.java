import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public String solution(int[] numbers) {
        String result = Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .sorted((s1, s2) -> (s2 + s1).compareTo(s1 + s2))
                .collect(Collectors.joining());

        return result.startsWith("0") ? "0" : result;
    }

    @Test
    void 정답() {
        int[] n1 = { 6, 10, 2 };
        int[] n2 = { 3, 30, 34, 5, 9 };

        Assertions.assertEquals("6210", solution(n1));
        Assertions.assertEquals("9534330", solution(n2));
    }
}
