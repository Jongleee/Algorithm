package com.example.algorithm.java.topologicalSort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class FinalRanking {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int testCases = Integer.parseInt(br.readLine().trim());

        for (int t = 0; t < testCases; t++) {
            int numberOfTeams = Integer.parseInt(br.readLine().trim());

            int[] initialRank = new int[numberOfTeams + 1];
            int[] finalRank = new int[numberOfTeams + 1];
            int[] sortedTeams = new int[numberOfTeams + 1];

            StringTokenizer initialRankingTokenizer = new StringTokenizer(br.readLine().trim());
            for (int teamIndex = 1; teamIndex <= numberOfTeams; teamIndex++) {
                int team = Integer.parseInt(initialRankingTokenizer.nextToken());
                initialRank[team] = teamIndex;
                finalRank[team] = teamIndex;
            }

            int numberOfChanges = Integer.parseInt(br.readLine().trim());

            for (int change = 0; change < numberOfChanges; change++) {
                StringTokenizer changeTokenizer = new StringTokenizer(br.readLine().trim());
                int teamA = Integer.parseInt(changeTokenizer.nextToken());
                int teamB = Integer.parseInt(changeTokenizer.nextToken());

                if (initialRank[teamA] < initialRank[teamB]) {
                    finalRank[teamA]++;
                    finalRank[teamB]--;
                } else {
                    finalRank[teamA]--;
                    finalRank[teamB]++;
                }
            }

            boolean isPossible = true;

            for (int team = 1; team <= numberOfTeams; team++) {
                int position = finalRank[team];
                if (position < 1 || position > numberOfTeams || sortedTeams[position] != 0) {
                    isPossible = false;
                    break;
                }
                sortedTeams[position] = team;
            }

            if (isPossible) {
                for (int position = 1; position <= numberOfTeams; position++) {
                    sb.append(sortedTeams[position]).append(" ");
                }
                sb.append("\n");
            } else {
                sb.append("IMPOSSIBLE\n");
            }
        }

        System.out.println(sb.toString());
    }
}

/*
3
5
5 4 3 2 1
2
2 4
3 4
3
2 3 1
0
4
1 2 3 4
3
1 2
3 4
2 3

5 3 2 4 1 
2 3 1
IMPOSSIBLE
*/