package prac;

import java.util.*;

class Solution8 {
    public long solution(long n) {
        // 숫자로 변환하여 한자리씩 배열에 저장
        String[] a = String.valueOf(n).split("");
        // 역순 배열
        Arrays.sort(a, Collections.reverseOrder());
        String b="";
        // 배열을 역순으로 표시
        for (int i = 0; i < a.length; i++) {
            b+=a[i];
        }
        long answer=Long.parseLong(b);
        return answer;
    }
}
