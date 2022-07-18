public class No23 {
    class Solution {
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
}
