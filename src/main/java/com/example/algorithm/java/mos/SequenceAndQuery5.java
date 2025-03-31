package com.example.algorithm.java.mos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SequenceAndQuery5 {
    private static class Query implements Comparable<Query> {
        int from, to, order, factor;

        public Query(int from, int to, int order, int sqrt) {
            this.from = from;
            this.to = to;
            this.order = order;
            this.factor = from / sqrt;
        }

        @Override
        public int compareTo(Query other) {
            if (this.factor == other.factor) {
                return this.to - other.to;
            }
            return this.factor - other.factor;
        }
    }

    private static final int MAX = 1_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine());
        int sqrt = (int) Math.sqrt(n);
        int[] number = new int[n + 1];
        StringTokenizer tokens = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            number[i] = Integer.parseInt(tokens.nextToken());
        }

        int m = Integer.parseInt(br.readLine());
        Query[] queries = new Query[m];
        for (int i = 0; i < m; i++) {
            tokens = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(tokens.nextToken());
            int to = Integer.parseInt(tokens.nextToken());
            queries[i] = new Query(from, to, i, sqrt);
        }

        Arrays.sort(queries);
        int[] queryResults = processQueries(number, queries);

        for (int res : queryResults) {
            sb.append(res).append("\n");
        }
        System.out.print(sb);
    }

    private static int[] processQueries(int[] number, Query[] queries) {
        int[] results = new int[queries.length];
        int[] count = new int[MAX + 1];
        int distinctCount = 0;

        int left = queries[0].from;
        int right = left - 1;

        for (Query query : queries) {
            while (right < query.to)
                distinctCount += add(++right, number, count);
            while (right > query.to)
                distinctCount -= remove(right--, number, count);
            while (left < query.from)
                distinctCount -= remove(left++, number, count);
            while (left > query.from)
                distinctCount += add(--left, number, count);

            results[query.order] = distinctCount;
        }
        return results;
    }

    private static int add(int index, int[] number, int[] count) {
        return (++count[number[index]] == 1) ? 1 : 0;
    }

    private static int remove(int index, int[] number, int[] count) {
        return (--count[number[index]] == 0) ? 1 : 0;
    }
}

/*
5
1 1 2 1 3
3        
1 5
2 4
3 5

3
2
3
*/