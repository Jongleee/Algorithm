package com.example.algorithm.java.heap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MinimumHeap {
    private static int[] heap = new int[100001];
    private static int heapsize = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(br.readLine());
            if (num != 0) {
                offer(num);
            } else {
                sb.append(poll()).append("\n");
            }
        }
        System.out.print(sb);
    }

    public static void offer(int item) {
        heap[++heapsize] = item;
        for (int i = heapsize; i > 1; i /= 2) {
            if (heap[i] < heap[i / 2]) {
                swap(i, i / 2);
            } else {
                break;
            }
        }
    }

    public static int poll() {
        if (heapsize == 0)
            return 0;
        int pollData = heap[1];
        heap[1] = heap[heapsize--];

        int parent = 1;
        int leftChild;
        int rightChild;

        while (parent * 2 <= heapsize) {
            leftChild = parent * 2;
            rightChild = leftChild + 1;

            int minChild = (rightChild <= heapsize && heap[rightChild] < heap[leftChild]) ? rightChild : leftChild;

            if (heap[parent] <= heap[minChild])
                break;

            swap(parent, minChild);
            parent = minChild;
        }
        return pollData;
    }

    public static void swap(int a, int b) {
        int temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }
}

/*
9
0
12345678
1
2
0
0
0
0
32

0
1
2
12345678
0
 */