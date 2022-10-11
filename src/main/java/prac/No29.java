package prac;

import java.util.Arrays;

public class No29 {
    class Solution {
        public int[] solution(int[] array, int[][] commands) {
            int[] answer = new int[commands.length];
            for (int i = 0; i < commands.length; i++) {
                //문제에서 주어진 조건대로 잘라주고 정렬 후 대입
                int[] tempArray = Arrays.copyOfRange(array,commands[i][0]-1, commands[i][1]);
                Arrays.sort(tempArray);
                answer[i]=tempArray[commands[i][2]-1];
            }
            return answer;
        }
    }
}
