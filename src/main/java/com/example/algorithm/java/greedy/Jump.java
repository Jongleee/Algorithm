package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Jump {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int testCaseCount = Integer.parseInt(br.readLine());
        long[] powerSum = new long[31];
        for (int i = 1; i < 31; i++) {
            powerSum[i] = (1L << i) - 1;
        }

        while (testCaseCount-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long x = Long.parseLong(st.nextToken());
            long y = Long.parseLong(st.nextToken());
            sb.append(getMaxJumpCount(x, y, powerSum)).append('\n');
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    private static int getMinJumpCount(long target) {
        long step = 1;
        int count = 0;
        while (target > 0) {
            target -= step;
            if (2 * step > target)
                step = 1;
            else
                step *= 2;
            count++;
        }
        return count;
    }

    private static int getMaxJumpCount(long left, long right, long[] powerSum) {
        if (left == right)
            return getMinJumpCount(right);
        if (left + 1 == right)
            return Math.max(getMinJumpCount(left), getMinJumpCount(right));

        int index = getPowerIndexExceeding(right, powerSum);
        if (index == 1)
            return 1;

        long jump1 = powerSum[index - 1];
        long jump2 = powerSum[index - 2];

        if (left <= jump2) {
            return Math.max((index - 2) + getMaxJumpCount(0, jump2, powerSum),
                    (index - 1) + getMaxJumpCount(0, right - jump1, powerSum));
        }

        if (left > jump1) {
            return (index - 1) + getMaxJumpCount(left - jump1, right - jump1, powerSum);
        }

        if (left < jump1) {
            return Math.max((index - 2) + getMaxJumpCount(left - jump2, jump2, powerSum),
                    (index - 1) + getMaxJumpCount(0, right - jump1, powerSum));
        }

        return (index - 1) + getMaxJumpCount(0, right - jump1, powerSum);
    }

    private static int getPowerIndexExceeding(long value, long[] powerSum) {
        for (int i = 0; i < powerSum.length; i++) {
            if (powerSum[i] > value)
                return i;
        }
        return powerSum.length;
    }
}

/*
3
1 4
7 7
12 16

3
3
7
*/