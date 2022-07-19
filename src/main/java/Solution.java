import java.util.Arrays;

class Solution {
    public int[] solution(int N, int[] stages) {
        int[] answer = new int[N];
        double[] success = new double[N];
        double [][] failureNum=new double[N][2];
        //각 스테이지에 머무르는 사람의 수를 구함
        for (int i = 0; i <N; i++) {
            if (stages[i]>=i+1) success[i]+=1;
        }
        //실패율을 구하기 위해 나눠질 값을 구함
        //분모는 단계마다 줄어들게 설정
        int denominator=stages.length;
        for (int i = 0; i <N; i++) {
            double numerator=success[i];
            if (denominator==0) failureNum[i][0]=0;
            else failureNum[i][0]=numerator/denominator;
            denominator-=success[i];
        }
        for (int i = 0; i <N; i++) {
            failureNum[i][1]=i+1;
        }

        Arrays.sort(failureNum, (o1,o2) -> {
            if (o1[0]==o2[0])
                return Double.compare(o1[1],o2[1]);
            return -Double.compare(o1[0],o2[0]);
        });
        for (int i = 0; i <N; i++) {
            answer[i]=(int)failureNum[i][1];
        }
        return answer;
    }
//    public static void main(String[] args){
//        Solution ns = new Solution();
//        //변수 선언
//        int N=4;
//        int[] stages={4,4,4,4,4};
//        System.out.println(ns.solution(N,stages));
//
//    }
}