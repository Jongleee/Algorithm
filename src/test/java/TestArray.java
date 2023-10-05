import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArray {
    private Map<String, List<Integer>> map = new HashMap<>();

    public int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];

        for (String inf : info) {
            String[] infoParts = inf.split(" ");
            int score = Integer.parseInt(infoParts[4]);
            generateCombinations(infoParts, 0, "", score);
        }

        for (List<Integer> list : map.values()) {
            Collections.sort(list);
        }

        for (int i = 0; i < query.length; i++) {
            String[] queryParts = query[i].replace(" and ", "").split(" ");
            int score = Integer.parseInt(queryParts[1]);
            answer[i] = binarySearch(queryParts[0], score);
        }

        return answer;
    }

    private void generateCombinations(String[] infoParts, int depth, String prefix, int score) {
        if (depth == 4) {
            map.computeIfAbsent(prefix, k -> new ArrayList<>()).add(score);
            return;
        }

        generateCombinations(infoParts, depth + 1, prefix + infoParts[depth], score);
        generateCombinations(infoParts, depth + 1, prefix + "-", score);
    }

    private int binarySearch(String key, int score) {
        if (map.containsKey(key)) {
            List<Integer> list = map.get(key);
            int left = 0;
            int right = list.size() - 1;

            if (list.get(right) < score) {
                return 0;
            }

            while (left < right) {
                int mid = (left + right) / 2;

                if (list.get(mid) < score) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            return list.size() - left;
        }

        return 0;
    }

    @Test
    public void 정답() {
        String[] info = { "java backend junior pizza 150", "python frontend senior chicken 210",
                "python frontend senior chicken 150", "cpp backend senior pizza 260", "java backend junior chicken 80",
                "python backend senior chicken 50" };
        String[] query = { "java and backend and junior and pizza 100",
                "python and frontend and senior and chicken 200", "cpp and - and senior and pizza 250",
                "- and backend and senior and - 150", "- and - and - and chicken 100", "- and - and - and - 150" };
        Assertions.assertArrayEquals(new int[] { 1, 1, 1, 1, 2, 4 }, solution(info, query));
    }
}
