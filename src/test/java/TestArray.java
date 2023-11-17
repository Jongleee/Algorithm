import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] answer = new int[enroll.length];
        Map<String, String> parent = new HashMap<>();
        Map<String, Integer> memberIndex = new HashMap<>();

        initializeMaps(enroll, referral, parent, memberIndex);

        calculateProfitsForSellers(seller, amount, answer, parent, memberIndex);

        return answer;
    }

    private void initializeMaps(String[] enroll, String[] referral, Map<String, String> parent,
            Map<String, Integer> memberIndex) {
        for (int i = 0; i < enroll.length; i++) {
            parent.put(enroll[i], referral[i]);
            memberIndex.put(enroll[i], i);
        }
    }

    private void calculateProfitsForSellers(String[] seller, int[] amount, int[] answer, Map<String, String> parent,
            Map<String, Integer> memberIndex) {
        for (int i = 0; i < seller.length; i++) {
            String currentSeller = seller[i];
            int profit = 100 * amount[i];

            while (!currentSeller.equals("-")) {
                int parentProfit = profit / 10;
                int myProfit = profit - parentProfit;
                answer[memberIndex.get(currentSeller)] += myProfit;
                currentSeller = parent.get(currentSeller);
                profit /= 10;
                if (profit < 1) {
                    break;
                }
            }
        }
    }

    @Test
    void 정답() {
        String[] e1 = { "john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young" };
        String[] r1 = { "-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward" };
        String[] s1 = { "sam", "emily", "jaimie", "edward" };
        int[] a1 = { 2, 3, 5, 4 };
        String[] s2 = { "young", "john", "tod", "emily", "mary" };
        int[] a2 = { 12, 4, 2, 5, 10 };
        Assertions.assertArrayEquals(new int[] { 0, 110, 378, 180, 270, 450, 0, 0 }, solution(e1, r1, s1, a1));
        Assertions.assertArrayEquals(new int[] { 360, 958, 108, 0, 450, 18, 180, 1080 }, solution(e1, r1, s2, a2));
    }
}
