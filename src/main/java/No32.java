import java.util.Arrays;
import java.util.Comparator;

public class No32 {
    class Solution {
        public int[] solution(int N, int[] stages) {
            int[] answer = new int[N];
            double[] success = new double[N];
            double[][] failureNum = new double[N][2];
            //각 스테이지에 머무르는 사람의 수를 구함
            for (int i = 0; i < stages.length; i++) {
                if (stages[i] <= N) success[stages[i] - 1] += 1;
            }
            //실패율을 구하기 위해 나눠질 값을 구함
            //분모는 단계마다 줄어들게 설정
            double denominator = stages.length;
            for (int i = 0; i < N; i++) {
                double numerator = success[i];
                if (denominator == 0) failureNum[i][0] = 0;
                else failureNum[i][0] = numerator / denominator;
                denominator -= success[i];
            }
            for (int i = 0; i < N; i++) {
                failureNum[i][1] = i + 1;
            }
            Arrays.sort(failureNum, new Comparator<double[]>() {
                @Override
                public int compare(double[] o1, double[] o2) {
                    //실패율 같을때 1번=번호에 따라 오름차순 정렬
                    if (o1[0] == o2[0]) {
                        return Double.compare(o1[1], o2[1]);
                    } else {
                        //나머지는 실패율 내림차순 정렬
                        return Double.compare(o2[0], o1[0]);
                    }
                }
            });
            for (int i = 0; i < N; i++) {
                answer[i] = (int) failureNum[i][1];
            }

            return answer;
        }
    }


}
