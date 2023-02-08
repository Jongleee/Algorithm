package com.example.algorithm.java.example;

import java.util.Deque;
import java.util.LinkedList;

public class SlidingWindow {
    public int findMaxValue(int[] nums, int k) {
        int maxValue = Integer.MIN_VALUE;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && deque.peek() < i - k + 1) {
                deque.poll();
            }
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            deque.offer(i);
            if (i >= k - 1) {
                maxValue = Math.max(maxValue, nums[deque.peek()]);
            }
        }
        return maxValue;
    }
}
