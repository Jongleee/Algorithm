package com.example.algorithm.java.practice;

public class InspectingWall {
    public static void main(String[] args) {
        System.out.println(solution(12, new int[] { 1, 5, 6, 10 }, new int[] { 1, 2, 3, 4 }));
    }

    static int ans = -1;
    static int[] spreadWeak;
    static int weakCnt;

    public static int solution(int n, int[] weak, int[] dist) {
        weakCnt = weak.length;
        spreadWeak = spreadPoint(n, weak);

        for (int i = 1; i <= dist.length; i++) {
            perm(0, i, dist, new boolean[dist.length], new int[i]);
        }

        return ans;
    }

    private static void perm(int depth, int cnt, int[] dist, boolean[] visit, int[] res) {
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

    private static void check(int[] res) {
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

    private static int[] spreadPoint(int n, int[] weak) {
        int[] spread = new int[weak.length * 2 - 1];
        for (int i = 0; i < weak.length; i++) {
            if (i <= weak.length)
                spread[i] = weak[i];
        }
        for (int i = 0; i < weak.length - 1; i++) {
            spread[i + weak.length] = weak[i] + n;
        }

        return spread;
    }
}
