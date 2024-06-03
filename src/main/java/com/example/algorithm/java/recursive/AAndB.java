package com.example.algorithm.java.recursive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AAndB {
    private static String start;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        start = br.readLine();
        String target = br.readLine();
        System.out.println(canTransform(target) ? 1 : 0);
    }

    private static StringBuilder sb = new StringBuilder();

    private static boolean canTransform(String current) {
        if (current.length() == start.length()) {
            return current.equals(start);
        }

        sb.setLength(0);
        sb.append(current);
        if (sb.charAt(sb.length() - 1) == 'A') {
            sb.deleteCharAt(sb.length() - 1);
            if (canTransform(sb.toString())) {
                return true;
            }
        }

        sb.setLength(0);
        sb.append(current).reverse();
        if (sb.charAt(sb.length() - 1) == 'B') {
            sb.deleteCharAt(sb.length() - 1);

            if (canTransform(sb.toString())) {
                return true;
            }
        }

        return false;
    }
}

/*
A
BABA

1


BAAAAABAA
BAABAAAAAB

1


A
ABBA

0
 */