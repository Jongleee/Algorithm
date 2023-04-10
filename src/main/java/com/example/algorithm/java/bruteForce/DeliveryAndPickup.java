package com.example.algorithm.java.bruteForce;

public class DeliveryAndPickup {
    public static long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;
        int deliveryLeft = 0;
        int pickupLeft = 0;

        for (int i = n - 1; i >= 0; i--) {
            deliveryLeft -= deliveries[i];
            pickupLeft -= pickups[i];
            int cnt = 0;
            while (deliveryLeft < 0 || pickupLeft < 0) {
                deliveryLeft += cap;
                pickupLeft += cap;
                cnt += 1;
            }
            answer += (i + 1) * 2 * cnt;
        }

        return answer;
    }

    public static void main(String[] args) {
        int c1 = 4;
        int n1 = 5;
        int[] d1 = { 1, 0, 3, 1, 2 };
        int[] p1 = { 0, 3, 0, 4, 0 };
        int c2 = 2;
        int n2 = 7;
        int[] d2 = { 1, 0, 2, 0, 1, 0, 2 };
        int[] p2 = { 0, 2, 0, 1, 0, 2, 0 };

        System.out.println(solution(c1, n1, d1, p1));// 16
        System.out.println(solution(c2, n2, d2, p2));// 30
    }
}
