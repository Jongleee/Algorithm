package com.example.algorithm.java.dynamicProgramming;

public class SumOfSuccessivePulsePartSequences {
    public long solution(int[] sequence) {
        int n = sequence.length;

        long[] dp0 = new long[n];
        long[] dp1 = new long[n];

        dp0[0] = pulseMultiNumber(0, 0, sequence);
        dp1[0] = pulseMultiNumber(1, 0, sequence);
        long answer = Math.max(dp0[0], dp1[0]);

        for (int i = 1; i < n; i++) {
            long cal0 = pulseMultiNumber(0, i, sequence);
            long cal1 = pulseMultiNumber(1, i, sequence);

            dp0[i] = Math.max(cal0, dp0[i - 1] + cal0);
            dp1[i] = Math.max(cal1, dp1[i - 1] + cal1);

            answer = Math.max(answer, Math.max(dp0[i], dp1[i]));
        }

        return answer;
    }

    private long pulseMultiNumber(int k, int i, int[] sequence) {
        int multiplier = (i % 2 == 0) ? 1 : -1;
        if (k == 0)
            multiplier *= -1;
        return sequence[i] * multiplier;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(10, solution(new int[] { 2, 3, -6, 1, 3, -1, 2, 4 }));
    // }
}
