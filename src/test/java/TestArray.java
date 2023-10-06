import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArray {
    private List<String> answerList;
    private Map<String, Integer> hashMap;

    public String[] solution(String[] orders, int[] course) {
        answerList = new ArrayList<>();
        hashMap = new HashMap<>();
        preprocessOrders(orders);
        generateCombinations(orders, course);
        return getAnswer();
    }

    private void preprocessOrders(String[] orders) {
        for (int i = 0; i < orders.length; i++) {
            char[] arr = orders[i].toCharArray();
            Arrays.sort(arr);
            orders[i] = String.valueOf(arr);
        }
    }

    private void generateCombinations(String[] orders, int[] course) {
        for (int courseLength : course) {
            for (String order : orders) {
                generateCombination("", order, courseLength);
            }
            checkAndAddValidCombinations();
            hashMap.clear();
        }
    }

    private void generateCombination(String current, String remaining, int count) {
        if (count == 0) {
            hashMap.put(current, hashMap.getOrDefault(current, 0) + 1);
            return;
        }

        for (int i = 0; i < remaining.length(); i++) {
            generateCombination(current + remaining.charAt(i), remaining.substring(i + 1), count - 1);
        }
    }

    private void checkAndAddValidCombinations() {
        if (!hashMap.isEmpty()) {
            int maxFrequency = Collections.max(hashMap.values());

            if (maxFrequency > 1) {
                for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                    if (entry.getValue() == maxFrequency) {
                        answerList.add(entry.getKey());
                    }
                }
            }
        }
    }

    private String[] getAnswer() {
        Collections.sort(answerList);
        return answerList.toArray(new String[0]);
    }

    @Test
    public void 정답() {
        String[] o1 = { "ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH" };
        int[] c1 = { 2, 3, 4 };
        Assertions.assertArrayEquals(new String[] { "AC", "ACDE", "BCFG", "CDE" }, solution(o1, c1));
        String[] o2 = { "ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD" };
        int[] c2 = { 2, 3, 5 };
        Assertions.assertArrayEquals(new String[] { "ACD", "AD", "ADE", "CD", "XYZ" }, solution(o2, c2));
        String[] o3 = { "XYZ", "XWY", "WXA" };
        Assertions.assertArrayEquals(new String[] { "WX", "XY" }, solution(o3, c1));
    }
}
