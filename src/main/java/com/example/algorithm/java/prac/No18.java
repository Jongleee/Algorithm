package com.example.algorithm.java.prac;

import java.util.ArrayList;

public class No18 {

    class Solution {
        public int[] solution(int[] answers) {
            // 각 수포자가 찍는 값을 입력
            int[] firstSupo = { 1, 2, 3, 4, 5 };
            int[] secondSupo = { 2, 1, 2, 3, 2, 4, 2, 5 };
            int[] thirdSupo = { 3, 3, 1, 1, 2, 2, 4, 4, 5, 5 };
            int[] right = new int[3];
            // 각 수포자가 맞은 갯수를 구하기 위해 %배열수의 값과 비교
            for (int i = 0; i < answers.length; i++) {
                if (firstSupo[i % 5] == answers[i])
                    right[0]++;
                if (secondSupo[i % 8] == answers[i])
                    right[1]++;
                if (thirdSupo[i % 10] == answers[i])
                    right[2]++;
            }
            // 각 사람의 정답 수 중에서 가장 큰 값을 찾아줌
            int maxRight = Math.max(right[1], Math.max(right[2], right[0]));
            // 각 사람의 정답 수와 가장 큰 정답의 갯수를 비교하여 차례대로 배열에 더함
            ArrayList<Integer> tempAnswer = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                if (maxRight == right[i])
                    tempAnswer.add(i + 1);
            }
            // answer 배열을 정의해주고 값을 넣어줌
            int[] answer = new int[tempAnswer.size()];
            for (int i = 0; i < tempAnswer.size(); i++) {
                answer[i] = tempAnswer.get(i);
            }
            return answer;
        }
    }
}
