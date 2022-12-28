package prac;

public class No25 {
    class Solution {
        public int solution(int left, int right) {
            // 제곱수인 경우에만 홀수이므로 빼면됨
            int sumAll=0;
            for (int i = left; i <=right ; i++) {
                double sqrt=Math.sqrt(i);
                if(sqrt== (int) sqrt){
                    sumAll-=i;
                }
                else sumAll+=i;
            }
            return sumAll;
        }
    }
}
