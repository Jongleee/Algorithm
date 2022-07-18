import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public String solution(String s) {
        String answer = "";
        // 문자열 분할한 후 역순 정렬
        String[] strSplit=s.split("");
        Arrays.sort(strSplit, Comparator.reverseOrder());
        // answer 배열에 대입
        for (int i = 0; i < strSplit.length; i++) {
            answer+=strSplit[i];
            }
        return answer;
    }
}