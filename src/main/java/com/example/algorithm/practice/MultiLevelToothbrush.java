package com.example.algorithm.practice;

import java.util.HashMap;
import java.util.Map;

public class MultiLevelToothbrush {

    class Solution {
        public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
            //new String[]{"john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"},
            //                new String[]{"-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"},
            //                new String[]{"sam", "emily", "jaimie", "edward"},
            //                new int[]{2, 3, 5, 4}
            int[] answer = new int[enroll.length];
            Map<String, String> parent = new HashMap<>();
            Map<String, Integer> memberIndex = new HashMap<>();

            for(int i=0; i < enroll.length; i++){
                parent.put(enroll[i], referral[i]);
                memberIndex.put(enroll[i], i);
            }
            for(int i=0; i<seller.length; i++){

                String me = seller[i];
                int profit = 100 * amount[i];

                while(!me.equals("-")){
                    int parentProfit = profit / 10;
                    int myProfit = profit - parentProfit;
                    answer[memberIndex.get(me)] += myProfit;
                    me = parent.get(me);
                    profit /= 10;
                    if (profit < 1) {
                        break;
                    }
                }
            }

            return answer;
        }
    }
}
