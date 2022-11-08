package com.example.algorithm.practice;

import java.util.ArrayList;
import java.util.List;

public class CollatzIntegral {
    public double[] solution(int k, int[][] ranges) {
        double[] answer = new double[ranges.length];
        List<Integer> collatz= new ArrayList<>();
        Double initialNum= (double) k;
        while (k>1){
            collatz.add(k);
            if(k%2==0)k/=2;
            else k=3*k+1;
        }
        collatz.add(1);
        Double[] collatzNumber=new Double[collatz.size()];
        collatzNumber[0]= initialNum;
        for (int i = 1; i < collatz.size(); i++) {
            collatzNumber[i]=Double.valueOf(collatz.get(i));
        }
        double[] areaSum= new double[collatzNumber.length];
        areaSum[0]=0;
        for (int i = 1; i < collatzNumber.length; i++) {
            areaSum[i]= (collatzNumber[i-1]+collatzNumber[i])/2+areaSum[i-1];
        }
        for (int i = 0; i < ranges.length; i++) {
            int start = ranges[i][0];
            int end = collatzNumber.length + ranges[i][1]-1;

            if (end>start){
                answer[i]=areaSum[end]-areaSum[start];
            }
            if (end==start) answer[i]=0;
            if (end<start) answer[i]=-1;
        }
        return answer;
    }
}
