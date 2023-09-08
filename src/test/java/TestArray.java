import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArray {
    public long[] solution(long[] numbers) {
        long[] answer = new long[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            answer[i] = processNumber(numbers[i]);
        }

        return answer;
    }

    private long processNumber(long number) {
        if (number % 2 == 0) {
            return number + 1;
        } else {
            String binaryString = Long.toBinaryString(number);

            int zeroIdx = binaryString.lastIndexOf("0");

            if (zeroIdx != -1) {
                binaryString = binaryString.substring(0, zeroIdx) + "10" + binaryString.substring(zeroIdx + 2);
            } else {
                binaryString = "10" + binaryString.substring(1);
            }

            return Long.parseLong(binaryString, 2);
        }
    }

    @Test
    public void 정답() {
        Assertions.assertArrayEquals(new long[] { 3, 11 }, solution(new long[] { 2, 7 }));
    }
}
