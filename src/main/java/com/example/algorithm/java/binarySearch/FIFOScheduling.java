package com.example.algorithm.java.binarySearch;

public class FIFOScheduling {
    public int solution(int n, int[] cores) {
        int left = -1;
        int right = 100000;
        left = findLeft(n, cores, left, right);
        if (left == -1)
            return n;

        int cnt = cores.length;
        for (int i = 0; i < cores.length; i++)
            cnt += left / cores[i];

        for (int i = 0; i < cores.length; i++) {
            if ((left + 1) % cores[i] == 0)
                cnt++;
            if (cnt == n)
                return i + 1;
        }
        return 0;
    }

    private int findLeft(int n, int[] cores, int left, int right) {
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            int cnt = cores.length;
            if (mid > 0) {
                for (int i = 0; i < cores.length; i++) {
                    cnt += mid / cores[i];
                }
            }
            if (cnt < n)
                left = mid;
            else
                right = mid;
        }
        return left;
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals(2, solution(6, new int[] { 1, 2, 3 }));
    // }
}
