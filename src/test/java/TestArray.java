import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public int[] solution(String[] gems) {
        Map<String, Integer> gemCount = new HashMap<>();
        Set<String> uniqueGems = new HashSet<>();
        Queue<String> gemQueue = new LinkedList<>();

        int minLength = gems.length + 1;
        int start = 0;
        int startIndex = 0;

        uniqueGems.addAll(Arrays.asList(gems));

        for (int i = 0; i < gems.length; i++) {
            gemCount.put(gems[i], gemCount.getOrDefault(gems[i], 0) + 1);
            gemQueue.add(gems[i]);

            while (gemCount.get(gemQueue.peek()) > 1) {
                gemCount.put(gemQueue.peek(), gemCount.get(gemQueue.poll()) - 1);
                startIndex++;
            }

            if (gemCount.size() == uniqueGems.size() && minLength > (i - startIndex)) {
                minLength = i - startIndex;
                start = startIndex + 1;
            }
        }

        return new int[] { start, start + minLength };
    }

    @Test
    void 정답() {
        String[] g1 = { "DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA" };
        String[] g2 = { "AA", "AB", "AC", "AA", "AC" };
        String[] g3 = { "XYZ", "XYZ", "XYZ" };
        String[] g4 = { "ZZZ", "YYY", "NNNN", "YYY", "BBB" };
        Assertions.assertArrayEquals(new int[] { 3, 7 }, solution(g1));
        Assertions.assertArrayEquals(new int[] { 1, 3 }, solution(g2));
        Assertions.assertArrayEquals(new int[] { 1, 1 }, solution(g3));
        Assertions.assertArrayEquals(new int[] { 1, 5 }, solution(g4));
    }
}
