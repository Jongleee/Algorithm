package com.example.algorithm.java.greedy;

public class Joystick {
    public int solution(String name) {
        int answer = 0;
        int length = name.length();
        int minMove = length - 1;

        for (int i = 0; i < length; i++) {
            answer += Math.min(name.charAt(i) - 'A', 'Z' - name.charAt(i) + 1);

            int nextIndex = i + 1;
            while (nextIndex < length && name.charAt(nextIndex) == 'A') {
                nextIndex++;
            }

            minMove = Math.min(minMove, i + length - nextIndex + Math.min(i, length - nextIndex));
        }

        return answer + minMove;
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals(56, solution("JEROEN"));
    //     Assertions.assertEquals(23, solution("JAN"));
    // }
}
