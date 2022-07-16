import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class prac {
class Solution {
    public String[] solution(String[] strings, int n) {
        ArrayList<String> strarr=new ArrayList<>();
        for (String string : strings) strarr.add(string.charAt(n) + string);
        Collections.sort(strarr);
        String[] answer = new String[strarr.size()];
        for (int i = 0; i < ; i++) {
            answer[i] = strarr[i].substring(1);

        }
        return answer;
    }
}
}
