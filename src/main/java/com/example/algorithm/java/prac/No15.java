package com.example.algorithm.java.prac;

import java.util.*;

public class No15 {
    public static int[] solution(int[] arr) {
        ArrayList<Integer> answerlist = new ArrayList<>();
        // 쭉 더하되 이전 값과 같으면 넘어감
        // 더해주기 위해서 ArrayList를 사용
        // comp는 비교용으로 첫값을 넣기 위해 포함안되는 11로 설정
        int comp = 11;
        for (int i = 0; i < arr.length; i++) {
            if (comp == arr[i]) {
                continue;
            }
            answerlist.add(arr[i]);
            comp = arr[i];
        }
        int[] answer = new int[answerlist.size()];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = answerlist.get(i);
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{1,1,3,3,0,1,1})));
        //[1,3,0,1]
    }
}
