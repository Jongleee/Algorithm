package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ExamSupervisor {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] students = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            students[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        int mainSupervisor = Integer.parseInt(st.nextToken());
        int subSupervisor = Integer.parseInt(st.nextToken());

        System.out.println(calculateSupervisors(students, mainSupervisor, subSupervisor));
    }

    private static long calculateSupervisors(int[] students, int mainSupervisor, int subSupervisor) {
        long totalSupervisors = 0;

        for (int studentsInRoom : students) {
            totalSupervisors += 1;
            int remaining = studentsInRoom - mainSupervisor;
            if (remaining > 0) {
                totalSupervisors += remaining / subSupervisor;
                if (remaining % subSupervisor != 0) {
                    totalSupervisors++;
                }
            }
        }
        return totalSupervisors;
    }
}

/*
5
1000000 1000000 1000000 1000000 1000000
5 7

714290


5
10 9 10 9 10
7 20

10
*/