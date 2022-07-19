import java.util.Arrays;

public class No27 {
    class Solution {
        public int solution(int[] d, int budget) {
            Arrays.sort(d);
            int answer = 0;
            for (int i = 0; i <d.length; i++) {
                budget=budget-d[i];
                if (budget<0)break;
                answer++;
            }
            return answer;
        }
    }
}
