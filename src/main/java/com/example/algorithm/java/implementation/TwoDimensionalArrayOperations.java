package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class TwoDimensionalArrayOperations {
    private static int r, c, k;
    private static int rowSize = 3, colSize = 3;
    private static int[][] map = new int[100][100];

    static class Node implements Comparable<Node> {
        int count, number;

        Node(int count, int number) {
            this.count = count;
            this.number = number;
        }

        @Override
        public int compareTo(Node o) {
            if (count == o.count) {
                return Integer.compare(number, o.number);
            }
            return Integer.compare(count, o.count);
        }
    }

    public static void main(String[] args) throws IOException {
        initializeInput();

        int result = performOperations();
        System.out.println(result);
    }

    private static void initializeInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        r = Integer.parseInt(tokenizer.nextToken());
        c = Integer.parseInt(tokenizer.nextToken());
        k = Integer.parseInt(tokenizer.nextToken());

        for (int i = 0; i < 3; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < 3; j++) {
                map[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }
    }

    private static int performOperations() {
        for (int tryCount = 0; tryCount <= 100; tryCount++) {
            if (map[r - 1][c - 1] == k) {
                return tryCount;
            }

            if (rowSize >= colSize) {
                processRows();
            } else {
                processColumns();
            }
        }
        return -1;
    }

    private static void processRows() {
        for (int i = 0; i < rowSize; i++) {
            processRow(i);
        }
    }

    private static void processColumns() {
        for (int i = 0; i < colSize; i++) {
            processColumn(i);
        }
    }

    private static void processRow(int row) {
        PriorityQueue<Node> priorityQueue = calculateFrequency(map[row], colSize);
        int index = updateRowFromQueue(row, priorityQueue);
        clearRemainingRow(row, index);
    }

    private static void processColumn(int column) {
        int[] columnData = extractColumnData(column);
        PriorityQueue<Node> priorityQueue = calculateFrequency(columnData, rowSize);
        int index = updateColumnFromQueue(column, priorityQueue);
        clearRemainingColumn(column, index);
    }

    private static PriorityQueue<Node> calculateFrequency(int[] data, int size) {
        int[] count = new int[101];
        for (int i = 0; i < size; i++) {
            if (data[i] > 0) {
                count[data[i]]++;
            }
        }

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (int i = 1; i <= 100; i++) {
            if (count[i] > 0) {
                priorityQueue.offer(new Node(count[i], i));
            }
        }

        return priorityQueue;
    }

    private static int updateRowFromQueue(int row, PriorityQueue<Node> priorityQueue) {
        int index = 0;
        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();
            map[row][index++] = node.number;
            map[row][index++] = node.count;
        }
        colSize = Math.max(colSize, index);
        return index;
    }

    private static int updateColumnFromQueue(int column, PriorityQueue<Node> priorityQueue) {
        int index = 0;
        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();
            map[index++][column] = node.number;
            map[index++][column] = node.count;
        }
        rowSize = Math.max(rowSize, index);
        return index;
    }

    private static void clearRemainingRow(int row, int startIndex) {
        for (int i = startIndex; i < 100; i++) {
            map[row][i] = 0;
        }
    }

    private static void clearRemainingColumn(int column, int startIndex) {
        for (int i = startIndex; i < 100; i++) {
            map[i][column] = 0;
        }
    }

    private static int[] extractColumnData(int column) {
        int[] columnData = new int[rowSize];
        for (int i = 0; i < rowSize; i++) {
            columnData[i] = map[i][column];
        }
        return columnData;
    }
}

/*
3 3 3
1 1 1
1 1 1
1 1 1

2


1 2 4
1 2 1
2 1 3
3 3 3

52
*/