import java.util.ArrayList;
import java.util.Collections;

public class No19 {
    class Solution {
        public String[] solution(String[] strings, int n) {
            //n번째 문자를 주어진 문자열 앞에 추가한 값을 넣어줄 ArrayList를 생성 후 입력
            ArrayList<String> strarr=new ArrayList<>();
            for (String string : strings) strarr.add(string.charAt(n) + string);
            //이 ArrayList를 정렬한 후 첫글자를 substring으로 제거하여 answer 배열에 입력
            Collections.sort(strarr);
            String[] answer = new String[strarr.size()];
            for (int i = 0; i <strarr.size(); i++) {
                answer[i] = strarr.get(i).substring(1);

            }
            return answer;
        }
    }
}
