package prac;

import java.util.*;

class Solution2 {
    public int[] solution(int[] arr, int divisor) {
        int cnt = 0;
        int j = 0;
        //divisor로 나눈 나머지가 0인 값의 갯수를 구함
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % divisor == 0) {
                cnt++;
            }
        }
        // 나누어 떨어지지 않는 경우 {-1} 대입
        if (cnt == 0) {
            int[] answer = {-1};
            return answer;
        }
        // 나누어 떨어지는 값의 갯수 만큼의 배열을 정의
        int[] answer = new int[cnt];
        // 그 배열에 해당하는 경우의 값을 넣어줌
        for (int i = 0; i < arr.length; i++) {
            if(arr[i]%divisor==0){
                answer[j]=arr[i];
                j++;
            }
        }

        Arrays.sort(answer);
        return answer;
    }
}
