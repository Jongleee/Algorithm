package com.example.algorithm.java.bruteForce;

import java.util.Arrays;
import java.util.Comparator;

public class MineMinerals {
    public int solution(int[] picks, String[] minerals) {
        int answer = 0;
        int num = picks[0] + picks[1] + picks[2];
        int[][] section = new int[minerals.length / 5 +1][3];
    
        for (int i = 0; i < minerals.length && num > 0; i++) {
            int index = i / 5;
            switch (minerals[i].charAt(0)) {
                case 'd':
                    section[index][0]++;
                    section[index][1] += 5;
                    section[index][2] += 25;
                    break;
                case 'i':
                    section[index][0]++;
                    section[index][1]++;
                    section[index][2] += 5;
                    break;
                default:
                    section[index][0]++;
                    section[index][1]++;
                    section[index][2]++;
            }
            if (i % 5 == 4) {
                num--;
            }
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
        int pick = 0;
        for (int[] arr : section) {
            while (pick < 3 && picks[pick] == 0) {
                pick++;
            }
            if (pick == 3) {
                break;
            }
            picks[pick]--;
            answer += arr[pick];
        }
    
        return answer;
    }
}
