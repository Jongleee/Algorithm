import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(String s) {
        char[] chars = s.toCharArray();

        for (int length = s.length(); length > 1; length--) {
            for (int start = 0; start + length <= s.length(); start++) {
                boolean isPalindrome = isPalindrome(chars, start, length);

                if (isPalindrome) {
                    return length;
                }
            }
        }

        return 1;
    }

    private boolean isPalindrome(char[] chars, int start, int length) {
        for (int i = 0; i < length / 2; i++) {
            if (chars[start + i] != chars[start + length - i - 1]) {
                return false;
            }
        }
        return true;
    }

    @Test
    void 정답() {
        Assertions.assertEquals(7, solution("abcdcba"));
        Assertions.assertEquals(3, solution("abacde"));
    }
}
