package com.example.algorithm.java.dynamicProgramming;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class MinimizeSalesDecline {

    static final int INF = 999999999;

    static List<Integer>[] adj;
    static int[][] dp;
    static List<Integer> val;

    static int getDp(int node, int parentNode, int select) {
        if (dp[node][select] != 0) {
            return dp[node][select];
        }

        int ret = 0;
        int minDiff = INF;
        boolean join = false;

        if (node != 1 && adj[node].size() == 1) {
            dp[node][select] = select == 1 ? val.get(node - 1) : 0;
            return dp[node][select];
        }

        for (int child : adj[node]) {
            if (child == parentNode) {
                continue;
            }

            int resSel = getDp(child, node, 1);
            int resNoSel = getDp(child, node, 0);
            ret += Math.min(resSel, resNoSel);

            if (resSel < resNoSel) {
                join = true;
            } else {
                minDiff = Math.min(minDiff, resSel - resNoSel);
            }
        }

        if (select == 1) {
            ret += val.get(node - 1);
        } else {
            if (!join) {
                ret += minDiff;
            }
        }

        dp[node][select] = ret;
        return dp[node][select];
    }

    static void makeConnection(int[][] links) {
        for (int[] link : links) {
            int f = link[0];
            int s = link[1];
            adj[f].add(s);
            adj[s].add(f);
        }
    }

    public static int solution(int[] sales, int[][] links) {
        int answer = 0;

        val = new ArrayList<>();
        for (int v : sales) {
            val.add(v);
        }

        int n = sales.length;
        adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        makeConnection(links);

        dp = new int[n + 1][2];
        answer = Math.min(getDp(1, 0, 1), getDp(1, 0, 0));

        return answer;
    }

    public static void main(String[] args) {
        int[] sales1 = { 14, 17, 15, 18, 19, 14, 13, 16, 28, 17 };
        int[][] links1 = { { 10, 8 }, { 1, 9 }, { 9, 7 }, { 5, 4 }, { 1, 5 }, { 5, 10 }, { 10, 6 }, { 1, 3 },
                { 10, 2 } };
        System.out.println(solution(sales1, links1)); // 44
    }
}
