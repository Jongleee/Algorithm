package com.example.algorithm.java.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class KAryTree {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        st.nextToken();
        int childSize = Integer.parseInt(st.nextToken());
        int findCount = Integer.parseInt(st.nextToken());

        StringBuilder result = new StringBuilder();
        while (findCount-- > 0) {
            st = new StringTokenizer(br.readLine());
            long node1 = Long.parseLong(st.nextToken());
            long node2 = Long.parseLong(st.nextToken());

            if (childSize == 1) {
                result.append(Math.abs(node1 - node2));
            } else {
                result.append(findDistance(node1, node2, childSize));
            }
            result.append("\n");
        }

        System.out.println(result.toString());
    }

    private static long findDistance(long node1, long node2, int childSize) {
        long distance = 0;
        while (node1 != node2) {
            distance++;
            if (node1 > node2) {
                node1 = getParent(node1, childSize);
            } else {
                node2 = getParent(node2, childSize);
            }
        }
        return distance;
    }

    private static long getParent(long node, int childSize) {
        return (node - 2) / childSize + 1;
    }
}

/*
7 2 3
1 2
2 1
4 7

1
1
4


9 3 3
8 9
5 7
8 4

2
2
3
*/