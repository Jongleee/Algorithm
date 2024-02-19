import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public boolean solution(String[] phone_book) {
        Set<String> set = new HashSet<>(Arrays.asList(phone_book));

        for (String phone : phone_book) {
            for (int i = 1; i < phone.length(); i++) {
                if (set.contains(phone.substring(0, i))) {
                    return false;
                }
            }
        }

        return true;
    }

    @Test
    void 정답() {
        String[] phone_book1 = { "119", "97674223", "1195524421" };
        String[] phone_book2 = { "123", "456", "789" };
        String[] phone_book3 = { "12", "123", "1235", "567", "88" };

        Assertions.assertEquals(false, solution(phone_book1));
        Assertions.assertEquals(true, solution(phone_book2));
        Assertions.assertEquals(false, solution(phone_book3));
    }
}
