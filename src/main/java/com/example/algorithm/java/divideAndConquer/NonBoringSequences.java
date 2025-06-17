package com.example.algorithm.java.divideAndConquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class NonBoringSequences {
    private static final int MAX_N = 200_000;
    private static final String BORING = "boring\n";
    private static final String NON_BORING = "non-boring\n";

    private static int[] left = new int[MAX_N];
    private static int[] right = new int[MAX_N];
    private static HashMap<Integer, Integer> map = new HashMap<>();
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            sb.append(solve() ? NON_BORING : BORING);
        }
        System.out.print(sb);
    }

    private static boolean solve() throws IOException {
        int n = Integer.parseInt(br.readLine());
        map.clear();
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            if (map.containsKey(num)) {
                left[i] = map.get(num);
                map.put(num, i);
            } else {
                left[i] = -1;
                map.put(num, i);
            }
        }

        for (int i = 0; i < n; i++) {
            right[i] = n;
            if (left[i] != -1) {
                right[left[i]] = i;
            }
        }

        return isNonBoring(0, n - 1);
    }

    private static boolean isNonBoring(int l, int r) {
        if (l >= r)
            return true;
        for (int i = l, j = r; i <= j; i++, j--) {
            if (left[i] < l && right[i] > r) {
                return isNonBoring(l, i - 1) && isNonBoring(i + 1, r);
            }
            if (left[j] < l && right[j] > r) {
                return isNonBoring(l, j - 1) && isNonBoring(j + 1, r);
            }
        }
        return false;
    }
}

/*
4
5
1 2 3 4 5
5
1 1 1 1 1
5
1 2 3 2 1
5
1 1 2 1 1

non-boring
boring
non-boring
boring
*/