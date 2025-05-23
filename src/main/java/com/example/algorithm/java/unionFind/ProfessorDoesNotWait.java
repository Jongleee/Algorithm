package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ProfessorDoesNotWait {
    static class Node {
        int parent;
        long weightDiff;

        Node(int parent) {
            this.parent = parent;
        }
    }

    public static void main(String[] args) throws IOException {
        final String unknown = "UNKNOWN\n";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            if (n == 0)
                break;
            int m = Integer.parseInt(st.nextToken());

            Node[] nodes = new Node[n + 1];
            for (int i = 1; i <= n; i++)
                nodes[i] = new Node(i);

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                String command = st.nextToken();
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                if (command.equals("!")) {
                    int w = Integer.parseInt(st.nextToken());
                    union(nodes, a, b, w);
                } else {
                    if (find(nodes, a) == find(nodes, b)) {
                        long diff = nodes[b].weightDiff - nodes[a].weightDiff;
                        sb.append(diff).append("\n");
                    } else {
                        sb.append(unknown);
                    }
                }
            }
        }

        System.out.println(sb);
    }

    private static int find(Node[] nodes, int x) {
        if (x == nodes[x].parent)
            return x;
        int root = find(nodes, nodes[x].parent);
        nodes[x].weightDiff += nodes[nodes[x].parent].weightDiff;
        return nodes[x].parent = root;
    }

    private static void union(Node[] nodes, int a, int b, int w) {
        int rootA = find(nodes, a);
        int rootB = find(nodes, b);
        if (rootA != rootB) {
            nodes[rootB].parent = rootA;
            nodes[rootB].weightDiff = nodes[a].weightDiff - nodes[b].weightDiff + w;
        }
    }
}

/*
2 2
! 1 2 1
? 1 2  
2 2
! 1 2 1
? 2 1
4 7
! 1 2 100
? 2 3
! 2 3 100
? 2 3
? 1 3
! 4 3 150
? 4 1
0 0

1
-1
UNKNOWN
100
200
-50
*/