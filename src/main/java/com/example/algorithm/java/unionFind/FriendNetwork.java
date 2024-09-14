package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class FriendNetwork {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int testCaseCount = Integer.parseInt(br.readLine());

        while (testCaseCount-- > 0) {
            Map<String, Integer> nameMapper = new HashMap<>();
            int friendshipCount = Integer.parseInt(br.readLine());
            int[] parent = new int[friendshipCount * 2];
            Arrays.fill(parent, -1);

            while (friendshipCount-- > 0) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                String name1 = st.nextToken();
                String name2 = st.nextToken();

                if (!nameMapper.containsKey(name1)) {
                    nameMapper.put(name1, nameMapper.size());
                }
                if (!nameMapper.containsKey(name2)) {
                    nameMapper.put(name2, nameMapper.size());
                }

                int friend1 = nameMapper.get(name1);
                int friend2 = nameMapper.get(name2);

                sb.append(union(parent, friend1, friend2)).append("\n");
            }
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    private static int find(int[] parent, int x) {
        if (parent[x] < 0) {
            return x;
        }
        parent[x] = find(parent, parent[x]);
        return parent[x];
    }

    private static int union(int[] parent, int a, int b) {
        a = find(parent, a);
        b = find(parent, b);

        if (a != b) {
            parent[a] += parent[b];
            parent[b] = a;
        }
        return -parent[a];
    }
}

/*
2
3
Fred Barney
Barney Betty
Betty Wilma
3
Fred Barney
Betty Wilma
Barney Betty

2
3
4
2
2
4
*/