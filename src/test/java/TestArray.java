import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public int[] solution(String msg) {
        List<Integer> compressedMsg = new ArrayList<>();
        int nextCode = 1;
        Map<String, Integer> dictionary = new HashMap<>();

        for (int i = 65; i <= 90; i++) {
            String character = String.valueOf((char) i);
            dictionary.put(character, nextCode++);
        }

        int i = 0;
        while (i < msg.length()) {
            int maxLength = msg.length() - i;
            int j = maxLength;
            while (j > 0 && !dictionary.containsKey(msg.substring(i, i + j))) {
                j--;
            }
            String substring = msg.substring(i, i + j);

            if (i + j == msg.length()) {
                compressedMsg.add(dictionary.get(substring));
            } else {
                substring = msg.substring(i, i + j + 1);
                compressedMsg.add(dictionary.get(substring.substring(0, substring.length() - 1)));
            }
            dictionary.put(substring, nextCode++);
            i += Math.max(1, j);
        }

        int[] result = new int[compressedMsg.size()];
        for (int k = 0; k < result.length; k++) {
            result[k] = compressedMsg.get(k);
        }

        return result;
    }

    @Test
    void 정답() {
        int[] result1 = { 11, 1, 27, 15 };
        int[] result2 = { 20, 15, 2, 5, 15, 18, 14, 15, 20, 27, 29, 31, 36, 30, 32, 34 };
        int[] result3 = { 1, 2, 27, 29, 28, 31, 30 };

        Assertions.assertArrayEquals(result1, solution("KAKAO"));
        Assertions.assertArrayEquals(result2, solution("TOBEORNOTTOBEORTOBEORNOT"));
        Assertions.assertArrayEquals(result3, solution("ABABABABABABABAB"));
    }
}
