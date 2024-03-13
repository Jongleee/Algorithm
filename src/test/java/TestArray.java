import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public int[] solution(int n, long k) {
        int[] result = new int[n];
        long factorialNumber = 1;
    
        int[] numbers = new int[n];

        for (int i = 1; i <= n; i++) {
            factorialNumber *= i;
            numbers[i-1] = i;
        }
    
        k--;
    
        for (int i = 0; i < n; i++) {
            factorialNumber /= (n - i);
            int index = (int) (k / factorialNumber);
            result[i] = numbers[index];
            removeElement(numbers, index, n);
            k %= factorialNumber;
        }
    
        return result;
    }
    
    private void removeElement(int[] arr, int index, int size) {
        for (int i = index; i < size - 1; i++) {
            arr[i] = arr[i + 1];
        }
    }

    @Test
    void 정답() {
        int[] n = { 3 };
        int[] k = { 5 };

        int[][] result = { { 3,1,2} };

        for (int i = 0; i < result.length; i++) {
            Assertions.assertArrayEquals(result[i], solution(n[i],k[i]));
        }
    }
}
