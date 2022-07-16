public class No9 {
class Solution9 {
    public long solution(long n) {
        //제곱근의 값을 구함
        Double sqrt = Math.sqrt(n);
        //정수가 맞는지 판별하여 제곱해줌
        if(sqrt == sqrt.intValue()){
            return (long)Math.pow(sqrt + 1, 2);
        } else return -1;
    }
}
}
