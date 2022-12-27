package prac;

import java.util.Arrays;

class Solution10 {
    public int[] solution(int[] arr) {
        // 먼저 빈 배열인 경우에 값을 정해줌
        if (arr.length <= 1) {
            return new int[] { -1 };
        }
        // 답 변수 정의
        int[] answer = new int[arr.length - 1];
        // 복제하여 오름차순 정렬 후 최솟값을 구함
        int[] arr1 = arr.clone();
        Arrays.sort(arr1);
        int min = arr1[0];
        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            // 값을 넣어줌 이때 최솟값인 경우 넣지 않도록 continue 사용
            // 문제에서 같은 값이 없다고 주어졌으므로 중복값 처리는 하지 않음
            if (arr[i] == min) {
                continue;
            }
            answer[j++] = arr[i];

        }
        return answer;
    }
}