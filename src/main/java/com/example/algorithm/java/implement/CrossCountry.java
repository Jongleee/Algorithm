package com.example.algorithm.java.implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CrossCountry {
    static int testCases;
    static int numOfPlayers;
    static int[] playerTeam = new int[1001];
    static int[] teamCount = new int[201];
    static int[] teamScore = new int[201];
    static int[] fifthMemberIndex = new int[201];
    static StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        testCases = Integer.parseInt(br.readLine());
        for (int testCase = 0; testCase < testCases; testCase++) {
            resetArrays();
            numOfPlayers = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= numOfPlayers; i++) {
                int teamNumber = Integer.parseInt(st.nextToken());
                playerTeam[i] = teamNumber;
                teamCount[teamNumber]++;
                if (teamCount[teamNumber] == 5) {
                    fifthMemberIndex[teamNumber] = i;
                }
            }
            calculateTeamScores();
            int winner = findWinner();
            result.append(winner).append("\n");
        }
        System.out.println(result);
        br.close();
    }

    static void resetArrays() {
        Arrays.fill(playerTeam, 0);
        Arrays.fill(teamCount, 0);
        Arrays.fill(teamScore, 0);
        Arrays.fill(fifthMemberIndex, 0);
    }

    static void calculateTeamScores() {
        int score = 1;
        for (int i = 1; i <= numOfPlayers; i++) {
            int currentTeam = playerTeam[i];
            if (teamCount[currentTeam] < 6) {
                continue;
            }
            if (fifthMemberIndex[currentTeam] <= i) {
                score++;
                continue;
            }
            teamScore[currentTeam] += score;
            score++;
        }
    }

    static int findWinner() {
        int minScore = Integer.MAX_VALUE;
        int winner = 0;
        for (int i = 1; i <= 200; i++) {
            if (teamCount[i] >= 6 && minScore >= teamScore[i]) {
                if (minScore == teamScore[i] && fifthMemberIndex[winner] < fifthMemberIndex[i]) {
                    continue;
                }
                minScore = teamScore[i];
                winner = i;
            }
        }
        return winner;
    }
}

/*
2
15
1 2 3 3 1 3 2 4 1 1 3 1 3 3 1
18
1 2 3 1 2 3 1 2 3 3 3 3 2 2 2 1 1 1

1
3
 */