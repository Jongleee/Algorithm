class Solution3 {
    public String solution(int n) {
        String answer = "";
        for (int i=0; i<n; i++){
            //홀수에 수 짝수에 박을 각각 더해줌 이때 0부터 시작하므로 %2=0을 사용함
            if(i%2==0){
                answer+="수";
            }
            else{
                answer+="박";
            }
        }
        return answer;
    }
}