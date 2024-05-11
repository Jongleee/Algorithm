package com.example.algorithm.java.implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class KCPC {
    private static final String NEW_LINE = "\n";

    private static class Team implements Comparable<Team> {
        int idx;
        int time;
        int report;
        int score;

        public Team(int idx, int time, int report, int score) {
            this.idx = idx;
            this.time = time;
            this.report = report;
            this.score = score;
        }

        @Override
        public int compareTo(Team s) {
            if (this.score != s.score) {
                return Integer.compare(s.score, this.score);
            }
            if (this.report != s.report) {
                return Integer.compare(this.report, s.report);
            }
            return Integer.compare(this.time, s.time);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            processTestCase(br, sb);
        }

        System.out.print(sb.toString());
    }

    private static void processTestCase(BufferedReader br, StringBuilder sb) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int number = Integer.parseInt(st.nextToken());
        int problem = Integer.parseInt(st.nextToken());
        int id = Integer.parseInt(st.nextToken()) - 1;
        int entry = Integer.parseInt(st.nextToken());

        Team[][] teams = initializeTeams(number, problem);

        int time = 1;
        while (entry-- > 0) {
            updateTeamData(br, teams, time);
            time++;
        }

        PriorityQueue<Team> pq = calculateTeamScores(teams, number, problem);

        int count = findTeamRank(id, pq);

        sb.append(count).append(NEW_LINE);
    }

    private static Team[][] initializeTeams(int number, int problem) {
        Team[][] teams = new Team[number][problem];
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < problem; j++) {
                teams[i][j] = new Team(0, 0, 0, 0);
            }
        }
        return teams;
    }

    private static void updateTeamData(BufferedReader br, Team[][] teams, int time) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int i = Integer.parseInt(st.nextToken()) - 1;
        int j = Integer.parseInt(st.nextToken()) - 1;
        int s = Integer.parseInt(st.nextToken());

        teams[i][j].idx = i;
        teams[i][j].score = Math.max(teams[i][j].score, s);
        teams[i][j].time = time;
        teams[i][j].report++;
    }

    private static PriorityQueue<Team> calculateTeamScores(Team[][] teams, int number, int problem) {
        PriorityQueue<Team> pq = new PriorityQueue<>();
        for (int i = 0; i < number; i++) {
            Team s = new Team(i, 0, 0, 0);
            for (int j = 0; j < problem; j++) {
                s.score += teams[i][j].score;
                s.time = Math.max(s.time, teams[i][j].time);
                s.report += teams[i][j].report;
            }
            pq.offer(s);
        }
        return pq;
    }

    private static int findTeamRank(int id, PriorityQueue<Team> pq) {
        int count = 1;
        while (!pq.isEmpty()) {
            Team current = pq.poll();
            if (current.idx == id) {
                break;
            }
            count++;
        }
        return count;
    }
}

/*
2
3 4 3 5
1 1 30
2 3 30
1 2 40
1 2 20
3 1 70
4 4 1 10
1 1 50
2 1 20
1 1 80
3 1 0
1 2 20
2 2 10
4 3 0
2 1 0
2 2 100
1 4 20

1
2
 */