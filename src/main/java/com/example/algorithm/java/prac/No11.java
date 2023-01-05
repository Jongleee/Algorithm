package com.example.algorithm.java.prac;

class Solution11 {
    public static int solution(int num) {
        if (num == 1) {
            return 0;
        }
        for (int i = 0; i < 499; i++) {
            if (num % 2 == 0) {
                num = num / 2;
            }
            // else if num%2==1 조건 대신 else로 둔 경우 626331이 입력된 경우
            // 오버플로우로 인해 500번 전에 결과가 나오는 오류가 발생함
            else if (num % 2 == 1) {
                num = num * 3 + 1;
            }
            if (num == 1) {
                return i + 1;
            }
        }
        return -1;

    }

    public static void main(String[] args) {
        System.out.println(solution(626331));
    }
}