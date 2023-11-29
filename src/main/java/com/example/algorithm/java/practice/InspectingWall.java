package com.example.algorithm.java.practice;

public class InspectingWall {
    private int ans;
    private int[] spreadWeak;
    private int weakCnt;

    public int solution(int n, int[] weak, int[] dist) {
        ans = -1;
        weakCnt = weak.length;
        spreadWeak = spreadPoint(n, weak);

        for (int i = 1; i <= dist.length; i++) {
            perm(0, i, dist, new boolean[dist.length], new int[i]);
        }

        return ans;
    }

    private void perm(int depth, int cnt, int[] dist, boolean[] visit, int[] res) {
        if (ans != -1) {
            return;
        }
        if (depth == cnt) {
            check(res);
            return;
        }

        for (int i = 0; i < dist.length; i++) {
            if (visit[i]) {
                continue;
            }
            res[depth] = dist[i];
            visit[i] = true;
            perm(depth + 1, cnt, dist, visit, res);
            visit[i] = false;
        }
    }

    private void check(int[] res) {
        outer: for (int i = 0; i < weakCnt; i++) {
            int start = i;
            int f = 0;
            for (int j = i; j < i + weakCnt; j++) {
                if (spreadWeak[j] - spreadWeak[start] > res[f]) {
                    start = j;
                    f++;
                }
                if (f == res.length) {
                    continue outer;
                }
            }
            ans = res.length;
            return;
        }
    }

    private int[] spreadPoint(int n, int[] weak) {
        int[] spread = new int[weak.length * 2 - 1];
        System.arraycopy(weak, 0, spread, 0, weak.length);
        for (int i = 0; i < weak.length - 1; i++) {
            spread[i + weak.length] = weak[i] + n;
        }

        return spread;
    }

    // @Test
    // void 정답() {
    //     int[] w1 = { 1, 5, 6, 10 };
    //     int[] d1 = { 1, 2, 3, 4 };
    //     int[] w2 = { 1, 3, 4, 9, 10 };
    //     int[] d2 = { 3, 5, 7 };
    //     Assertions.assertEquals(2, solution(12, w1, d1));
    //     Assertions.assertEquals(1, solution(12, w2, d2));
    // }
}
