package prac;

class Solution5 {
    public String solution(String s) {
        StringBuilder answer = new StringBuilder();
        // 단어를 하나씩 쪼개기
        String[] ss = s.split("");

        int cnt = 0;
        for (int i = 0; i < ss.length; i++) {
            // 공백을 만나면 카운트 초기화
            if (ss[i].equals(" ")) {
                cnt = 0;
            }
            // 짝수번째 단어 대문자
            else if (cnt % 2 == 0) {
                ss[i] = ss[i].toUpperCase();
                cnt++;
            }
            // 나머지 경우는 소문자로 덧붙여줌
            else {
                ss[i] = ss[i].toLowerCase();
                cnt++;
            }
            answer.append(ss[i]);

        }
        return answer.toString();
    }
}