package com.example.algorithm.java.prac;

class Solution3 {
    public static String solution(int n) {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < n; i++) {
            // 홀수에 수 짝수에 박을 각각 더해줌 이때 0부터 시작하므로 %2=0을 사용함
            if (i % 2 == 0) {
                answer.append("수");
            } else {
                answer.append("박");
            }
        }
        return answer.toString();
    }
    public static void main(String[] args) {
        System.out.println(solution(9));
        //수박수박수박수박수
    }
}