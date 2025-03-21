package com.example.algorithm.java.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MobileKeyboard {
    static class Node {
        Node[] children = new Node[26];
        int childCount = 0;
        int wordCount = 0;
    }

    static class Trie {
        private final Node root;
        private int inputCount;

        public Trie() {
            this.root = new Node();
            this.root.childCount = 1;
            this.inputCount = 0;
        }

        void insert(String word) {
            Node node = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    if (node.childCount == 1) {
                        inputCount += node.wordCount;
                    }
                    node.children[index] = new Node();
                    node.childCount++;
                }
                if (node.childCount > 1) {
                    inputCount++;
                }
                node.wordCount++;
                node = node.children[index];
            }
            if (node.childCount == 1) {
                inputCount += node.wordCount;
            }
            node.childCount += 2;
            node.wordCount++;
        }

        int getInputCount() {
            return inputCount;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null && !line.isEmpty()) {
            int n = Integer.parseInt(line);
            Trie trie = new Trie();

            for (int i = 0; i < n; i++) {
                trie.insert(br.readLine());
            }

            double average = (double) trie.getInputCount() / n;
            sb.append(String.format("%.2f", average)).append('\n');
        }
        System.out.print(sb);
    }
}

/*
4
hello
hell 
heaven
goodbye
3
hi
he
h
7
structure
structures
ride
riders
stress
solstice
ridiculous

2.00
1.67
2.71
*/