package com.example.algorithm.java.bipartiteMatching;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CatAndDog {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int testCase = Integer.parseInt(br.readLine());

        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            st.nextToken();
            st.nextToken();
            int voteCount = Integer.parseInt(st.nextToken());

            ArrayList<int[]> catVotes = new ArrayList<>();
            ArrayList<int[]> dogVotes = new ArrayList<>();

            for (int i = 0; i < voteCount; i++) {
                st = new StringTokenizer(br.readLine());
                String first = st.nextToken();
                String second = st.nextToken();
                int like = Integer.parseInt(first.substring(1));
                int hate = Integer.parseInt(second.substring(1));
                if (first.charAt(0) == 'C') {
                    catVotes.add(new int[] { like, hate });
                } else {
                    dogVotes.add(new int[] { like, hate });
                }
            }

            ArrayList<ArrayList<Integer>> adjList = buildGraph(catVotes, dogVotes);

            int[] match = new int[dogVotes.size()];
            Arrays.fill(match, -1);
            boolean[] visited = new boolean[catVotes.size()];

            int maxMatch = 0;
            for (int i = 0; i < catVotes.size(); i++) {
                Arrays.fill(visited, false);
                if (isMatched(i, adjList, match, visited)) {
                    maxMatch++;
                }
            }

            sb.append(voteCount - maxMatch).append('\n');
        }

        bw.write(sb.toString());
        bw.flush();
    }

    private static ArrayList<ArrayList<Integer>> buildGraph(ArrayList<int[]> catVotes, ArrayList<int[]> dogVotes) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < catVotes.size(); i++) {
            graph.add(new ArrayList<>());
            for (int j = 0; j < dogVotes.size(); j++) {
                int[] cat = catVotes.get(i);
                int[] dog = dogVotes.get(j);
                if (cat[0] == dog[1] || cat[1] == dog[0]) {
                    graph.get(i).add(j);
                }
            }
        }
        return graph;
    }

    private static boolean isMatched(int x, ArrayList<ArrayList<Integer>> adjList, int[] match, boolean[] visited) {
        visited[x] = true;
        for (int y : adjList.get(x)) {
            if (match[y] == -1 || (!visited[match[y]] && isMatched(match[y], adjList, match, visited))) {
                match[y] = x;
                return true;
            }
        }
        return false;
    }
}

/*
2
1 1 2
C1 D1
D1 C1
1 2 4
C1 D1
C1 D1
C1 D2
D2 C1

1
3
*/