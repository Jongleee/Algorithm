package com.example.algorithm;

import java.util.ArrayList;
import java.util.Collections;

public class TripRoute {
    ArrayList<String> visitRoute = new ArrayList<>();
    boolean[] visited;

    public String[] solution(String[][] tickets) {
//        tickets=new String[][]{{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL","SFO"}};
        String[] answer = {};
        int cnt = 0;
        visited = new boolean[tickets.length];
        dfs("ICN", "ICN", tickets, cnt);
        Collections.sort(visitRoute);
        return visitRoute.get(0).split(" ");
    }

    public void dfs(String start, String route, String[][] tickets, int cnt) {
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
}
