package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class KCMTravel {
    static class Ticket {
        int to;
        int cost;
        int time;

        Ticket(int to, int cost, int time) {
            this.to = to;
            this.cost = cost;
            this.time = time;
        }
    }

    static class Airport implements Comparable<Airport> {
        int airportNum;
        int minTime;

        Airport(int airportNum, int minTime) {
            this.airportNum = airportNum;
            this.minTime = minTime;
        }

        @Override
        public int compareTo(Airport other) {
            return this.minTime - other.minTime;
        }
    }

    static int[][] dp;
    static int numOfAirports;
    static int maxCost;
    static final int INF = 93000;
    static List<Ticket>[] flights;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testCases = Integer.parseInt(br.readLine());

        for (int i = 0; i < testCases; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            numOfAirports = Integer.parseInt(st.nextToken());
            maxCost = Integer.parseInt(st.nextToken());
            int numOfTickets = Integer.parseInt(st.nextToken());

            dp = new int[numOfAirports + 1][maxCost + 1];
            for (int j = 2; j <= numOfAirports; j++) {
                Arrays.fill(dp[j], INF);
            }

            flights = new ArrayList[numOfAirports + 1];
            for (int j = 0; j <= numOfAirports; j++) {
                flights[j] = new ArrayList<>();
            }

            for (int j = 0; j < numOfTickets; j++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());
                flights[from].add(new Ticket(to, cost, time));
            }

            findMinTravelTime();

            int result = INF;
            for (int k = 0; k <= maxCost; k++) {
                result = Math.min(result, dp[numOfAirports][k]);
            }

            if (result == INF) {
                bw.write("Poor KCM\n");
            } else {
                bw.write(result + "\n");
            }

        }

        bw.flush();
        bw.close();
    }

    static void findMinTravelTime() {
        PriorityQueue<Airport> queue = new PriorityQueue<>();
        boolean[] visited = new boolean[numOfAirports + 1];

        queue.add(new Airport(1, 0));
        dp[1][0] = 0;

        while (!queue.isEmpty()) {
            Airport currentAirport = queue.poll();

            if (currentAirport.airportNum == numOfAirports)
                return;
            if (visited[currentAirport.airportNum])
                continue;
            visited[currentAirport.airportNum] = true;

            for (Ticket ticket : flights[currentAirport.airportNum]) {
                if (ticket.cost > maxCost)
                    continue;

                int newCost = INF;
                for (int k = 0; k <= maxCost - ticket.cost; k++) {
                    if (dp[currentAirport.airportNum][k] != INF) {
                        if (dp[ticket.to][k + ticket.cost] > dp[currentAirport.airportNum][k] + ticket.time) {
                            dp[ticket.to][k + ticket.cost] = dp[currentAirport.airportNum][k] + ticket.time;
                            newCost = newCost > dp[currentAirport.airportNum][k] + ticket.time
                                    ? dp[currentAirport.airportNum][k] + ticket.time
                                    : newCost;
                        }
                    }
                }
                if (newCost != INF)
                    queue.add(new Airport(ticket.to, newCost));
            }
        }
    }
}

/*
1
3 100 3
1 2 1 1
2 3 1 1
1 3 3 30

2


1
4 10 4
1 2 5 3
2 3 5 4
3 4 1 5
1 3 10 6

Poor KCM
*/