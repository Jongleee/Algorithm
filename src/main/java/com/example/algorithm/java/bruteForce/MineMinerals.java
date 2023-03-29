package com.example.algorithm.java.bruteForce;

import java.util.Arrays;
import java.util.Comparator;

public class MineMinerals {
    public int solution(int[] picks, String[] minerals) {
        int answer = 0;
        int num = picks[0] + picks[1] + picks[2];
        int[][] section = new int[minerals.length / 5 + 1][0];
        int i, pick;
        for(i = 0; i < minerals.length && num > 0; i++) {
            switch(minerals[i].charAt(0))
            {
                case 'd':
                    section[i / 5][0] += 1;
                    section[i / 5][1] += 5;
                    section[i / 5][2] += 25;
                    break;
                case 'i':
                    section[i / 5][0] += 1;
                    section[i / 5][1] += 1;
                    section[i / 5][2] += 5;
                    break;
                default:
                    section[i / 5][0] += 1;
                    section[i / 5][1] += 1;
                    section[i / 5][2] += 1;
            }
            if(i % 5 == 4) num--;            
        }
        Arrays.sort(section, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[2] < o2[2]) 
                    return 1;
                else 
                    return -1;
            }
        });
        for(i = 0, pick = 0; i < section.length; i++) {
            while(pick < 3 && picks[pick] == 0) pick++;
            if(pick == 3) break;
            picks[pick]--;
            answer += section[i][pick];
        }
        return answer;
    }
}
