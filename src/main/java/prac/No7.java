package prac;

class Solution7 {
    public int[] solution(long n) {
        // 숫자로 변환하여 한자리씩 배열에 저장
        String[] b = String.valueOf(n).split("");
        int[] answer = new int[b.length];
        int[] v=new int[b.length];
        // i 와 j를 반대방향으로 돌게 해서 배열을 뒤집음
        for (int i = v.length - 1, j = 0; i >= 0; i--, j++) {
            answer[i] = Integer.parseInt(b[j]);
        }
        return answer;
    }
}
