package com.example.algorithm;

public class BestSet {

    public int[] solution(int n, int s) {
        if (n>s) return new int[]{-1};
        int[] answer = new int[n];
        if(s%n==0){
            for (int i = 0; i < n; i++) {
                answer[i]=s/n;
            }
        }
        if(s%n!=0){
            int remainder=s%n;
            int position=n-remainder;
            for (int i = 0; i <n ; i++) {
                if(i<position) answer[i]=s/n;
                if(i>=position) answer[i]=s/n+1;
            }

        }
        return answer;
    }
}
