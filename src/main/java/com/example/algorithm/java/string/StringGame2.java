package com.example.algorithm.java.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StringGame2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        int testCases = Integer.parseInt(br.readLine());
        
        while (testCases-- > 0) {
            char[] word = br.readLine().toCharArray();
            int k = Integer.parseInt(br.readLine());
            int minLength = Integer.MAX_VALUE;
            int maxLength = -1;
            int[] charCount = new int[26];
            int[] prevKPos = new int[26];
            int[] prevPos = new int[26];
            int[] nextPos = new int[word.length];
            
            for (int i = 0; i < word.length; i++) {
                int charIndex = word[i] - 'a';
                charCount[charIndex]++;
                
                if (charCount[charIndex] == 1) prevKPos[charIndex] = i;
                if (charCount[charIndex] >= 2) nextPos[prevPos[charIndex]] = i;
                prevPos[charIndex] = i;
                
                if (charCount[charIndex] == k) {
                    minLength = Math.min(minLength, i - prevKPos[charIndex] + 1);
                    maxLength = Math.max(maxLength, i - prevKPos[charIndex] + 1);
                } else if (charCount[charIndex] > k) {
                    prevKPos[charIndex] = nextPos[prevKPos[charIndex]];
                    minLength = Math.min(minLength, i - prevKPos[charIndex] + 1);
                    maxLength = Math.max(maxLength, i - prevKPos[charIndex] + 1);
                }
            }
            
            if (minLength == Integer.MAX_VALUE) {
                sb.append(-1).append("\n");
            } else {
                sb.append(minLength).append(" ").append(maxLength).append("\n");
            }
        }
        
        System.out.println(sb);
    }
}


/*
2
superaquatornado
2
abcdefghijklmnopqrstuvwxyz
5

4 8
-1


1
abaaaba
3

3 4
 */