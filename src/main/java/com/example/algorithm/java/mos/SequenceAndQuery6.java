package com.example.algorithm.java.mos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SequenceAndQuery6 {
    private static int[] count = new int[100010];
    private static int[] frequency = new int[100010];
    private static int currentAnswer = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int sqrtN = (int) Math.sqrt(n);
        int[] arr = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            arr[i] = Integer.parseInt(st.nextToken());

        int m = Integer.parseInt(br.readLine());
        Query[] queries = new Query[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            queries[i] = new Query(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i, sqrtN);
        }

        int[] results = new int[m];
        processQueries(arr, queries, results);

        StringBuilder sb = new StringBuilder();
        for (int result : results)
            sb.append(result).append('\n');

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    static class Query implements Comparable<Query> {
        int left, right, index, block;

        public Query(int left, int right, int index, int sqrtN) {
            this.left = left;
            this.right = right;
            this.index = index;
            this.block = left / sqrtN;
        }

        @Override
        public int compareTo(Query other) {
            if (this.block == other.block) {
                return Integer.compare(this.right, other.right);
            }
            return Integer.compare(this.block, other.block);
        }
    }

    private static void add(int num) {
        frequency[count[num]]--;
        if (++frequency[++count[num]] == 1 && count[num] > currentAnswer) {
            currentAnswer = count[num];
        }
    }

    private static void remove(int num) {
        if (--frequency[count[num]] == 0 && currentAnswer == count[num]) {
            currentAnswer--;
        }
        frequency[--count[num]]++;
    }

    private static void processQueries(int[] arr, Query[] queries, int[] results) {
        Arrays.sort(queries);

        int left = queries[0].left;
        int right = queries[0].right;
        for (int i = left; i <= right; i++)
            add(arr[i]);
        results[queries[0].index] = currentAnswer;

        for (int i = 1; i < queries.length; i++) {
            updateRange(arr, queries[i], left, right);
            left = queries[i].left;
            right = queries[i].right;
            results[queries[i].index] = currentAnswer;
        }
    }

    private static void updateRange(int[] arr, Query query, int left, int right) {
        for (int i = query.left; i < left; i++)
            add(arr[i]);
        for (int i = right + 1; i <= query.right; i++)
            add(arr[i]);
        for (int i = left; i < query.left; i++)
            remove(arr[i]);
        for (int i = query.right + 1; i <= right; i++)
            remove(arr[i]);
    }
}

/*
5
1 2 1 3 3
3
1 3
2 3
1 5

2
1
2
*/