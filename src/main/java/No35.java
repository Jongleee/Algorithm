import java.util.Arrays;

public class No35 {

    class Solution {
        public String[] solution(int n, int[] arr1, int[] arr2) {
            String[] answer = new String[n];
            Arrays.fill(answer,"");
            String[] temp1 = new String[n];
            String[] temp2 = new String[n];
            for (int i = 0; i < n; i++) {
                String tempVal = Integer.toString(arr1[i], 2);
                int size = tempVal.length();
                while (size < n) {
                    tempVal = Integer.toString(0) + tempVal;
                    size = tempVal.length();
                }
                temp1[i] = tempVal;
            }

            for (int i = 0; i < n; i++) {
                String tempVal = Integer.toString(arr2[i], 2);
                int size = tempVal.length();
                while (size < n) {
                    tempVal = Integer.toString(0)  + tempVal;
                    size = tempVal.length();
                }
                temp2[i] = tempVal;
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (temp1[i].charAt(j)=='1' ||temp2[i].charAt(j)=='1' ) answer[i]+="#";
                    else answer[i]+=" ";
                }
            }

            return answer;
        }
    }
}
