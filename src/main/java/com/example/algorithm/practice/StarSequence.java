package com.example.algorithm.practice;

public class StarSequence {
    public static void main(String[] args) {
        System.out.println(solution(new int[] { 0, 3, 3, 0, 7, 2, 0, 2, 2, 0 }));
    }

    public static int solution(int[] a) {
        int[] count = new int[a.length];
        for (int num : a)
            count[num]++;
        int answer = 0;
        for (int i = 0; i < a.length; i++) {
            if (count[i] == 0)
                continue;
            if (count[i] <= answer)
                continue;
            int length = 0;
            for (int j = 0; j < a.length - 1; j ++) {
                if ((a[j] != i && a[j + 1] != i))
                    continue;
                if (a[j] == a[j + 1]) 
                    continue;
                length++;
                j++;
            }
            answer = Math.max(length, answer);
        }
        return answer * 2;
    }
}
