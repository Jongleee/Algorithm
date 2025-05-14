package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class HalloweenBully {
    static class Node {
        int member, candy;

        Node(int member, int candy) {
            this.member = member;
            this.candy = candy;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int studentCount = Integer.parseInt(st.nextToken());
        int friendRelations = Integer.parseInt(st.nextToken());
        int bagCapacity = Integer.parseInt(st.nextToken()) - 1;

        int[] parents = new int[studentCount + 1];
        int[] candies = new int[studentCount + 1];
        Arrays.fill(parents, -1);

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= studentCount; i++) {
            candies[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < friendRelations; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            union(a, b, parents, candies);
        }

        List<Node> groups = getGroups(studentCount, parents, candies);
        int maxCandies = getMaxCandies(groups, bagCapacity);
        System.out.println(maxCandies);
    }

    private static int find(int x, int[] parents) {
        if (parents[x] < 0)
            return x;
        return parents[x] = find(parents[x], parents);
    }

    private static void union(int a, int b, int[] parents, int[] candies) {
        int rootA = find(a, parents);
        int rootB = find(b, parents);
        if (rootA == rootB)
            return;

        parents[rootA] += parents[rootB];
        candies[rootA] += candies[rootB];
        parents[rootB] = rootA;
    }

    private static List<Node> getGroups(int n, int[] parents, int[] candies) {
        List<Node> groupList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (parents[i] < 0) {
                groupList.add(new Node(-parents[i], candies[i]));
            }
        }
        return groupList;
    }

    private static int getMaxCandies(List<Node> groups, int capacity) {
        int[] dp = new int[capacity + 1];
        for (Node group : groups) {
            for (int i = capacity; i >= group.member; i--) {
                dp[i] = Math.max(dp[i], dp[i - group.member] + group.candy);
            }
        }
        return dp[capacity];
    }
}

/*
5 4 4
9 9 9 9 9
1 2      
2 3
3 4
4 5 

0


10 6 6
9 15 4 4 1 5 19 14 20 5
1 3
2 5
4 9
6 2
7 8
6 10

57
*/