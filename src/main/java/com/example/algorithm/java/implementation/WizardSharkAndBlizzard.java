package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class WizardSharkAndBlizzard {
    private static class LinkedNode {
        LinkedNode prev, next;
        int value;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] board = new int[n][n];
        int[][] positions = new int[n * n][2];

        for (int i = 0, x = n / 2, y = n / 2; i < positions.length; i++) {
            positions[i][0] = x;
            positions[i][1] = y;

            if (x > y) {
                if (x < n - 1 - y)
                    x++;
                else
                    y++;
            } else {
                if (y <= n - 1 - x)
                    y--;
                else
                    x--;
            }
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        LinkedNode root = new LinkedNode();
        LinkedNode currentNode = root;

        for (int i = 1; i < positions.length; i++) {
            int x = positions[i][0], y = positions[i][1];
            if (board[x][y] == 0)
                continue;

            LinkedNode newNode = new LinkedNode();
            currentNode.next = newNode;
            newNode.prev = currentNode;
            newNode.value = board[x][y];
            currentNode = newNode;
        }

        int[][] targetPositions = new int[5][n / 2];

        for (int i = 1; i < positions.length; i++) {
            int x = positions[i][0], y = positions[i][1];

            if (x == n / 2) {
                if (y < n / 2)
                    targetPositions[3][n / 2 - y - 1] = i;
                else
                    targetPositions[4][y - n / 2 - 1] = i;
            } else if (y == n / 2) {
                if (x < n / 2)
                    targetPositions[1][n / 2 - x - 1] = i;
                else
                    targetPositions[2][x - n / 2 - 1] = i;
            }
        }

        int totalSum = calculateSum(br, n, m, positions, root, targetPositions);

        System.out.println(totalSum);
    }

    private static int calculateSum(BufferedReader br, int n, int m, int[][] positions, LinkedNode root,
            int[][] targetPositions) throws IOException {
        StringTokenizer st;
        int totalSum = 0;
        Queue<LinkedNode> queue1 = new ArrayDeque<>();
        Queue<LinkedNode> queue2 = new ArrayDeque<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int direction = Integer.parseInt(st.nextToken());
            int distance = Integer.parseInt(st.nextToken());

            makeNode(positions, root, targetPositions, queue1, direction, distance);

            while (!queue1.isEmpty()) {
                totalSum = getSize(totalSum, queue1, queue2);

                processQueue(queue1, queue2);
            }

            getValueCount(n, root);
        }
        return totalSum;
    }

    private static void makeNode(int[][] positions, LinkedNode root, int[][] targetPositions, Queue<LinkedNode> queue1,
            int direction, int distance) {
        LinkedNode currentNode;
        currentNode = root;

        for (int j = 1, count = 0; j < positions.length && count < distance; j++) {
            currentNode = currentNode.next;
            if (currentNode == null)
                break;
            if (j == targetPositions[direction][count]) {
                currentNode.prev.next = currentNode.next;
                if (currentNode.next != null) {
                    queue1.offer(currentNode.next);
                    currentNode.next.prev = currentNode.prev;
                }
                count++;
            }
        }
    }

    private static int getSize(int totalSum, Queue<LinkedNode> queue1, Queue<LinkedNode> queue2) {
        LinkedNode currentNode;
        int size = queue1.size();
        while (size-- > 0) {
            currentNode = queue1.poll();
            if (currentNode.value == 0)
                continue;

            int count = 1;
            LinkedNode left = currentNode;

            while (left.prev.value == currentNode.value) {
                count++;
                left = left.prev;
            }

            LinkedNode right = currentNode;
            while (right.next != null && right.next.value == currentNode.value) {
                count++;
                right = right.next;
            }

            if (count >= 4) {
                LinkedNode temp = left;
                int val = temp.value;
                queue2.offer(left);
                totalSum += temp.value * count;
                while (temp != null && temp.value == val) {
                    temp.value = 0;
                    temp = temp.next;
                }
            }
        }
        return totalSum;
    }

    private static void processQueue(Queue<LinkedNode> queue1, Queue<LinkedNode> queue2) {
        LinkedNode currentNode;
        while (!queue2.isEmpty()) {
            currentNode = queue2.poll();
            LinkedNode left = currentNode;
            while (currentNode.next != null && currentNode.next.value == 0) {
                currentNode = currentNode.next;
            }
            LinkedNode right = currentNode;
            left.prev.next = right.next;
            if (right.next != null) {
                right.next.prev = left.prev;
                queue1.offer(right.next);
            }
        }
    }

    private static void getValueCount(int n, LinkedNode root) {
        LinkedNode currentNode;
        currentNode = root.next;
        LinkedNode previousNode = currentNode;
        int valueCount = 1, nodeCount = 0;

        while (currentNode != null) {
            if (currentNode.next == null || previousNode.value != currentNode.next.value) {
                LinkedNode newNode = new LinkedNode();
                newNode.next = currentNode.next;
                previousNode.next = newNode;
                newNode.prev = previousNode;
                newNode.value = previousNode.value;
                previousNode.value = valueCount;
                nodeCount += 2;

                if (newNode.next == null || nodeCount > n * n - 2) {
                    newNode.next = null;
                    break;
                }
                newNode.next.prev = newNode;
                previousNode = newNode.next;
                currentNode = previousNode;
                valueCount = 1;
            } else {
                valueCount++;
                currentNode = currentNode.next;
            }
        }
    }
}

/*
7 4
1 1 1 2 2 2 3
1 2 2 1 2 2 3
1 3 3 2 3 1 2
1 2 2 0 3 2 2
3 1 2 2 3 2 2
3 1 2 1 1 2 1
3 1 2 2 2 1 1
1 3
2 2
3 1
4 3

39


7 7
1 1 1 2 2 2 3
1 2 2 1 2 2 3
1 3 3 2 3 1 2
1 2 2 0 3 2 2
3 1 2 2 3 2 2
3 1 2 1 1 2 1
3 1 2 2 2 1 1
1 3
2 2
3 1
4 3
1 3
1 1
1 3

62
*/