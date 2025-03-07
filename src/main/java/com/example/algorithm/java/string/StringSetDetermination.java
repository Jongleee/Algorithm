package com.example.algorithm.java.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class StringSetDetermination {
    static class TrieNode {
        boolean output;
        Map<Character, TrieNode> children = new HashMap<>();
        TrieNode fail;

        void insert(String word) {
            TrieNode current = this;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                current.children.putIfAbsent(c, new TrieNode());
                current = current.children.get(c);
            }
            current.output = true;
        }

        void computeFailFunction() {
            Queue<TrieNode> queue = new LinkedList<>();
            this.fail = this;
            queue.add(this);

            while (!queue.isEmpty()) {
                TrieNode current = queue.poll();

                for (char c = 'a'; c <= 'z'; c++) {
                    TrieNode next = current.children.get(c);
                    if (next == null)
                        continue;

                    if (current == this) {
                        next.fail = this;
                    } else {
                        getFailLink(current, c, next);
                    }

                    if (next.fail.output) {
                        next.output = true;
                    }
                    queue.add(next);
                }
            }
        }

        boolean ahoCorasick(String text) {
            TrieNode current = this;
            for (char c : text.toCharArray()) {
                while (current != this && current.children.get(c) == null) {
                    current = current.fail;
                }
                current = current.children.getOrDefault(c, this);
                if (current.output)
                    return true;
            }
            return false;
        }

        private void getFailLink(TrieNode current, char c, TrieNode next) {
            TrieNode failLink = current.fail;
            while (failLink != this && failLink.children.get(c) == null) {
                failLink = failLink.fail;
            }
            next.fail = failLink.children.getOrDefault(c, this);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        TrieNode trie = new TrieNode();

        for (int i = 0; i < n; i++) {
            trie.insert(br.readLine());
        }

        trie.computeFailFunction();

        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            sb.append(trie.ahoCorasick(br.readLine()) ? "YES\n" : "NO\n");
        }

        System.out.print(sb);
    }
}

/*
3
www
woo
jun
3
myungwoo
hongjun
dooho

YES
YES
NO
*/