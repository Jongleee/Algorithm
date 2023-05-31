package com.example.algorithm.java.bruteForce;

import java.util.HashSet;

public class FindPrimeNumber {
    static char[] chs;
    static boolean[] visited;
    static HashSet<Integer> set;

    public static int solution(String numbers) {
        int len = numbers.length();
        visited = new boolean[len];
        set = new HashSet<>();

        for (int i = 1; i <= len; i++) {
            chs = new char[i];
            permutation(0, i, len, numbers);
        }

        return set.size();
    }

    public static void permutation(int start, int r, int n, String numbers) {
        if (start == r) {
            if (chs[0] == '0')
                return;
            int num = Integer.parseInt(String.valueOf(chs));
            if (isPrimeNumber(num)) {
                set.add(num);
            }
            return;
        }

        for (int i = 0; i < n; i++) {
            if (visited[i])
                continue;

            visited[i] = true;
            chs[start] = numbers.charAt(i);
            permutation(start + 1, r, n, numbers);
            visited[i] = false;
        }
    }

    public static boolean isPrimeNumber(int num) {
        if (num == 1)
            return false;

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(solution("232"));
    }
}
