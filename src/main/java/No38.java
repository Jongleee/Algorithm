import java.util.ArrayList;
import java.util.Collections;

public class No38 {

    class Solution {
        public String solution(String s) {
            String[]str=s.split(" ");
            ArrayList<Integer> integerList = new ArrayList<>();
            String answer = "";
            for (int i = 0; i < str.length; i++) {
                integerList.add(Integer.valueOf(str[i]));
            }
            Collections.sort(integerList);
            answer=integerList.get(0)+" "+integerList.get(str.length-1);

            return answer;
        }
    }
}
