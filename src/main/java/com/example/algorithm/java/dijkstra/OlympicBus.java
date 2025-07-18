package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class OlympicBus {
    private static final int N = 207, M = 100007;
    private static final long INF = (long) 1e18;

    private static class GraphState {
        long[] dist = new long[N];
        boolean[] inPath = new boolean[M];
    }

    private static int n, m, edgeCount = 1;
    private static int[] head = new int[N], to = new int[M], next = new int[M];
    private static long[] cost = new long[M], delay = new long[M];
    private static boolean[] isUsed = new boolean[M];
    private static long currentTotal, answer = INF;
    private static GraphState g1 = new GraphState(), gn = new GraphState(), gt1 = new GraphState(),
            gtn = new GraphState();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            addEdge(x, y, c, d);
        }

        currentTotal += dijkstra(1, n, isUsed, g1);
        currentTotal += dijkstra(n, 1, isUsed, gn);
        answer = Math.min(answer, currentTotal);

        flipEdges();
        dijkstra(1, n, isUsed, gt1);
        dijkstra(n, 1, isUsed, gtn);
        flipEdges();

        for (int i = 2; i <= edgeCount; i += 2) {
            answer = Math.min(answer, simulateEdgeRemoval(i));
        }

        System.out.println(answer == INF ? -1 : answer);
    }

    private static void addEdge(int x, int y, int c, int d) {
        to[++edgeCount] = y;
        next[edgeCount] = head[x];
        head[x] = edgeCount;
        cost[edgeCount] = c;
        delay[edgeCount] = d;
        isUsed[edgeCount] = true;

        to[++edgeCount] = x;
        next[edgeCount] = head[y];
        head[y] = edgeCount;
        cost[edgeCount] = c;
        delay[edgeCount] = d;
        isUsed[edgeCount] = false;
    }

    private static long dijkstra(int start, int end, boolean[] used, GraphState state) {
        long[][] minCost = new long[N][N];
        int[][] edgeId = new int[N][N];
        for (int[] row : edgeId)
            Arrays.fill(row, -1);
        for (long[] row : minCost)
            Arrays.fill(row, INF);

        for (int x = 1; x <= n; x++) {
            for (int i = head[x]; i > 0; i = next[i]) {
                if (!used[i])
                    continue;
                int y = to[i];
                if (minCost[x][y] > cost[i]) {
                    minCost[x][y] = cost[i];
                    edgeId[x][y] = i;
                }
            }
        }

        long[] dist = new long[N];
        boolean[] visited = new boolean[N];
        int[] trace = new int[N];
        Arrays.fill(dist, INF);
        Arrays.fill(trace, -1);
        dist[start] = 0;

        for (int i = 1; i <= n; i++) {
            int u = -1;
            for (int j = 1; j <= n; j++) {
                if (!visited[j] && (u == -1 || dist[j] < dist[u])) {
                    u = j;
                }
            }
            if (u == -1)
                break;
            visited[u] = true;

            for (int v = 1; v <= n; v++) {
                if (visited[v])
                    continue;
                if (minCost[u][v] < INF && dist[v] > dist[u] + minCost[u][v]) {
                    dist[v] = dist[u] + minCost[u][v];
                    trace[v] = edgeId[u][v];
                }
            }
        }

        System.arraycopy(dist, 0, state.dist, 0, N);
        Arrays.fill(state.inPath, false);
        for (int v = end; v != start && trace[v] != -1;) {
            state.inPath[trace[v]] = true;
            v = to[trace[v] ^ 1];
        }

        return dist[end];
    }

    private static void flipEdges() {
        for (int i = 2; i <= edgeCount; i++) {
            isUsed[i] ^= true;
        }
    }

    private static long simulateEdgeRemoval(int i) {
        long temp = delay[i];
        isUsed[i] = false;
        isUsed[i ^ 1] = true;

        if (g1.inPath[i]) {
            temp += dijkstra(1, n, isUsed, new GraphState());
        } else {
            long candidate = cost[i] + g1.dist[to[i]];
            if (gtn.inPath[i]) {
                candidate += dijkstra(to[i ^ 1], n, isUsed, new GraphState());
            } else {
                candidate += gtn.dist[to[i ^ 1]];
            }
            temp += Math.min(g1.dist[n], candidate);
        }

        if (gn.inPath[i]) {
            temp += dijkstra(n, 1, isUsed, new GraphState());
        } else {
            long candidate = cost[i] + gn.dist[to[i]];
            if (gt1.inPath[i]) {
                candidate += dijkstra(to[i ^ 1], 1, isUsed, new GraphState());
            } else {
                candidate += gt1.dist[to[i ^ 1]];
            }
            temp += Math.min(gn.dist[1], candidate);
        }

        isUsed[i] = true;
        isUsed[i ^ 1] = false;
        return temp;
    }
}

/*
4 10
1 2 4 4
1 2 4 4
1 3 2 1
1 3 2 1
4 3 1 2
4 3 1 2
4 1 6 1
4 1 6 1
2 4 2 5
2 4 2 5

10


4 5
2 1 4 4
1 3 2 1
4 3 1 2
4 3 6 1
2 4 2 5

-1
*/