package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class ShortestPathAndQuery {
    static class Query {
        int n1, m1, n2, m2;
        long answer = Long.MAX_VALUE;

        Query(int a, int b, int c, int d) {
            n1 = a - 1;
            m1 = b - 1;
            n2 = c - 1;
            m2 = d - 1;
        }
    }

    static class Pair {
        int x, y;
        long weight;

        Pair(int x, int y, long weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        final int[] dx = { -1, 1, 0, 0 };
        final int[] dy = { 0, 0, -1, 1 };

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int rowCount = Integer.parseInt(st.nextToken());
        int colCount = Integer.parseInt(st.nextToken());

        long[][] value = new long[rowCount][colCount];
        long[][] dp = new long[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < colCount; j++) {
                value[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int queryCount = Integer.parseInt(br.readLine());
        List<Query> queries = new ArrayList<>(queryCount);
        for (int i = 0; i < queryCount; i++) {
            st = new StringTokenizer(br.readLine());
            queries.add(new Query(
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())));
        }

        divideAndConquer(0, colCount - 1, queries, rowCount, colCount, value, dp, dx, dy);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (Query q : queries) {
            bw.write(q.answer + "\n");
        }
        bw.flush();
    }

    private static void divideAndConquer(int left, int right, List<Query> queries,
            int rowCount, int colCount,
            long[][] value, long[][] dp,
            int[] dx, int[] dy) {
        if (queries.isEmpty() || left > right)
            return;

        int mid = (left + right) >> 1;
        List<Query> leftQueries = new ArrayList<>();
        List<Query> rightQueries = new ArrayList<>();

        for (Query q : queries) {
            if (q.m1 < mid && q.m2 < mid)
                leftQueries.add(q);
            else if (q.m1 > mid && q.m2 > mid)
                rightQueries.add(q);
        }

        for (int i = 0; i < rowCount; i++) {
            dijkstra(i, mid, left, right, rowCount, value, dp, dx, dy);
            for (Query q : queries) {
                q.answer = Math.min(q.answer,
                        dp[q.n1][q.m1] + dp[q.n2][q.m2] + value[i][mid]);
            }
        }

        divideAndConquer(left, mid, leftQueries, rowCount, colCount, value, dp, dx, dy);
        divideAndConquer(mid + 1, right, rightQueries, rowCount, colCount, value, dp, dx, dy);
    }

    private static void dijkstra(int sx, int sy, int left, int right, int rowCount,
            long[][] value, long[][] dp, int[] dx, int[] dy) {
        for (int i = 0; i < rowCount; i++) {
            Arrays.fill(dp[i], left, right + 1, Long.MAX_VALUE);
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingLong(p -> p.weight));
        dp[sx][sy] = 0;
        pq.offer(new Pair(sx, sy, 0));

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();
            if (dp[cur.x][cur.y] < cur.weight)
                continue;

            for (int dir = 0; dir < 4; dir++) {
                int nx = cur.x + dx[dir];
                int ny = cur.y + dy[dir];

                if (nx < 0 || nx >= rowCount || ny < left || ny > right)
                    continue;

                long nextCost = cur.weight + value[nx][ny];
                if (dp[nx][ny] > nextCost) {
                    dp[nx][ny] = nextCost;
                    pq.offer(new Pair(nx, ny, nextCost));
                }
            }
        }
    }
}

/*
3 5
1 3 2 4 5
1 1 2 3 1
5 4 3 2 1
3
1 1 3 5
2 2 3 3
3 3 3 3

10
6
3
*/