import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public int[] solution(String s) {
        String[] elements = extractElements(s);
        List<Integer> resultList = new ArrayList<>();

        Arrays.sort(elements, (s1, s2) -> s1.length() - s2.length());

        for (String element : elements) {
            String[] values = element.split(",");
            for (String value : values) {
                int intValue = Integer.parseInt(value);
                if (!resultList.contains(intValue)) {
                    resultList.add(intValue);
                }
            }
        }

        return resultList.stream().mapToInt(Integer::intValue).toArray();
    }

    private String[] extractElements(String s) {
        String[] parts = s.substring(2, s.length() - 2).split("},\\{");
        return parts;
    }

    @Test
    void 정답() {
        Assertions.assertArrayEquals(new int[] { 2, 1, 3, 4 },
                solution("{{2},{2,1},{2,1,3},{2,1,3,4}}"));
        Assertions.assertArrayEquals(new int[] { 2, 1, 3, 4 },
                solution("{{1,2,3},{2,1},{1,2,4,3},{2}}"));
        Assertions.assertArrayEquals(new int[] { 123 },
                solution("{{123}}"));
        Assertions.assertArrayEquals(new int[] { 3, 2, 4, 1, },
                solution("{{4,2,3},{3},{2,3,4,1},{2,3}}"));
    }
}
