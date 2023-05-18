package com.example.algorithm.java.recursive;

import java.util.Stack;

public class ConvertParentheses {
    public String solution(String p) {
        return dfs(p);
    }
    
    public static String dfs(String w) {
        if (w.isEmpty()) {
            return "";
        }
        
        String u = "";
        String v = "";
        int lcnt = 0;
        int rcnt = 0;
        
        for (int i = 0; i < w.length(); i++) {
            if (w.charAt(i) == '(') {
                lcnt++;
            } else {
                rcnt++;
            }
            
            u += w.charAt(i);
            
            if (lcnt == rcnt) {
                v = w.substring(i + 1);
                break;
            }
        }
        
        if (isCorrect(u)) {
            return u + dfs(v);
        } else {
            StringBuilder temp = new StringBuilder("(");
            temp.append(dfs(v));
            temp.append(")");
            u = u.substring(1, u.length() - 1);
            
            for (int i = 0; i < u.length(); i++) {
                if (u.charAt(i) == '(') {
                    temp.append(')');
                } else {
                    temp.append('(');
                }
            }
            
            return temp.toString();
        }
    }
    
    public static boolean isCorrect(String str) {
        Stack<Character> st = new Stack<>();
        
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                st.push('(');
            } else {
                if (st.isEmpty() || st.peek() == ')') {
                    return false;
                } else {
                    st.pop();
                }
            }
        }
        
        return true;
    }
}
