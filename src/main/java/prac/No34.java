package prac;

import java.util.Arrays;

public class No34 {

    class Solution {
        public int solution(int[] nums) {
            int answer = 0;
            Arrays.sort(nums);
            for (int i = 0; i <nums.length-1 ; i++) {
                if(nums[i]==nums[i+1] )nums[i]=0;
            }
            for (int i = 0; i <nums.length ; i++) {
                if(nums[i]!=0) answer++;
            }
            if(answer>nums.length/2) answer=nums.length/2;
            return answer;
        }
    }
}
