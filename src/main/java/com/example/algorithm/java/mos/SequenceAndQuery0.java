package com.example.algorithm.java.mos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SequenceAndQuery0 {
    static final int SQRT = 400;
    static final int MAX_N = 100000;

    static class Query implements Comparable<Query> {
        int start, end, index;

        Query(int start, int end, int index) {
            this.start = start;
            this.end = end;
            this.index = index;
        }

        @Override
        public int compareTo(Query other) {
            if (this.start / SQRT != other.start / SQRT) {
                return this.start - other.start;
            }
            return this.end - other.end;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] prefixSum = new int[n + 1];
        int[] raw = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            raw[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= n; i++) {
            prefixSum[i] = raw[i] + prefixSum[i - 1];
        }

        for (int i = 0; i <= n; i++) {
            prefixSum[i] += MAX_N;
        }

        List<Integer>[] positions = createPositionLists();
        int[] count = new int[MAX_N + 1];
        int[] bucket = new int[(MAX_N / SQRT) + 10];

        int q = Integer.parseInt(br.readLine());
        int[] answers = new int[q];
        List<Query> queries = new ArrayList<>();

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken()) - 1;
            int end = Integer.parseInt(st.nextToken());
            queries.add(new Query(start, end, i));
        }

        queries.sort(null);
        int s = queries.get(0).start;
        int e = queries.get(0).end;
        int x = queries.get(0).index;

        for (int i = s; i <= e; i++) {
            add(i, 1, prefixSum, positions, count, bucket);
        }
        answers[x] = getMaxInterval(count, bucket);

        for (int i = 1; i < q; i++) {
            Query query = queries.get(i);
            x = query.index;

            while (query.start < s)
                add(--s, 0, prefixSum, positions, count, bucket);
            while (e < query.end)
                add(++e, 1, prefixSum, positions, count, bucket);
            while (s < query.start)
                remove(s++, 0, prefixSum, positions, count, bucket);
            while (query.end < e)
                remove(e--, 1, prefixSum, positions, count, bucket);

            answers[x] = getMaxInterval(count, bucket);
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int ans : answers) {
            bw.write(ans + "\n");
        }
        bw.flush();
    }

    @SuppressWarnings("unchecked")
    private static List<Integer>[] createPositionLists() {
        List<Integer>[] pos = new ArrayList[MAX_N * 2 + 1];
        for (int i = 0; i < pos.length; i++) {
            pos[i] = new ArrayList<>();
        }
        return pos;
    }

    private static void add(int idx, int dir, int[] prefixSum, List<Integer>[] pos, int[] count, int[] bucket) {
        int value = prefixSum[idx];
        List<Integer> list = pos[value];

        if (!list.isEmpty()) {
            int len = list.get(list.size() - 1) - list.get(0);
            count[len]--;
            bucket[len / SQRT]--;
        }

        if (dir == 0)
            list.add(0, idx);
        else
            list.add(idx);

        int len = list.get(list.size() - 1) - list.get(0);
        count[len]++;
        bucket[len / SQRT]++;
    }

    private static void remove(int idx, int dir, int[] prefixSum, List<Integer>[] pos, int[] count, int[] bucket) {
        int value = prefixSum[idx];
        List<Integer> list = pos[value];

        int len = list.get(list.size() - 1) - list.get(0);
        count[len]--;
        bucket[len / SQRT]--;

        if (dir == 0)
            list.remove(0);
        else
            list.remove(list.size() - 1);

        if (!list.isEmpty()) {
            len = list.get(list.size() - 1) - list.get(0);
            count[len]++;
            bucket[len / SQRT]++;
        }
    }

    private static int getMaxInterval(int[] count, int[] bucket) {
        for (int i = bucket.length - 1; i >= 0; i--) {
            if (bucket[i] == 0)
                continue;
            for (int j = SQRT - 1; j >= 0; j--) {
                int index = i * SQRT + j;
                if (index < count.length && count[index] > 0)
                    return index;
            }
        }
        return 0;
    }
}

/*
6
1 1 1 -1 -1 -1
4
1 3
1 4
1 5
1 6

0
2
4
6
*/