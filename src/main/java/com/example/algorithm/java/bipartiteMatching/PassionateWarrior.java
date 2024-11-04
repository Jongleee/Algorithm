package com.example.algorithm.java.bipartiteMatching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class PassionateWarrior {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int numPeople = Integer.parseInt(st.nextToken());
        int numTasks = Integer.parseInt(st.nextToken());

        List<Deque<Integer>> taskPreferences = new ArrayList<>();
        for (int i = 0; i <= numPeople; i++) {
            taskPreferences.add(new LinkedList<>());
        }

        int[] taskAssignedTo = new int[numTasks + 1];
        int[] currentTaskOfPerson = new int[numPeople + 1];

        for (int i = 1; i <= numPeople; i++) {
            st = new StringTokenizer(br.readLine());
            st.nextToken();
            while (st.hasMoreTokens()) {
                int task = Integer.parseInt(st.nextToken());
                taskPreferences.get(i).add(task);
            }
        }

        int maxMatches = 0;
        for (int i = 1; i <= numPeople; i++) {
            if (tryAssignTask(i, taskPreferences, taskAssignedTo, currentTaskOfPerson)) {
                maxMatches++;
            }
        }

        System.out.println(maxMatches);
    }

    private static boolean tryAssignTask(int person, List<Deque<Integer>> taskPreferences, int[] taskAssignedTo,
            int[] currentTaskOfPerson) {
        int limit = currentTaskOfPerson[person] == 0 ? 0 : 1;
        while (taskPreferences.get(person).size() > limit) {
            int task = taskPreferences.get(person).pollFirst();

            if (taskAssignedTo[task] == 0
                    || tryAssignTask(taskAssignedTo[task], taskPreferences, taskAssignedTo, currentTaskOfPerson)) {
                taskAssignedTo[task] = person;
                currentTaskOfPerson[person] = task;

                taskPreferences.get(person).addLast(task);
                return true;
            }
        }
        return false;
    }
}

/*
5 5
2 1 2
1 1
2 2 3
3 3 4 5
1 1

4
*/