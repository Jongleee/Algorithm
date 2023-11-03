package com.example.algorithm.java.practice;

public class StarSequence {
    public int solution(int[] a) {
        int[] count = new int[a.length];
        int answer = 0;

        for (int num : a)
            count[num]++;

        for (int i = 0; i < a.length; i++) {
            if (count[i] == 0)
                continue;

            if (count[i] <= answer)
                continue;

            int length = 0;
            for (int j = 0; j < a.length - 1; j++) {
                if (a[j] != i && a[j + 1] != i)
                    continue;
                if (a[j] == a[j + 1])
                    continue;
                length++;
                j++;
            }
            answer = Math.max(length, answer);
        }

        return answer * 2;
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals(0, solution(new int[] { 0 }));
    //     Assertions.assertEquals(4, solution(new int[] { 5, 2, 3, 3, 5, 3 }));
    //     Assertions.assertEquals(8, solution(new int[] { 0, 3, 3, 0, 7, 2, 0, 2, 2, 0 }));
    // }
}
