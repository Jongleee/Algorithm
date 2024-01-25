import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(String s) {
        int answer = s.length();

        for (int i = 1; i <= s.length() / 2; i++) {
            int zipLevel = 1;
            StringBuilder result = new StringBuilder();
            String zipStr = s.substring(0, i);

            for (int j = i; j <= s.length(); j += i) {
                String next = (j + i > s.length()) ? s.substring(j) : s.substring(j, j + i);

                if (zipStr.equals(next)) {
                    zipLevel++;
                } else {
                    appendCompressed(result, zipLevel, zipStr);
                    zipStr = next;
                    zipLevel = 1;
                }
            }

            appendCompressed(result, zipLevel, zipStr);
            answer = Math.min(answer, result.length());
        }

        return answer;
    }

    private void appendCompressed(StringBuilder result, int zipLevel, String zipStr) {
        result.append((zipLevel > 1) ? Integer.toString(zipLevel) : "").append(zipStr);
    }

    @Test
    void 정답() {
        Assertions.assertEquals(7, solution("aabbaccc"));
        Assertions.assertEquals(9, solution("ababcdcdababcdcd"));
        Assertions.assertEquals(8, solution("abcabcdede"));
        Assertions.assertEquals(14, solution("abcabcabcabcdededededede"));
        Assertions.assertEquals(17, solution("xababcdcdababcdcd"));
    }
}
