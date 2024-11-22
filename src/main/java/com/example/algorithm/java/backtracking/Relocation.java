package com.example.algorithm.java.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Relocation {
    static class Road implements Comparable<Road> {
        int city, distance;

        Road(int city, int distance) {
            this.city = city;
            this.distance = distance;
        }

        @Override
        public int compareTo(Road o) {
            return this.distance - o.distance;
        }
    }

    static class Graph {
        int[][] minDistances;
        int[] marketCities;
        boolean[] hasMarket;

        Graph(int[][] minDistances, int[] marketCities, boolean[] hasMarket) {
            this.minDistances = minDistances;
            this.marketCities = marketCities;
            this.hasMarket = hasMarket;
        }
    }

    static class Route {
        int[] path;
        boolean[] visited;

        Route(int marketCount) {
            this.path = new int[marketCount];
            this.visited = new boolean[marketCount];
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int cityCount = Integer.parseInt(st.nextToken());
        int roadCount = Integer.parseInt(st.nextToken());
        int marketCount = Integer.parseInt(st.nextToken());

        boolean[] hasMarket = new boolean[cityCount];
        int[] marketCities = new int[marketCount];
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < cityCount; i++) {
            roads.add(new ArrayList<>());
        }

        for (int i = 0; i < marketCount; i++) {
            int cityId = Integer.parseInt(br.readLine()) - 1;
            hasMarket[cityId] = true;
            marketCities[i] = cityId;
        }

        for (int i = 0; i < roadCount; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int city1 = Integer.parseInt(st.nextToken()) - 1;
            int city2 = Integer.parseInt(st.nextToken()) - 1;
            int distance = Integer.parseInt(st.nextToken());
            roads.get(city1).add(new Road(city2, distance));
            roads.get(city2).add(new Road(city1, distance));
        }

        int[][] minDistances = calculateMinDistances(marketCount, marketCities, cityCount, roads);
        Graph graph = new Graph(minDistances, marketCities, hasMarket);

        Route route = new Route(marketCount);
        int shortestPath = findShortestPath(0, route, graph, cityCount, Integer.MAX_VALUE);
        System.out.println(shortestPath);
    }

    private static int[][] calculateMinDistances(int marketCount, int[] marketCities, int cityCount, List<List<Road>> roads) {
        int[][] minDistances = new int[marketCount][cityCount];
        for (int i = 0; i < marketCount; i++) {
            Arrays.fill(minDistances[i], Integer.MAX_VALUE);
            PriorityQueue<Road> pq = new PriorityQueue<>();
            pq.add(new Road(marketCities[i], 0));
            minDistances[i][marketCities[i]] = 0;

            while (!pq.isEmpty()) {
                Road current = pq.poll();
                for (Road neighbor : roads.get(current.city)) {
                    int newDistance = current.distance + neighbor.distance;
                    if (newDistance < minDistances[i][neighbor.city]) {
                        minDistances[i][neighbor.city] = newDistance;
                        pq.add(new Road(neighbor.city, newDistance));
                    }
                }
            }
        }
        return minDistances;
    }

    private static int findShortestPath(int depth, Route route, Graph graph, int cityCount, int currentMin) {
        if (depth == graph.marketCities.length) {
            return calculatePathDistance(route, graph, cityCount, currentMin);
        }

        for (int i = 0; i < graph.marketCities.length; i++) {
            if (!route.visited[i]) {
                route.visited[i] = true;
                route.path[depth] = i;
                currentMin = Math.min(currentMin, findShortestPath(depth + 1, route, graph, cityCount, currentMin));
                route.visited[i] = false;
            }
        }
        return currentMin;
    }

    private static int calculatePathDistance(Route route, Graph graph, int cityCount, int currentMin) {
        int totalDistance = Integer.MAX_VALUE;

        for (int city = 0; city < cityCount; city++) {
            if (!graph.hasMarket[city]) {
                int roundTripDistance = graph.minDistances[route.path[0]][city] + graph.minDistances[route.path[graph.marketCities.length - 1]][city];
                totalDistance = Math.min(totalDistance, roundTripDistance);
            }
        }

        for (int i = 1; i < graph.marketCities.length; i++) {
            totalDistance += graph.minDistances[route.path[i - 1]][graph.marketCities[route.path[i]]];
        }

        return Math.min(currentMin, totalDistance);
    }
}

/*
5 6 3
1
2
3
1 2 1
1 5 2
3 2 3
3 4 5
4 2 7
4 5 10

12
*/