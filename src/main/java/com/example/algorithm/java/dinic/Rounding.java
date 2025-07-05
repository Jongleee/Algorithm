package com.example.algorithm.java.dinic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Rounding {
    static class Edge {
        int to, capacity, lowerBound, initialFlow, flow;
        Edge dual;

        Edge(int to, int capacity, int lowerBound) {
            this.to = to;
            this.capacity = capacity;
            this.lowerBound = lowerBound;
            this.initialFlow = 0;
            this.flow = 0;
        }

        int spare() {
            return capacity - flow;
        }

        void addFlow(int flowAmount) {
            this.flow += flowAmount;
            this.dual.flow -= flowAmount;
        }
    }

    static class Dinic {
        static final int MAX_N = 200;
        static final int MAX_V = 2 * MAX_N * (MAX_N + 1) + 4;
        static final int INF = 1_000_000_000;

        int rowCount, colCount, totalDemand;
        int source = MAX_V - 2;
        int sink = MAX_V - 1;
        int s0 = MAX_V - 4;
        int e0 = MAX_V - 3;

        int[] level = new int[MAX_V];
        int[] work = new int[MAX_V];

        Edge[] rowSumEdges = new Edge[MAX_N];
        Edge[] colSumEdges = new Edge[MAX_N];
        Edge[][] cellEdges = new Edge[MAX_N][MAX_N];

        List<Edge> allEdges = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<Edge>[] adj = new ArrayList[MAX_V];

        Dinic() {
            for (int i = 0; i < MAX_V; i++)
                adj[i] = new ArrayList<>();
        }

        void run(BufferedReader br, BufferedWriter bw) throws IOException {
            initializeGraph(br);
            solveMaxFlow();
            printResult(bw);
        }

        void initializeGraph(BufferedReader br) throws IOException {
            int[][] cell = new int[MAX_N][MAX_N];
            int[][] cellM = new int[MAX_N][MAX_N];
            int[] rowSum = new int[MAX_N];
            int[] rowSumM = new int[MAX_N];
            int[] colSum = new int[MAX_N];
            int[] colSumM = new int[MAX_N];

            StringTokenizer st = new StringTokenizer(br.readLine());
            rowCount = Integer.parseInt(st.nextToken());
            colCount = Integer.parseInt(st.nextToken());

            for (int i = 0; i < rowCount; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < colCount; j++) {
                    String[] parts = st.nextToken().split("\\.");
                    cell[i][j] = Integer.parseInt(parts[0]);
                    cellM[i][j] = Integer.parseInt(parts[1]);
                }
                String[] sumParts = st.nextToken().split("\\.");
                rowSum[i] = Integer.parseInt(sumParts[0]);
                rowSumM[i] = Integer.parseInt(sumParts[1]);
            }

            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < colCount; j++) {
                String[] sumParts = st.nextToken().split("\\.");
                colSum[j] = Integer.parseInt(sumParts[0]);
                colSumM[j] = Integer.parseInt(sumParts[1]);
            }

            for (int i = 0; i < rowCount; i++) {
                rowSumEdges[i] = addEdge(s0, 2 * MAX_N * MAX_N + i, rowSum[i] + (rowSumM[i] > 0 ? 1 : 0), rowSum[i]);
                for (int j = 0; j < colCount; j++) {
                    addEdge(2 * MAX_N * MAX_N + i, (MAX_N * i + j) * 2, INF, 0);
                }
            }

            for (int j = 0; j < colCount; j++) {
                colSumEdges[j] = addEdge(2 * MAX_N * MAX_N + MAX_N + j, e0, colSum[j] + (colSumM[j] > 0 ? 1 : 0),
                        colSum[j]);
                for (int i = 0; i < rowCount; i++) {
                    addEdge((MAX_N * i + j) * 2 + 1, 2 * MAX_N * MAX_N + MAX_N + j, INF, 0);
                }
            }

            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    cellEdges[i][j] = addEdge((MAX_N * i + j) * 2, (MAX_N * i + j) * 2 + 1,
                            cell[i][j] + (cellM[i][j] > 0 ? 1 : 0), cell[i][j]);
                }
            }

            addEdge(e0, s0, INF, 0);

            int[] demand = new int[MAX_V - 2];
            for (Edge e : allEdges) {
                if (e.lowerBound > 0) {
                    demand[e.to] -= e.lowerBound;
                    demand[e.dual.to] += e.lowerBound;
                    e.capacity -= e.lowerBound;
                    e.initialFlow = e.lowerBound;
                    e.lowerBound = 0;
                }
            }

            totalDemand = 0;
            for (int i = 0; i < MAX_V - 2; i++) {
                if (demand[i] < 0)
                    addEdge(source, i, -demand[i], 0);
                else if (demand[i] > 0) {
                    addEdge(i, sink, demand[i], 0);
                    totalDemand += demand[i];
                }
            }
        }

        void solveMaxFlow() {
            while (bfs()) {
                Arrays.fill(work, 0);
                int flow;
                while ((flow = dfs(source, sink, INF)) > 0) {
                    totalDemand -= flow;
                }
            }
        }

        void printResult(BufferedWriter bw) throws IOException {
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    bw.write((cellEdges[i][j].flow + cellEdges[i][j].initialFlow) + " ");
                }
                bw.write((rowSumEdges[i].flow + rowSumEdges[i].initialFlow) + "\n");
            }
            for (int j = 0; j < colCount; j++) {
                bw.write((colSumEdges[j].flow + colSumEdges[j].initialFlow) + " ");
            }
            bw.flush();
        }

        Edge addEdge(int u, int v, int capacity, int lowerBound) {
            Edge e1 = new Edge(v, capacity, lowerBound);
            Edge e2 = new Edge(u, 0, 0);
            e1.dual = e2;
            e2.dual = e1;
            adj[u].add(e1);
            adj[v].add(e2);
            allEdges.add(e1);
            allEdges.add(e2);
            return e1;
        }

        boolean bfs() {
            Arrays.fill(level, -1);
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(source);
            level[source] = 0;
            while (!queue.isEmpty()) {
                int current = queue.poll();
                for (Edge e : adj[current]) {
                    int next = e.to;
                    if (level[next] == -1 && e.spare() > 0) {
                        level[next] = level[current] + 1;
                        queue.offer(next);
                    }
                }
            }
            return level[sink] != -1;
        }

        int dfs(int current, int dest, int flow) {
            if (current == dest)
                return flow;
            for (; work[current] < adj[current].size(); work[current]++) {
                Edge e = adj[current].get(work[current]);
                int next = e.to;
                if (level[next] == level[current] + 1 && e.spare() > 0) {
                    int pushed = dfs(next, dest, Math.min(flow, e.spare()));
                    if (pushed > 0) {
                        e.addFlow(pushed);
                        return pushed;
                    }
                }
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        new Dinic().run(br, bw);
    }
}

/*
3 3
4.3 6.7 7.1 18.1
9.2 3.0 0.2 12.4
4.0 7.7 1.3 13.0
17.5 17.4 8.6

4 7 7 18
9 3 0 12
4 8 1 13
17 18 8


2 3
0.4 0.4 0.4 1.2
0.5 0.5 0.5 1.5
0.9 0.9 0.9

1 0 0 1
0 1 0 1
1 1 0
*/