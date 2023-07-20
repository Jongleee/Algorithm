package com.example.algorithm.java.stack;

import java.util.Stack;

public class MakeHamburger {
    public int solution(int[] ingredient) {
        int answer = 0;
        Stack<Integer> inStack = new Stack<Integer>();

        for (int in : ingredient) {
            inStack.push(in);

            if (inStack.size() >= 4) {
                if (inStack.get(inStack.size() - 4) == 1 &&
                    inStack.get(inStack.size() - 3) == 2 &&
                    inStack.get(inStack.size() - 2) == 3 &&
                    inStack.get(inStack.size() - 1) == 1) {
                        answer++;
                        inStack.pop();
                        inStack.pop();
                        inStack.pop();
                        inStack.pop();
                }
            }
        }

        return answer;
    }
    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(2, solution(new int[] { 2, 1, 1, 2, 3, 1, 2, 3, 1 }));
    //     Assertions.assertEquals(0, solution(new int[] { 1, 3, 2, 1, 2, 1, 3, 1, 2 }));
    // }
}
