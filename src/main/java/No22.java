public class No22 {
    class Solution {
        public int solution(String s) {
            // 영단어로 이루어진 숫자를 입력
            String[] engNum={"zero","one","two","three","four","five","six","seven","eight","nine"};
            // 숫자까지 하나하나 넣을 순 없으니 toString과 for를 이용해 대체시켜줌
            for (int i = 0; i <10 ; i++) {
                s=s.replace(engNum[i],Integer.toString(i));
            }
            //출력 형식을 맞춰줌
            return Integer.parseInt(s);
        }
    }
}
