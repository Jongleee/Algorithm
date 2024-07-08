package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class GaheeAndTower {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st = null;

    static int n;
    static int a;
    static int b;
    static List<Integer> list = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());

        if (a + b - 1 > n) {
            System.out.println(-1);
            return;
        }

        populateList();
        adjustListSize();
        printList();
    }

    private static void populateList() {
        for (int i = 1; i <= a - 1; i++) {
            list.add(i);
        }
        list.add(Math.max(a, b));
        for (int i = b - 1; i >= 1; i--) {
            list.add(i);
        }
    }

    private static void adjustListSize() {
        for (int i = 0; i < n - (a + b - 1); i++) {
            list.add(1, 1);
        }
    }

    private static void printList() {
        for (int num : list) {
            sb.append(num).append(' ');
        }
        System.out.println(sb);
    }
}

/*
3 3 1

1 2 3 


1 1 1

1 
 */