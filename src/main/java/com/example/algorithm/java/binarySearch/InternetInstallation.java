package com.example.algorithm.java.binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class InternetInstallation {
    static class Host implements Comparable<Host> {
        int number, cost;
        Host next;

        public Host(int number, int cost, Host next) {
            this.number = number;
            this.cost = cost;
            this.next = next;
        }

        @Override
        public int compareTo(Host o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        Host[] hosts = new Host[n + 1];
        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            hosts[a] = new Host(b, cost, hosts[a]);
            hosts[b] = new Host(a, cost, hosts[b]);
        }

        int result = findMinCost(n, k, hosts);
        System.out.println(result);
    }

    private static int findMinCost(int n, int k, Host[] hosts) {
        int left = 0, right = 1_000_000, result = -1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (canConnect(n, k, hosts, mid)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    private static boolean canConnect(int n, int k, Host[] hosts, int maxAllowedCost) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;

        PriorityQueue<Host> pq = new PriorityQueue<>();
        pq.offer(new Host(1, 0, null));

        while (!pq.isEmpty()) {
            Host current = pq.poll();
            if (dist[current.number] < current.cost)
                continue;

            for (Host next = hosts[current.number]; next != null; next = next.next) {
                int extraCost = current.cost + (next.cost > maxAllowedCost ? 1 : 0);
                if (dist[next.number] > extraCost) {
                    dist[next.number] = extraCost;
                    pq.offer(new Host(next.number, extraCost, null));
                }
            }
        }
        return dist[n] <= k;
    }
}

/*
5 7 1
1 2 5
3 1 4
2 4 8
3 2 3
5 2 9
3 4 7
4 5 6

4
*/