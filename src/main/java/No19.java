import java.util.ArrayList;
import java.util.Collections;

public class No19 {
    class Solution {
        public String[] solution(String[] strings, int n) {
            ArrayList<String> strarr=new ArrayList<>();
            for (String string : strings) strarr.add(string.charAt(n) + string);
            Collections.sort(strarr);
            String[] answer = new String[strarr.size()];
            for (int i = 0; i <strarr.size(); i++) {
                answer[i] = strarr.get(i).substring(1);

            }
            return answer;
        }
    }
}
