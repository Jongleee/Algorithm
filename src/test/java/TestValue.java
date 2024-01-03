import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[] numbers, int target) {
        return dfs(numbers, 0, target, 0, 0);
    }

    public int dfs(int[] numbers, int depth, int target, int sum, int answer) {
        if (depth == numbers.length) {
            return (target == sum) ? answer + 1 : answer;
        } else {
            int result1 = dfs(numbers, depth + 1, target, sum + numbers[depth], answer);
            int result2 = dfs(numbers, depth + 1, target, sum - numbers[depth], answer);
            return result1 + result2;
        }
    }

    @Test
    void 정답() {
        int[] number1 = { 1, 1, 1, 1, 1 };
        int[] number2 = { 4, 1, 2, 1 };

        Assertions.assertEquals(5, solution(number1, 3));
        Assertions.assertEquals(2, solution(number2, 4));
    }
}
