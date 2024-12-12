package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DangerousDive {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int[] input = readInput(br);
        int n = input[0];
        int r = input[1];

        if (n == r) {
            sb.append("*");
        } else {
            boolean[] returned = markReturnedPeople(br, r, n);
            listMissingPeople(sb, returned);
        }
        System.out.print(sb);
    }

    private static int[] readInput(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        return new int[] { n, r };
    }

    private static boolean[] markReturnedPeople(BufferedReader br, int r, int n) throws IOException {
        boolean[] returned = new boolean[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < r; i++) {
            int person = Integer.parseInt(st.nextToken());
            returned[person] = true;
        }
        return returned;
    }

    private static void listMissingPeople(StringBuilder sb, boolean[] returned) {
        for (int i = 1; i < returned.length; i++) {
            if (!returned[i]) {
                sb.append(i).append(" ");
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
    }
}

/*
5 3
3 1 5

2 4


6 6
6 1 3 2 5 4

*
*/