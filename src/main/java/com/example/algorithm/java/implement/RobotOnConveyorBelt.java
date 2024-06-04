package com.example.algorithm.java.implement;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RobotOnConveyorBelt {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        int n = Integer.parseInt(input[0]);
        int k = Integer.parseInt(input[1]);

        int beltLength = n * 2;
        boolean[] hasRobot = new boolean[beltLength];
        int[] durability = new int[beltLength];

        input = br.readLine().split(" ");
        for (int i = 0; i < beltLength; i++) {
            durability[i] = Integer.parseInt(input[i]);
        }

        int steps = 0;
        int start = 0;
        int end = n - 1;
        int lastIndex = beltLength - 1;
        int current;
        int next;

        while (k > 0) {
            start = (start - 1 < 0) ? lastIndex : start - 1;
            end = (end - 1 < 0) ? lastIndex : end - 1;

            current = end;
            hasRobot[end] = false;
            while (true) {
                next = current;
                current = (current - 1 < 0) ? lastIndex : current - 1;

                if (hasRobot[current] && !hasRobot[next] && durability[next] > 0) {
                    hasRobot[current] = false;
                    if (end != next)
                        hasRobot[next] = true;

                    if (--durability[next] == 0)
                        k--;
                }

                if (current == start)
                    break;
            }

            if (durability[start]-- > 0) {
                if (durability[start] == 0)
                    k--;

                hasRobot[start] = true;
            }
            steps++;
        }

        System.out.println(steps);
    }
}

/*
3 2
1 2 1 2 1 2

2


3 6
10 10 10 10 10 10

31


4 5
10 1 10 6 3 4 8 2

24


5 8
100 99 60 80 30 20 10 89 99 100

472
 */