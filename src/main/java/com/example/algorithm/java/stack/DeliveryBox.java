package com.example.algorithm.java.stack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DeliveryBox {
    public int solution(int[] order) {
        Queue<Integer> belt = new LinkedList<>();
        Stack<Integer> assistance = new Stack<>();

        int cnt = 0;
        for (int i = 0; i < order.length; i++) {
            assistance.add(i + 1);
            while (!assistance.isEmpty()) {
                if (assistance.peek() == order[cnt]) {
                    belt.offer(assistance.pop());
                    cnt++;
                } else {
                    break;
                }

            }
        }
        return belt.size();
    }
}
