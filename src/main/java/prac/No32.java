package prac;

import java.util.Arrays;
import java.util.Comparator;

public class No32 {
    private class Solution {
        public static int[] solution(int n, int[] stages) {
            int[] answer = new int[n];
            double[] remain = new double[n];
            double[][] failureNum = new double[n][2];
            //각 스테이지에 머무르는 사람의 수를 구함
            for (int i = 0; i < stages.length; i++) {
                if (stages[i] <= n) remain[stages[i] - 1] += 1;
            }
            //실패율을 구하기 위해 나눠질 값을 구함
            //분모는 단계마다 줄어들게 설정
            double denominator = stages.length;
            for (int i = 0; i < n; i++) {
                double numerator = remain[i];
                if (denominator == 0) failureNum[i][0] = 0;
                else failureNum[i][0] = numerator / denominator;
                denominator -= remain[i];
            }
            for (int i = 0; i < n; i++) {
                failureNum[i][1] = (double) i + 1;
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
            for (int i = 0; i < n; i++) {
                answer[i] = (int) failureNum[i][1];
            }

            return answer;
        }
    }
    public static void main(String[] args) {
        System.out.println(Arrays.toString(Solution.solution(5,new int[]{2, 1, 2, 6, 2, 4, 3, 3})));
    }


}
