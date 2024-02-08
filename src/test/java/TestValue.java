import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public String solution(String number, int k) {
        StringBuilder sb = new StringBuilder();
        int maxIndex = 0;
        int len = number.length() - k;

        for (int i = 0; i < len; i++) {
            int maxDigit = 0;
            for (int j = maxIndex; j <= k + i; j++) {
                int digit = number.charAt(j) - '0';
                if (digit > maxDigit) {
                    maxDigit = digit;
                    maxIndex = j + 1;
                }
                if (maxDigit == 9)
                    break;
            }
            sb.append(maxDigit);
        }

        return sb.toString();
    }

    @Test
    void 정답() {
        Assertions.assertEquals("94", solution("1924", 2));
        Assertions.assertEquals("3234", solution("1231234", 3));
        Assertions.assertEquals("775841", solution("4177252841", 4));
    }
}
