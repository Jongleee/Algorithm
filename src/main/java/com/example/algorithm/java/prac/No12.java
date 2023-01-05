package com.example.algorithm.java.prac;

public class No12 {
    public static boolean solution(int x) {
        int n = 0;
        String[] a = Integer.toString(x).split("");
        // 숫자로 변환하여 한자리씩 더함
        for (int i = 0; i < a.length; i++) {
            n += Integer.parseInt(a[i]);
        }
        if (n == 0)
            return false;
        return x % n == 0;
    }

    public static void main(String[] args) {
        System.out.println(solution(128));
        // false
    }
}