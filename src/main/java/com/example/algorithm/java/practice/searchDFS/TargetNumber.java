package com.example.algorithm.java.practice.searchDFS;

public class TargetNumber {
    static int answer = 0;

    public static int solution(int[] numbers, int target) {
        dfs(numbers, 0, target, 0);
        return answer;
    }

    public static void dfs(int[] numbers, int depth, int target, int sum) {
        if (depth == numbers.length) {
            if (target == sum)
                answer++;
        } else {
            dfs(numbers, depth + 1, target, sum + numbers[depth]);
            dfs(numbers, depth + 1, target, sum - numbers[depth]);
        }
    }

    public static void main(String[] args) {
        int[] n1 = { 1, 1, 1, 1, 1 };
        int t1 = 3;// 5
        int[] n2 = { 4, 1, 2, 1 };
        int t2 = 4;// 2
        System.out.println(solution(n1, t1));
        answer=0;
        System.out.println(solution(n2, t2));
    }
}
