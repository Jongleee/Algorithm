package com.example.algorithm.java.searchDFS;

import java.util.ArrayList;
import java.util.Collections;

public class TripRoute {
    private ArrayList<String> visitRoute;
    private boolean[] visited;

    public String[] solution(String[][] tickets) {
        visitRoute = new ArrayList<>();
        visited = new boolean[tickets.length];
        dfs("ICN", "ICN", tickets, 0);
        Collections.sort(visitRoute);
        return visitRoute.get(0).split(" ");
    }

    private void dfs(String start, String route, String[][] tickets, int cnt) {
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

    // @Test
    // void 정답() {
    //     String[][] tickets1 = { { "ICN", "JFK" }, { "HND", "IAD" }, { "JFK", "HND" } };
    //     String[] routes1 = { "ICN", "JFK", "HND", "IAD" };

    //     String[][] tickets2 = { { "ICN", "SFO" }, { "ICN", "ATL" }, { "SFO", "ATL" }, { "ATL", "ICN" },{ "ATL", "SFO" } };
    //     String[] routes2 = { "ICN", "ATL", "ICN", "SFO", "ATL", "SFO" };

    //     Assertions.assertArrayEquals(routes1, solution(tickets1));
    //     Assertions.assertArrayEquals(routes2, solution(tickets2));
    // }
}
