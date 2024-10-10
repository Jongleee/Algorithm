package com.example.algorithm.java.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class AntColony {
    private static class Node implements Comparable<Node> {
        private final String name;
        private final List<Node> children;

        public Node(String name) {
            this.name = name;
            this.children = new ArrayList<>();
        }

        @Override
        public int compareTo(Node other) {
            return this.name.compareTo(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numberOfEntries = Integer.parseInt(br.readLine());
        Node root = new Node("root");
        StringBuilder resultBuilder = new StringBuilder();

        for (int i = 0; i < numberOfEntries; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int pathLength = Integer.parseInt(st.nextToken());
            String[] path = new String[pathLength];
            for (int j = 0; j < pathLength; j++) {
                path[j] = st.nextToken();
            }
            buildTree(root, path, 0);
        }

        generateOutput(root, 0, resultBuilder);
        System.out.print(resultBuilder);
    }

    private static void buildTree(Node currentNode, String[] path, int depth) {
        if (depth == path.length) {
            return;
        }

        String currentName = path[depth];
        Node childNode = findChild(currentNode.children, currentName);

        if (childNode == null) {
            childNode = new Node(currentName);
            currentNode.children.add(childNode);
        }

        buildTree(childNode, path, depth + 1);
    }

    private static Node findChild(List<Node> children, String name) {
        for (Node child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    private static void generateOutput(Node node, int depth, StringBuilder sb) {
        if (!node.name.equals("root")) {
            for (int i = 1; i < depth; i++) {
                sb.append("--");
            }
            sb.append(node.name).append("\n");
        }

        Collections.sort(node.children);
        for (Node child : node.children) {
            generateOutput(child, depth + 1, sb);
        }
    }
}

/*
3
2 B A
4 A B C D
2 A C

A
--B
----C
------D
--C
B
--A


4
2 KIWI BANANA
2 KIWI APPLE
2 APPLE APPLE
3 APPLE BANANA KIWI

APPLE
--APPLE
--BANANA
----KIWI
KIWI
--APPLE
--BANANA
*/