import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[] picks, String[] minerals) {
        int answer = 0;
        int num = picks[0] + picks[1] + picks[2];
        int[][] section = new int[minerals.length / 5 + 1][3];

        for (int i = 0; i < minerals.length && num > 0; i++) {
            int index = i / 5;
            updateSection(section[index], minerals[i]);
            if (i % 5 == 4) {
                num--;
            }
        }

        Arrays.sort(section, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[2] - o1[2];
            }
        });

        int pick = 0;
        for (int[] arr : section) {
            while (pick < 3 && picks[pick] == 0) {
                pick++;
            }
            if (pick == 3) {
                break;
            }
            picks[pick]--;
            answer += arr[pick];
        }

        return answer;
    }

    private void updateSection(int[] section, String mineral) {
        switch (mineral.charAt(0)) {
            case 'd':
                section[0]++;
                section[1] += 5;
                section[2] += 25;
                break;
            case 'i':
                section[0]++;
                section[1]++;
                section[2] += 5;
                break;
            default:
                section[0]++;
                section[1]++;
                section[2]++;
        }
    }

    @Test
    void 정답() {
        int[] picks1 = { 1, 3, 2 };
        String[] minerals1 = { "diamond", "diamond", "diamond", "iron", "iron", "diamond", "iron", "stone" };
        int[] picks2 = { 0, 1, 1 };
        String[] minerals2 = { "diamond", "diamond", "diamond", "diamond", "diamond", "iron", "iron", "iron", "iron",
                "iron", "diamond" };

        Assertions.assertEquals(12, solution(picks1, minerals1));
        Assertions.assertEquals(50, solution(picks2, minerals2));
    }
}
