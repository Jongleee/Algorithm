package com.example.algorithm.java.queue;

import java.util.LinkedList;
import java.util.Queue;

public class BridgeTruck {
    public int solution(int bridgeLength, int weight, int[] truckWeights) {
        Queue<Integer> waitingTrucks = new LinkedList<>();
        for (int truckWeight : truckWeights) {
            waitingTrucks.add(truckWeight);
        }

        int elapsedTime = 0;
        int currentBridgeWeight = 0;
        Queue<Integer> trucksOnBridge = new LinkedList<>();
        Queue<Integer> enteredTimes = new LinkedList<>();

        while (!waitingTrucks.isEmpty() || !trucksOnBridge.isEmpty()) {
            elapsedTime++;

            if (!trucksOnBridge.isEmpty() && elapsedTime - enteredTimes.peek() >= bridgeLength) {
                currentBridgeWeight -= trucksOnBridge.poll();
                enteredTimes.poll();
            }

            if (!waitingTrucks.isEmpty() && currentBridgeWeight + waitingTrucks.peek() <= weight) {
                int currentTruckWeight = waitingTrucks.poll();
                currentBridgeWeight += currentTruckWeight;
                trucksOnBridge.add(currentTruckWeight);
                enteredTimes.add(elapsedTime);
            }
        }

        return elapsedTime;
    }

    // @Test
    // void 정답() {
    //     int l1 = 100;
    //     int w1 = 100;
    //     int[] tw1 = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
    //     Assertions.assertEquals(110, solution(l1, w1, tw1));
    //     int l2 = 2;
    //     int w2 = 10;
    //     int[] tw2 = { 7, 4, 5, 6 };
    //     Assertions.assertEquals(8, solution(l2, w2, tw2));
    // }
}
