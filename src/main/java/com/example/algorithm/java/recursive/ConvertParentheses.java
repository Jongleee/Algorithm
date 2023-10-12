package com.example.algorithm.java.recursive;

import java.util.Stack;

public class ConvertParentheses {
    public String solution(String p) {
        return dfs(p);
    }

    private String dfs(String w) {
        if (w.isEmpty()) {
            return "";
        }

        int splitIndex = findSplitIndex(w);
        String u = w.substring(0, splitIndex + 1);
        String v = w.substring(splitIndex + 1);

        if (isCorrect(u)) {
            return u + dfs(v);
        } else {
            StringBuilder result = new StringBuilder("(");
            result.append(dfs(v));
            result.append(")");

            u = u.substring(1, u.length() - 1);

            for (char ch : u.toCharArray()) {
                if (ch == '(') {
                    result.append(')');
                } else {
                    result.append('(');
                }
            }

            return result.toString();
        }
    }

    private int findSplitIndex(String w) {
        int lcnt = 0;
        int rcnt = 0;

        for (int i = 0; i < w.length(); i++) {
            if (w.charAt(i) == '(') {
                lcnt++;
            } else {
                rcnt++;
            }

            if (lcnt == rcnt) {
                return i;
            }
        }

        return -1;
    }

    private boolean isCorrect(String str) {
        Stack<Character> stack = new Stack<>();

        for (char ch : str.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }
        }

        return true;
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals("(()())()", solution("(()())()"));
    //     Assertions.assertEquals("()", solution(")("));
    //     Assertions.assertEquals("()(())()", solution("()))((()"));
    // }
}
