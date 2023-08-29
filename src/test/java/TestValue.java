import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValue {
    public String solution(String phone_number) {
        int len = phone_number.length() - 4;
        String asterisks = "*".repeat(len);
        String lastFourDigits = phone_number.substring(len);

        return asterisks + lastFourDigits;
    }

    @Test
    public void 정답() {
        Assertions.assertEquals("*******4444", solution("01033334444"));
        Assertions.assertEquals("*****8888", solution("027778888"));
    }
}
