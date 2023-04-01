package com.example.algorithm.java.queue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class WorkAssignment {
    public static String[] solution(String[][] plans) {
        String[] answer = new String[plans.length];
        Stack<String[]> working = new Stack<>();
        Queue<String[]> wait = new LinkedList<>();
        int index = 0;

        Arrays.sort(plans, (a, b) -> {
            int aStart = Integer.parseInt(a[1].split(":")[0]) * 60 + Integer.parseInt(a[1].split(":")[1]);
            int bStart = Integer.parseInt(b[1].split(":")[0]) * 60 + Integer.parseInt(b[1].split(":")[1]);
            return Integer.compare(aStart, bStart);
        });

        for (String[] s : plans) {
            wait.add(s);
        }

        while (!wait.isEmpty()) {
            working.push(wait.poll());

            if (wait.isEmpty()) {
                while (!working.isEmpty()) {
                    answer[index] = working.pop()[0];
                    index++;
                }
                break;
            }

            int term = calculateTerm(wait.peek()[1], working.peek()[1]);

            while (!working.isEmpty() && term >= Integer.parseInt(working.peek()[2])) {
                answer[index] = working.peek()[0];
                term -= Integer.parseInt(working.pop()[2]);
                index++;
            }

            if (!working.isEmpty()) {
                String[] work = working.pop();
                work[2] = String.valueOf((Integer.parseInt(work[2]) - term));
                working.push(work);
            }
        }

        return answer;
    }

    private static int calculateTerm(String wait, String working) {
        int waitMin = Integer.parseInt(wait.split(":")[0]) * 60 + Integer.parseInt(wait.split(":")[1]);
        int workMin = Integer.parseInt(working.split(":")[0]) * 60 + Integer.parseInt(working.split(":")[1]);
        return waitMin - workMin;
    }

    public static void main(String[] args) {
        String[][] p1 = new String[][] { { "science", "12:40", "50" }, { "music", "12:20", "40" },
                { "history", "14:00", "30" }, { "computer", "12:30", "100" } };

        System.out.println(Arrays.toString(solution(p1))); // "science", "history", "computer", "music"
    }
}
