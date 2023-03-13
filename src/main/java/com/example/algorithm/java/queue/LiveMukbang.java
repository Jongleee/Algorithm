package com.example.algorithm.java.queue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class LiveMukbang {
    public static class Food {
        private final int index;
        private int time;

        public Food(int index, int time) {
            this.index = index;
            this.time = time;
        }

        public int getIndex() {
            return index;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

    public static int solution(int[] foodTimes, long k) {
        Queue<Food> q = new PriorityQueue<>(Comparator.comparingInt(Food::getTime).thenComparingInt(Food::getIndex));

        for (int i = 0; i < foodTimes.length; i++) {
            q.add(new Food(i + 1, foodTimes[i]));
        }

        int prevTime = 0;
        int n = foodTimes.length;

        while (!q.isEmpty()) {
            Food food = q.peek();
            int diff = food.getTime() - prevTime;
            long totalTime = (long) diff * n;

            if (totalTime <= k) {
                k -= totalTime;
                prevTime = food.getTime();
                q.poll();
                n--;
            } else {
                break;
            }
        }

        if (q.isEmpty()) {
            return -1;
        }

        List<Food> remainingFoods = new ArrayList<>();

        while (!q.isEmpty()) {
            remainingFoods.add(q.poll());
        }

        remainingFoods.sort(Comparator.comparingInt(Food::getIndex));

        int index = (int) (k % n);
        return remainingFoods.get(index).getIndex();
    }

    public static void main(String[] args) {
        int[] f1 = { 3, 1, 2 };
        int k1 = 5;
        System.out.println(solution(f1, k1));
    }

}
