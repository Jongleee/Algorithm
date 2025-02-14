package com.example.algorithm.java.mos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class SequenceAndQuery4 {
    private static class Query {
        int left, right, order;

        Query(int left, int right, int order) {
            this.left = left;
            this.right = right;
            this.order = order;
        }
    }

    private static int[] a = new int[100_100];
    @SuppressWarnings("unchecked")
    private static Deque<Integer>[] dq = new Deque[100_100];
    private static int[] counter = new int[100_100];
    private static int result = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; ++i) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i <= k; ++i) {
            dq[i] = new ArrayDeque<>();
        }

        int m = Integer.parseInt(br.readLine());
        Query[] queries = new Query[m];
        for (int i = 0; i < m; ++i) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken()) - 1;
            int r = Integer.parseInt(st.nextToken()) - 1;
            queries[i] = new Query(l, r, i);
        }

        int sqrtN = (int) Math.sqrt(n) + 1;
        Arrays.sort(queries, (o1, o2) -> {
            int block1 = o1.left / sqrtN;
            int block2 = o2.left / sqrtN;
            if (block1 != block2)
                return Integer.compare(block1, block2);
            return Integer.compare(o1.right, o2.right);
        });

        int[] answer = getAnswer(m, queries);

        StringBuilder sb = new StringBuilder();
        for (int x : answer) {
            sb.append(x).append("\n");
        }
        System.out.print(sb);
        br.close();
    }

    private static int[] getAnswer(int m, Query[] queries) {
        int[] answer = new int[m];
        int left = queries[0].left;
        int right = queries[0].right;
        for (int i = left; i <= right; ++i) {
            insertRight(i);
        }
        answer[queries[0].order] = result;

        for (int i = 1; i < m; ++i) {
            int l = queries[i].left;
            int r = queries[i].right;
            int order = queries[i].order;
            while (left > l)
                insertLeft(--left);
            while (right < r)
                insertRight(++right);
            while (left < l)
                eraseLeft(left++);
            while (right > r)
                eraseRight(right--);
            while (result > 0 && counter[result] == 0)
                --result;
            answer[order] = result;
        }
        return answer;
    }

    private static void eraseLeft(int i) {
        int value = a[i];
        if (dq[value].size() > 1) {
            int length = getLength(value);
            --counter[length];
        }
        dq[value].removeFirst();
        if (dq[value].size() > 1) {
            int length = getLength(value);
            ++counter[length];
            result = Math.max(result, length);
        }
    }

    private static void eraseRight(int i) {
        int value = a[i];
        if (dq[value].size() > 1) {
            int length = getLength(value);
            --counter[length];
        }
        dq[value].removeLast();
        if (dq[value].size() > 1) {
            int length = getLength(value);
            ++counter[length];
            result = Math.max(result, length);
        }
    }

    private static void insertLeft(int i) {
        int value = a[i];
        if (dq[value].size() > 1) {
            int length = getLength(value);
            --counter[length];
        }
        dq[value].addFirst(i);
        if (dq[value].size() > 1) {
            int length = getLength(value);
            ++counter[length];
            result = Math.max(result, length);
        }
    }

    private static void insertRight(int i) {
        int value = a[i];
        if (dq[value].size() > 1) {
            int length = getLength(value);
            --counter[length];
        }
        dq[value].addLast(i);
        if (dq[value].size() > 1) {
            int length = getLength(value);
            ++counter[length];
            result = Math.max(result, length);
        }
    }

    private static int getLength(int value) {
        if (dq[value].isEmpty()) {
            return 0;
        }
        return Math.abs(dq[value].peekLast() - dq[value].peekFirst());
    }
}

/*
7 7
4 5 6 6 5 7 4
5
6 6
5 6
3 5
3 7
1 7

0
0
1
1
6
*/