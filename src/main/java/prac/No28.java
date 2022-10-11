package prac;

public class No28 {
    class Solution {
        public int[] solution(int n, int m) {
            int GCD = 0;
            int min = (n <= m) ? n : m;
            for (int i = min; i >0; i--) {
                if (n % i == 0 && m % i == 0) {
                    GCD = i;
                    break;
                }
            }
            int commonMultiply = n * m / GCD;
            return new int[]{GCD, commonMultiply};
        }
    }
}
