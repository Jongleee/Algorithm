package prac;

public class No6 {
    public int solution(int n) {
        int answer = 0;
        // 문자열로 변환하여 각 숫자를 구함
        String[] a = Integer.toString(n).split("");

        // 숫자로 변환하여 한자리씩 더함
        for (int i = 0; i < a.length; i++) {
            answer += Integer.parseInt(a[i]);
        }
        // 10으로 나눈 나머지 더해도 될듯?

        return answer;
    }
}