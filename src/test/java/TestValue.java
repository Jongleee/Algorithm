import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValue {
    public int solution(String s) {
        int answer = 0;
        char temp = s.charAt(0);
        int num = 0;
        int total = 0;
        
        for (char c : s.toCharArray()) {
            if (num * 2 == total) {
                answer++;
                temp = c;
            }
            if (c == temp) {
                num++;
            }
            total++;
        }
        return answer;
    }

    @Test
    public void 정답() {
        Assertions.assertEquals(3, solution("banana"));
        Assertions.assertEquals(6, solution("abracadabra"));
        Assertions.assertEquals(3, solution("aaabbaccccabba"));
    }
}
