package com.example.algorithm.java.prac;

import java.util.Arrays;

class Solution7 {
    public static int[] solution(long n) {
        // 숫자로 변환하여 한자리씩 배열에 저장
        String[] b = String.valueOf(n).split("");
        int[] answer = new int[b.length];
        int[] v = new int[b.length];
        // i 와 j를 반대방향으로 돌게 해서 배열을 뒤집음
        for (int i = v.length - 1, j = 0; i >= 0; i--, j++) {
            answer[i] = Integer.parseInt(b[j]);
        }
        return answer;
    }
    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(21425)));
        //[5, 2, 4, 1, 2]
    }
}
