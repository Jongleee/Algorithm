package com.example.algorithm.java.trie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

public class Legend {
    private static class Trie {
        private final int[][] trieNodes;
        private final boolean[][] isWordEnd;
        private int nodeIndex;

        public Trie() {
            trieNodes = new int[4001200][26];
            isWordEnd = new boolean[4001200][26];
            nodeIndex = 0;
        }

        public void insert(String word) {
            int node = 0;
            for (int i = 0; i < word.length(); i++) {
                int charIndex = word.charAt(i) - 'a';
                if (trieNodes[node][charIndex] == 0) {
                    trieNodes[node][charIndex] = ++nodeIndex;
                }
                if (i == word.length() - 1) {
                    isWordEnd[node][charIndex] = true;
                }
                node = trieNodes[node][charIndex];
            }
        }

        public boolean search(String query, Set<String> nicknameSet) {
            int node = 0;
            for (int i = 0; i < query.length(); i++) {
                int charIndex = query.charAt(i) - 'a';
                if (isWordEnd[node][charIndex] && nicknameSet.contains(query.substring(i + 1))) {
                    return true;
                }
                if (trieNodes[node][charIndex] == 0) {
                    return false;
                }
                node = trieNodes[node][charIndex];
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] input = br.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);

        Trie trie = buildTrie(br, n);
        HashSet<String> nicknameSet = buildNicknameSet(br, m);

        int queryCount = Integer.parseInt(br.readLine());
        processQuery(br, bw, trie, nicknameSet, queryCount);

        bw.flush();
        bw.close();
    }

    private static Trie buildTrie(BufferedReader br, int n) throws IOException {
        Trie trie = new Trie();
        for (int i = 0; i < n; i++) {
            trie.insert(br.readLine());
        }
        return trie;
    }

    private static HashSet<String> buildNicknameSet(BufferedReader br, int m) throws IOException {
        HashSet<String> nicknameSet = new HashSet<>();
        for (int i = 0; i < m; i++) {
            nicknameSet.add(br.readLine());
        }
        return nicknameSet;
    }

    private static void processQuery(BufferedReader br, BufferedWriter bw, Trie trie, HashSet<String> nicknameSet,
            int queryCount) throws IOException {
        while (queryCount-- > 0) {
            String query = br.readLine();
            bw.write(trie.search(query, nicknameSet) ? "Yes\n" : "No\n");
        }
    }
}

/*
4 3
red 
blue
purple
orange
shift
joker
noon
5
redshift
bluejoker
purplenoon
orangeshift
whiteblue

Yes
Yes
Yes
Yes
No
*/