package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class PickNumbers {
    static int n;
    static int[] numbers;
    static boolean[] visited;
    static ArrayList<Integer> cycleList;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        n = Integer.parseInt(br.readLine());
        numbers = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            numbers[i] = Integer.parseInt(br.readLine());
        }
        
        cycleList = new ArrayList<>();
        visited = new boolean[n + 1];
        
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(i, i);
                visited[i] = false;
            }
        }
        
        Collections.sort(cycleList);
        StringBuilder sb = new StringBuilder();
        sb.append(cycleList.size()).append("\n");
        for (int num : cycleList) {
            sb.append(num).append("\n");
        }
        
        System.out.print(sb.toString());
        br.close();
    }
    
    static void dfs(int start, int target) {
        if (!visited[numbers[start]]) {
            visited[numbers[start]] = true;
            dfs(numbers[start], target);
            visited[numbers[start]] = false;
        }
        
        if (numbers[start] == target) {
            cycleList.add(target);
        }
    }
}
/*
7
3
1
1
5
5
4
6

3
1
3
5
 */