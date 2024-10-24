package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class ATM {
    static int n;
    static int m;
    static int cnt;
    static int sccCount;
    static ArrayList<Integer>[] adj;
    static int[] dfsn;
    static int[] sccId;
    static int[] sIndegree;
    static int[] cash;
    static int[] sCash;
    static int[] sMax;
    static boolean[] finished;
    static boolean[] rest;
    static boolean[] sRest;
    static boolean[] sPath;
    static Stack<Integer> stack = new Stack<>();
    static ArrayList<Integer>[] sAdj;
    static int start;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

        dfsn = new int[n + 1];
        finished = new boolean[n + 1];
        sccId = new int[n + 1];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
        }

        cash = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            cash[i] = Integer.parseInt(br.readLine());
        }

        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        int restCount = Integer.parseInt(st.nextToken());

        rest = new boolean[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < restCount; i++) {
            int r = Integer.parseInt(st.nextToken());
            rest[r] = true;
        }

        for (int i = 1; i <= n; i++) {
            if (dfsn[i] == 0) findSCC(i);
        }

        sAdj = new ArrayList[sccCount + 1];
        sIndegree = new int[sccCount + 1];
        sRest = new boolean[sccCount + 1];
        sCash = new int[sccCount + 1];
        sMax = new int[sccCount + 1];
        sPath = new boolean[sccCount + 1];

        for (int i = 1; i <= sccCount; i++) sAdj[i] = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            int currentSCC = sccId[i];
            sCash[currentSCC] += cash[i];
            sMax[currentSCC] = sCash[currentSCC];
            if (rest[i]) sRest[currentSCC] = true;
            if (i == start) sPath[currentSCC] = true;

            for (int next : adj[i]) {
                int nextSCC = sccId[next];
                if (currentSCC != nextSCC && !sAdj[currentSCC].contains(nextSCC)) {
                    sAdj[currentSCC].add(nextSCC);
                    sIndegree[nextSCC]++;
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= sccCount; i++) {
            if (sIndegree[i] == 0) queue.offer(i);
        }

        while (!queue.isEmpty()) {
            int currSCC = queue.poll();
            for (int nextSCC : sAdj[currSCC]) {
                if (sPath[currSCC]) {
                    sMax[nextSCC] = Math.max(sMax[nextSCC], sMax[currSCC] + sCash[nextSCC]);
                    sPath[nextSCC] = true;
                }
                if (--sIndegree[nextSCC] == 0) queue.offer(nextSCC);
            }
        }

        int result = 0;
        for (int i = 1; i <= sccCount; i++) {
            if (sRest[i] && sPath[i]) result = Math.max(result, sMax[i]);
        }
        System.out.println(result);
    }

    public static int findSCC(int curr) {
        dfsn[curr] = ++cnt;
        stack.push(curr);
        int lowLink = dfsn[curr];

        for (int next : adj[curr]) {
            if (dfsn[next] == 0) lowLink = Math.min(lowLink, findSCC(next));
            else if (!finished[next]) lowLink = Math.min(lowLink, dfsn[next]);
        }

        if (lowLink == dfsn[curr]) {
            sccCount++;
            while (true) {
                int node = stack.pop();
                finished[node] = true;
                sccId[node] = sccCount;
                if (node == curr) break;
            }
        }
        return lowLink;
    }
}

/*
6 7
1 2
2 3
3 5
2 4
4 1
2 6
6 5
10
12
8
16
1
5
1 4
4 3 5 6

47
*/