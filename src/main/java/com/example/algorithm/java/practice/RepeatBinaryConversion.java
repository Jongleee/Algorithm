package com.example.algorithm.java.practice;

public class RepeatBinaryConversion {
    public int[] solution(String s) {
        int[] answer = {0,0};
        int temp=0;
        while (s.length()>1) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i)=='0') {
                    answer[1]+=1;
                }
                else{
                    temp+=1;
                }
            }
            s=Integer.toBinaryString(temp);
            temp=0;
            answer[0]+=1;
        }
        return answer;
    }
}
