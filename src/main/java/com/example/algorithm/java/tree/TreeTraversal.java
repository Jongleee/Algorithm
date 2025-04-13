package com.example.algorithm.java.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TreeTraversal {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int nodeCount = Integer.parseInt(br.readLine());

        Node head = new Node('A');

        for (int i = 0; i < nodeCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            char root = st.nextToken().charAt(0);
            char left = st.nextToken().charAt(0);
            char right = st.nextToken().charAt(0);
            insertNode(head, root, left, right);
        }

        StringBuilder sb = new StringBuilder();
        traversePreOrder(head, sb);
        sb.append('\n');
        traverseInOrder(head, sb);
        sb.append('\n');
        traversePostOrder(head, sb);
        System.out.println(sb);
    }

    private static class Node {
        char value;
        Node left;
        Node right;

        Node(char value) {
            this.value = value;
        }
    }

    private static void insertNode(Node current, char root, char left, char right) {
        if (current == null)
            return;

        if (current.value == root) {
            current.left = (left == '.') ? null : new Node(left);
            current.right = (right == '.') ? null : new Node(right);
        } else {
            insertNode(current.left, root, left, right);
            insertNode(current.right, root, left, right);
        }
    }

    private static void traversePreOrder(Node node, StringBuilder sb) {
        if (node == null)
            return;
        sb.append(node.value);
        traversePreOrder(node.left, sb);
        traversePreOrder(node.right, sb);
    }

    private static void traverseInOrder(Node node, StringBuilder sb) {
        if (node == null)
            return;
        traverseInOrder(node.left, sb);
        sb.append(node.value);
        traverseInOrder(node.right, sb);
    }

    private static void traversePostOrder(Node node, StringBuilder sb) {
        if (node == null)
            return;
        traversePostOrder(node.left, sb);
        traversePostOrder(node.right, sb);
        sb.append(node.value);
    }
}

/*
7
A B C
B D .
C E F
E . .
F . G
D . .
G . .

ABDCEFG
DBAECFG
DBEGFCA
*/