package com.example.algorithm.Practice;

import java.util.Stack;

public class ParenthesisRotation {
    public int solution(String s) {
        int answer = 0;
        for (int i = 0; i <s.length() ; i++) {
            StringBuilder sb = new StringBuilder(s);
            String substring=s.substring(0,i);
            sb.delete(0,i);
            sb.append(substring);
            if(isCorrect(sb))answer+=1;
        }

        return answer;
    }

    private boolean isCorrect(StringBuilder s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if(stack.isEmpty())stack.push(s.charAt(i));
            else {
                if (s.charAt(i) == ']') {
                    if (stack.peek() == '[') stack.pop();
                    else {
                        stack.push(s.charAt(i));
                    }
                }

                else if(s.charAt(i)=='}'){
                    if(stack.peek() == '{') stack.pop();
                    else {stack.push(s.charAt(i));}
                }
                else if(s.charAt(i)==')'){
                    if(stack.peek() == '(') stack.pop();
                    else {stack.push(s.charAt(i));}
                }
                else {stack.push(s.charAt(i));}
            }
        }
        return stack.isEmpty();
    }
}
