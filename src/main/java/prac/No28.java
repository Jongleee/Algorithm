package prac;

public class No28 {
    class Solution {
        public int[] solution(int n, int m) {
            int gcd = 0;
            int min = (n <= m) ? n : m;
            for (int i = min; i > 0; i--) {
                if (n % i == 0 && m % i == 0) {
                    gcd = i;
                    break;
                }
            }
            if (gcd == 0)
                return new int[] { 0, 0 };
            int commonMultiply = n * m / gcd;
            return new int[] { gcd, commonMultiply };
        }
    }
}
