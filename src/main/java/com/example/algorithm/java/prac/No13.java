package com.example.algorithm.java.prac;

public class No13 {
    public int solution(int n) {
        int answer = 0;
        String t = Integer.toString(n, 3);
        String[] m = t.split("");
        for (int i = 0; i < m.length; i++) {
            int e = Integer.parseInt(m[i]);
            int r = (int) Math.pow(3, i);
            answer += e * r;
        }
        return answer;
    }

    public static void main(String[] args) {
        No13 ns = new No13();
        System.out.println(ns.solution(125));
        // 229
    }
}