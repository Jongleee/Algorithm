package com.example.algorithm.java.practice;

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
                    popConditionBig(s, stack, i);
                }

                else if(s.charAt(i)=='}'){
                    popConditionMiddle(s, stack, i);
                }
                else if(s.charAt(i)==')'){
                    popConditionSmall(s, stack, i);
                }
                else {stack.push(s.charAt(i));}
            }
        }
        return stack.isEmpty();
    }

    private void popConditionSmall(StringBuilder s, Stack<Character> stack, int i) {
        if(stack.peek() == '(') stack.pop();
        else {stack.push(s.charAt(i));}
    }

    private void popConditionMiddle(StringBuilder s, Stack<Character> stack, int i) {
        if(stack.peek() == '{') stack.pop();
        else {stack.push(s.charAt(i));}
    }

    private void popConditionBig(StringBuilder s, Stack<Character> stack, int i) {
        if (stack.peek() == '[') stack.pop();
        else {
            stack.push(s.charAt(i));
        }
    }
}
