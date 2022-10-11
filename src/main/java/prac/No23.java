package prac;

public class No23 {
class Solution1 {
    public String solution(String s, int n) {
        String answer = "";
        // char를 통해 코드값으로 전환
        for (int i = 0; i < s.length(); i++) {
            char tempText = s.charAt(i);
            //26을 넘는 경우 다음 인덱스로 넘어가기 때문에 26으로 나누어줌
            if (Character.isLowerCase(tempText)) {
                tempText = (char) ((tempText - 'a' + n) % 26 + 'a');
            } else if (Character.isUpperCase(tempText)) {
                tempText = (char) ((tempText - 'A' + n) % 26 + 'A');
            }
            answer += tempText;
        }
        return answer;
    }
}

class Solution2 {
     String solution(String s, int n) {
         String answer = "";
         // 인덱스 생성
         String indexUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
         String indexLower = "abcdefghijklmnopqrstuvwxyz";
         for (int i = 0; i < s.length(); i++) {
             // 소문자인 경우와 대문자인 경우, 공백인 경우를 나누어서 붙여줌
             if (Character.isLowerCase(s.charAt(i))) {
                 for (int j = 0; j <26 ; j++) {
                     if(s.charAt(i) == indexLower.charAt(j)) {
                         answer += indexLower.charAt((j + n) % 26);
                     }
                 }
             } else if (Character.isUpperCase(s.charAt(i))) {
                 for (int j = 0; j <26 ; j++) {
                     if(s.charAt(i)==indexUpper.charAt(j)) {
                         answer += indexUpper.charAt((j + n) % 26);
                     }
                 }
             } else if (s.charAt(i) == ' ') {
                 answer += " ";
             }
         }
         return answer;
     }
}
}
