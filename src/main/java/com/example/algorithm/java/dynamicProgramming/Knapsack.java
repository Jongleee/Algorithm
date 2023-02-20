package com.example.algorithm.java.dynamicProgramming;

public class Knapsack {

    public static int knapsack(int capacity, int[] itemWeights, int[] itemValues, int numItems) {
        int[][] maxValues = new int[numItems + 1][capacity + 1];

        for (int i = 0; i <= numItems; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (i == 0 || w == 0) {
                    maxValues[i][w] = 0;
                } else if (itemWeights[i - 1] <= w) {
                    int valueIfIncluded = itemValues[i - 1] + maxValues[i - 1][w - itemWeights[i - 1]];
                    int valueIfExcluded = maxValues[i - 1][w];
                    maxValues[i][w] = Math.max(valueIfIncluded, valueIfExcluded);
                } else {
                    maxValues[i][w] = maxValues[i - 1][w];
                }
            }
        }

        return maxValues[numItems][capacity];
    }

    public static void main(String[] args) {
        int[] val = new int[] { 60, 100, 120 };
        int[] weights = new int[] { 10, 20, 30 };
        int capacity = 50;
        int n = val.length;
        System.out.println(knapsack(capacity, weights, val, n));
    }
}