package com.example.algorithm.java.queue;

import java.util.ArrayDeque;
import java.util.Queue;

public class EqualSumQueues {
    public static int solution(int[] queue1, int[] queue2) {
        Queue<Integer> q1 = new ArrayDeque<>();
        Queue<Integer> q2 = new ArrayDeque<>();
        long sum1 = 0;
        long sum2 = 0;
        long sum;

        for (int tmp : queue1) {
            q1.add(tmp);
            sum1 += tmp;
        }

        for (int tmp : queue2) {
            q2.add(tmp);
            sum2 += tmp;
        }

        sum = sum1 + sum2;
        if (sum % 2 == 1)
            return -1;

        sum /= 2;
        int cnt1 = 0;
        int cnt2 = 0;
        int limit = queue1.length * 2;

        while (cnt1 <= limit && cnt2 <= limit) {
            if (sum1 == sum)
                return cnt1 + cnt2;

            if (sum1 > sum) {
                int temp = q1.poll();
                sum1 -= temp;
                sum2 += temp;
                q2.add(temp);
                cnt1++;
            } else {
                int temp = q2.poll();
                sum2 -= temp;
                sum1 += temp;
                q1.add(temp);
                cnt2++;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[] { 1, 1 }, new int[] { 1, 5 }));
    }
}