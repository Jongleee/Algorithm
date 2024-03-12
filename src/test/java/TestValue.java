import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int n, int k) {
        int answer = 0;
        int[] digits = convertToBaseKArray(n, k);

        int j;
        for (int i = 0; i < digits.length; i = j) {
            j = i + 1;
            while (j < digits.length && digits[j] != 0)
                j++;

            if (isPrime(convertFromBaseK(digits, i, j)))
                answer++;
        }

        return answer;
    }

    public int[] convertToBaseKArray(int n, int k) {
        int[] result = new int[32];
        int i = 31;

        while (n > 0) {
            result[i--] = n % k;
            n /= k;
        }

        return result;
    }

    public long convertFromBaseK(int[] digits, int start, int end) {
        long result = 0;
        long multiplier = 1;

        for (int i = end - 1; i >= start; i--) {
            result += digits[i] * multiplier;
            multiplier *= 10;
        }

        return result;
    }

    public boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        } else if (n == 2) {
            return true;
        }

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    @Test
    void 정답() {
        int[] n = { 437674, 110011 };
        int[] k = { 3, 10 };
        int[] result = { 3, 2 };

        for (int i = 0; i < result.length; i++) {
            Assertions.assertEquals(result[i], solution(n[i], k[i]));
        }
    }
}
