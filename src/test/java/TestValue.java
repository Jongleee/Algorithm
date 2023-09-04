import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValue {
    boolean solution(String s) {
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = Character.toLowerCase(s.charAt(i));
            if (c == 'p') {
                num++;
            } else if (c == 'y') {
                num--;
            }
        }

        return num == 0;
    }

    @Test
    public void 정답() {
        Assertions.assertEquals(true, solution("pPoooyY"));
        Assertions.assertEquals(false, solution("Pyy"));
    }
}
