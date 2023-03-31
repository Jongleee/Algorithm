package com.example.algorithm.java.greedy;

public class Joystick {
    public int solution(String name) {
        int answer = 0;
        int length = name.length();

        int move = length - 1;
        for(int i = 0; i < length; i++){
            answer += Math.min(name.charAt(i) - 'A', 'Z' - name.charAt(i) + 1);

            int index = i + 1;
            while(index < length && name.charAt(index) == 'A'){
                index++;
            }

            move = Math.min(move, i + length - index + Math.min(i, length - index));
        }
        return answer + move;
    }
}
