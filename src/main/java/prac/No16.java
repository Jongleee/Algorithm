package prac;

import java.util.*;

public class No16 {
    public int[] solution(int[] numbers) {
        int val = 0;
        ArrayList<Integer> sumlist = new ArrayList<>();
        for (int i = 0; i < numbers.length - 1; i++) {
            // 범위가 같으면 같은 값을 더하게 되므로
            // i는 0부터 총 길이-1까지 j는 i+1부터 총길이까지 더해줌
            for (int j = i + 1; j < numbers.length; j++) {
                val = numbers[i] + numbers[j];
                if (sumlist.contains(val)) {
                    continue;
                }
                sumlist.add(val);
            }
        }
        Collections.sort(sumlist);
        int[] answer = new int[sumlist.size()];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = sumlist.get(i);
        }
        return answer;
    }

}
