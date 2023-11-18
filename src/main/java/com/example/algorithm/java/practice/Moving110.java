package com.example.algorithm.java.practice;

import java.util.Stack;

public class Moving110 {
    public String[] solution(String[] s) {
        String[] answer = new String[s.length];

        for (int i = 0; i < s.length; i++) {
            answer[i] = solve(s[i]);
        }

        return answer;
    }

    public String solve(String s) {
        int cnt110 = 0;
        Stack<Character> st = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            st.push(s.charAt(i));

            if (st.size() < 3)
                continue;

            cnt110 = processStack(st, cnt110);
        }

        StringBuilder sb = new StringBuilder();
        int position = st.size();
        boolean flag = false;

        position = makeString(st, sb, position, flag);

        for (int i = 0; i < cnt110; i++)
            sb.insert(position, "110");

        return sb.toString();
    }

    private int processStack(Stack<Character> st, int cnt110) {
        char first = st.pop();
        char second = st.pop();
        char third = st.pop();

        if (first == '0' && second == '1' && third == '1') {
            cnt110++;
        } else {
            st.push(third);
            st.push(second);
            st.push(first);
        }
        return cnt110;
    }

    private int makeString(Stack<Character> st, StringBuilder sb, int position, boolean flag) {
        while (!st.isEmpty()) {
            char pop = st.pop();
            if (!flag && pop == '1')
                position--;
            if (pop == '0')
                flag = true;
            sb.insert(0, pop);
        }
        return position;
    }

    // @Test
    // void 정답() {
    //     String[] s1= { "1110", "100111100", "0111111010" };
    //     Assertions.assertArrayEquals(new String[] { "1101","100110110","0110110111" }, solution(s1));
    // }
}
