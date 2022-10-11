package prac;

public class No14 {
    class Solution {
        public int solution(int[][] sizes) {
            int biggerSideMax = 0;
            int smallerSideMax = 0;
            for (int[] size : sizes) {
                biggerSideMax = Math.max(biggerSideMax, Math.max(size[0], size[1]));
                smallerSideMax = Math.max(smallerSideMax, Math.min(size[0], size[1]));
            }
            return biggerSideMax * smallerSideMax;
        }
    }
}



