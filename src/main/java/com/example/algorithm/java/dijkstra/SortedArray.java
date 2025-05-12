package com.example.algorithm.java.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SortedArray {
    static class Info implements Comparable<Info> {
        int dist, num;

        Info(int dist, int num) {
            this.dist = dist;
            this.num = num;
        }

        @Override
        public int compareTo(Info other) {
            return this.dist - other.dist;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] initialArray = readArray(br, n);
        int m = Integer.parseInt(br.readLine());
        int[][] operations = readOperations(br, m);

        Map<Integer, Integer> distance = new HashMap<>();
        int startNum = toInt(initialArray, n);
        distance.put(startNum, 0);

        PriorityQueue<Info> queue = new PriorityQueue<>();
        queue.offer(new Info(0, startNum));

        Arrays.sort(initialArray);
        int targetNum = toInt(initialArray, n);

        while (!queue.isEmpty()) {
            Info current = queue.poll();
            if (current.num == targetNum) {
                break;
            }
            int[] currentArray = toArray(current.num, n);
            for (int[] op : operations) {
                swap(currentArray, op[0], op[1]);
                int nextNum = toInt(currentArray, n);
                int nextCost = current.dist + op[2];
                if (!distance.containsKey(nextNum) || distance.get(nextNum) > nextCost) {
                    distance.put(nextNum, nextCost);
                    queue.offer(new Info(nextCost, nextNum));
                }
                swap(currentArray, op[0], op[1]);
            }
        }

        System.out.println(distance.getOrDefault(targetNum, -1));
    }

    private static int[] readArray(BufferedReader br, int size) throws IOException {
        int[] array = new int[size];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < size; i++) {
            array[i] = Integer.parseInt(st.nextToken()) - 1;
        }
        return array;
    }

    private static int[][] readOperations(BufferedReader br, int count) throws IOException {
        int[][] ops = new int[count][3];
        for (int i = 0; i < count; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            ops[i][0] = Integer.parseInt(st.nextToken()) - 1;
            ops[i][1] = Integer.parseInt(st.nextToken()) - 1;
            ops[i][2] = Integer.parseInt(st.nextToken());
        }
        return ops;
    }

    private static int toInt(int[] array, int length) {
        int value = 0;
        for (int i = 0; i < length; i++) {
            value = value * 10 + array[i];
        }
        return value;
    }

    private static int[] toArray(int number, int length) {
        int[] array = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            array[i] = number % 10;
            number /= 10;
        }
        return array;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }
}

/*
4
1 3 1 3
6      
1 2 3
1 3 3
1 4 3
2 3 3
2 4 1
3 4 1

2


4
1 4 3 2
4
1 2 4
2 3 3
3 4 2
1 4 10

7
*/