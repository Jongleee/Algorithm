package com.example.algorithm.java.implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Editor {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Node root = new Node('\0');
        Node cur = root;
        String initialText = br.readLine();
        for (int i = 0; i < initialText.length(); i++) {
            cur = cur.add(initialText.charAt(i));
        }

        int commandCount = Integer.parseInt(br.readLine());
        while (commandCount-- > 0) {
            String command = br.readLine();
            char cmdType = command.charAt(0);
            if (cmdType == 'L') {
                cur = cur.moveLeft();
            } else if (cmdType == 'D') {
                cur = cur.moveRight();
            } else if (cmdType == 'B') {
                cur = cur.del();
            } else if (cmdType == 'P') {
                cur = cur.add(command.charAt(2));
            }
        }

        StringBuffer sb = new StringBuffer();
        for (cur = root.next; cur != null; cur = cur.next) {
            sb.append(cur.c);
        }
        System.out.println(sb.toString());
    }

    static class Node {
        private char c;
        private Node next;
        private Node prev;

        public Node(char c) {
            this.c = c;
            this.next = null;
            this.prev = null;
        }

        public Node add(char c) {
            Node newNode = new Node(c);
            if (this.next != null) {
                newNode.next = this.next;
                this.next.prev = newNode;
            }
            this.next = newNode;
            newNode.prev = this;
            return newNode;
        }

        public Node del() {
            if (this.prev == null) {
                return this;
            }
            if (this.next != null) {
                this.prev.next = this.next;
                this.next.prev = this.prev;
            } else {
                this.prev.next = null;
            }
            return this.prev;
        }

        public Node moveRight() {
            if (this.next != null) {
                return this.next;
            }
            return this;
        }

        public Node moveLeft() {
            if (this.prev != null) {
                return this.prev;
            }
            return this;
        }
    }
}

/*
abcd
3
P x
L
P y

abcdyx


abc
9
L
L
L
L
L
P x
L
B
P y

yxabc


dmih
11
B
B
P x
L
B
B
B
P y
D
D
P z

yxz
 */