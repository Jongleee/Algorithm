package com.example.algorithm.java.binarySearch;

public class CarryingGoldSilver {
    public long solution(int a, int b, int[] g, int[] s, int[] w, int[] t) {
        long answer = 4 * (long) 10e13;

        int cityLength = g.length;
        long start = 0;
        long end = answer;

        while (start <= end) {
            long mid = (start + end) / 2;
            int gold = 0;
            int silver = 0;
            int sum = 0;

            for (int i = 0; i < cityLength; i++) {
                int nowGold = g[i];
                int nowSilver = s[i];
                int nowWeight = w[i];
                long nowTime = t[i];

                long moveCount = mid / (nowTime * 2);
                if (mid % (nowTime * 2) >= t[i]) {
                    moveCount++;
                }

                gold += Math.min(nowGold, moveCount * nowWeight);
                silver += Math.min(nowSilver, moveCount * nowWeight);
                sum += Math.min(nowGold + nowSilver, moveCount * nowWeight);
            }

            if (a <= gold && b <= silver && a + b <= sum) {
                end = mid - 1;
                answer = Math.min(mid, answer);
                continue;
            }

            start = mid + 1;
        }

        return answer;
    }

    // @Test
    // public void 정답() {
    // Assertions.assertEquals(50,
    // solution(10, 10, new int[] { 100 }, new int[] { 100 }, new int[] { 7 }, new
    // int[] { 10 }));

    // Assertions.assertEquals(499, solution(90, 500, new int[] { 70, 70, 0 }, new
    // int[] { 0, 0, 500 },
    // new int[] { 100, 100, 2 }, new int[] { 4, 8, 1 }));

    // }
}
