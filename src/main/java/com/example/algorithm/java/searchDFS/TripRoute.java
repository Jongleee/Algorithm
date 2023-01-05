package com.example.algorithm.java.searchDFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TripRoute {
    static ArrayList<String> visitRoute = new ArrayList<>();
    static boolean[] visited;

    public static String[] solution(String[][] tickets) {
        int cnt = 0;
        visited = new boolean[tickets.length];
        dfs("ICN", "ICN", tickets, cnt);
        Collections.sort(visitRoute);
        return visitRoute.get(0).split(" ");
    }

    public static void dfs(String start, String route, String[][] tickets, int cnt) {
        if (cnt == tickets.length) {
            visitRoute.add(route);
            return;
        }
        for (int i = 0; i < tickets.length; i++) {
            if (start.equals(tickets[i][0]) && !visited[i]) {
                visited[i] = true;
                dfs(tickets[i][1], route + " " + tickets[i][1], tickets, cnt + 1);
                visited[i] = false;
            }
        }
    }
    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new String[][]{{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL","SFO"}})));
        }
}
