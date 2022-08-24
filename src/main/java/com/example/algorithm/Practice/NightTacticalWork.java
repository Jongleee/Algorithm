package com.example.algorithm.Practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NightTacticalWork {
    public int solution(int distance, int[][] scope, int[][] times) {
        //10, new int[][]{{3, 4}, {5, 8}}, new int[][]{{2, 5}, {4, 3}}
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < scope.length; i++) {

            Arrays.sort(scope[i]);
            int start = scope[i][0];
            int end = scope[i][1];
            int workTime = times[i][0];
            int restTime = times[i][1];

            while (start<=end){
                int timeCheck = start%(workTime+restTime);
                if(timeCheck>0&&timeCheck<=workTime){
                    temp.add(start);
                    break;
                }
                start++;
            }
        }
        Collections.sort(temp);
        if(temp.size()>0) return temp.get(0);
        return distance;
    }
}
