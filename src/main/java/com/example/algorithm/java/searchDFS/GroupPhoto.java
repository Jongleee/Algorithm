package com.example.algorithm.java.searchDFS;

import java.util.HashMap;

public class GroupPhoto {
    static String[] people;
    static HashMap<Character, Integer> map;
    static boolean[] visited;
    static int[] positions;
    static int count;

    public static int solution(int n, String[] data) {
        if (data.length != n) {
            return 0;
        }
        people = data;
        map = new HashMap<>();
        visited = new boolean[8];
        positions = new int[8];
        count = 0;
        map.put('A', 0);
        map.put('C', 1);
        map.put('F', 2);
        map.put('J', 3);
        map.put('M', 4);
        map.put('N', 5);
        map.put('R', 6);
        map.put('T', 7);
        dfs(0);
        return count;
    }

    public static void dfs(int idx) {
        if (idx == 8) {
            if (isValidArrangement()) {
                count++;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    positions[idx] = i;
                    dfs(idx + 1);
                    visited[i] = false;
                }
            }
        }
    }

    public static boolean isValidArrangement() {
        for (String condition : people) {
            int pos1 = positions[map.get(condition.charAt(0))];
            int pos2 = positions[map.get(condition.charAt(2))];
            int distance = condition.charAt(4) - '0' + 1;
            char operator = condition.charAt(3);

            switch (operator) {
                case '=':
                    if (Math.abs(pos1 - pos2) != distance) {
                        return false;
                    }
                    break;
                case '>':
                    if (Math.abs(pos1 - pos2) <= distance) {
                        return false;
                    }
                    break;
                case '<':
                    if (Math.abs(pos1 - pos2) >= distance) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(solution(2, new String[] { "N~F=0", "R~T>2" }));
    }
}
