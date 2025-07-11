package com.example.algorithm.java.ahoCorasick;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BigPicture {
    private static class Trie {
        private int currentKey = 0;
        private final Node origin = new Node(0);
        private final Node root = new Node(0);

        public int insert(String word) {
            Node node = root;
            for (char ch : word.toCharArray()) {
                int index = (ch == 'o') ? 1 : 0;
                if (node.go[index] == null) {
                    node.go[index] = new Node(0);
                }
                node = node.go[index];
            }
            if (node.key == 0) {
                node.key = ++currentKey;
            }
            return node.key;
        }

        public void buildFailureLinks() {
            for (int i = 0; i < 2; i++) {
                origin.go[i] = root;
            }
            root.fail = origin;

            Queue<Node> queue = new ArrayDeque<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                Node current = queue.poll();
                for (int i = 0; i < 2; i++) {
                    Node next = current.go[i];
                    if (next == null)
                        continue;

                    Node fail = current.fail;
                    while (fail != origin && fail.go[i] == null) {
                        fail = fail.fail;
                    }
                    next.fail = fail.go[i];
                    queue.offer(next);
                }
            }
        }

        public int[] encodeLine(String line) {
            int[] encoded = new int[line.length()];
            Node node = root;
            for (int i = 0; i < line.length(); i++) {
                int index = (line.charAt(i) == 'o') ? 1 : 0;
                while (node != origin && node.go[index] == null) {
                    node = node.fail;
                }
                node = node.go[index];
                encoded[i] = node.key;
            }
            return encoded;
        }
    }

    private static class Node {
        int key;
        Node fail;
        Node[] go;

        public Node(int key) {
            this.key = key;
            this.go = new Node[2];
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int h = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        int mh = Integer.parseInt(st.nextToken());
        int mw = Integer.parseInt(st.nextToken());

        Trie trie = new Trie();
        int[] pattern = new int[h];
        for (int i = 0; i < h; i++) {
            pattern[i] = trie.insert(br.readLine());
        }
        trie.buildFailureLinks();

        int[] kmp = buildKmpTable(pattern, h);

        int[][] textEncoded = new int[mh][];
        for (int i = 0; i < mh; i++) {
            textEncoded[i] = trie.encodeLine(br.readLine());
        }

        int result = countPatternMatches(w, mw, h, mh, pattern, kmp, textEncoded);
        System.out.println(result);
    }

    private static int[] buildKmpTable(int[] pattern, int h) {
        int[] kmp = new int[h + 1];
        kmp[0] = -1;
        for (int i = 0; i < h; i++) {
            int j = kmp[i];
            while (j >= 0 && pattern[j] != pattern[i]) {
                j = kmp[j];
            }
            kmp[i + 1] = j + 1;
        }
        return kmp;
    }

    private static int countPatternMatches(int w, int mw, int h, int mh, int[] pattern, int[] kmp,
            int[][] textEncoded) {
        int count = 0;
        for (int col = w - 1; col < mw; col++) {
            int idx = 0;
            for (int row = 0; row < mh; row++) {
                int key = textEncoded[row][col];
                while (idx >= 0 && pattern[idx] != key) {
                    idx = kmp[idx];
                }
                idx++;
                if (idx == h) {
                    count++;
                    idx = kmp[idx];
                }
            }
        }
        return count;
    }
}

/*
4 4 10 10
oxxo
xoox
xoox
oxxo
xxxxxxoxxo
oxxoooxoox
xooxxxxoox
xooxxxoxxo
oxxoxxxxxx
ooooxxxxxx
xxxoxxoxxo
oooxooxoox
oooxooxoox
xxxoxxoxxo

4
*/