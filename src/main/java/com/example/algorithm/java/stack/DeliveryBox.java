package com.example.algorithm.java.stack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DeliveryBox {

    public int solution(int[] order) {
        Queue<Integer> belt = new LinkedList<>();
        Stack<Integer> assistance = new Stack<>();

        int answer = 0;
        for (int i = 0; i < order.length; i++) {
            belt.offer(i + 1);
        }
        int temp = 0;
        for (Integer i : order) {
            while (true) {
                if (i < temp) {
                    if (assistance.peek() == i) {
                        temp = assistance.pop();
                        answer += 1;
                        break;
                    } else {
                        return answer;
                    }

                }
                else if (!belt.isEmpty()){
                    if (belt.peek() == i){
                        temp = belt.poll();
                        answer += 1;
                        break;
                    }
                }
            }
        }
        return answer;
    }
}
