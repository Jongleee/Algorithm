package com.example.algorithm.java.bruteForce;

public class StringCompression {
    public static int solution(String s) {
        int answer = s.length();
        for (int i = 1; i <= s.length() / 2; i++) {
            int zipLevel = 1;
            String zipStr = s.substring(0, i);
            StringBuilder result = new StringBuilder();
            for (int j = i; j <= s.length(); j += i) {
                String next = j + i > s.length() ? s.substring(j) : s.substring(j, j + i);
                if (zipStr.equals(next)) {
                    zipLevel++;
                } else {
                    result.append(zipLevel > 1 ? Integer.toString(zipLevel) : "").append(zipStr);
                    zipStr = next;
                    zipLevel = 1;
                }
            }
            result.append(zipStr);
            answer = Math.min(answer, result.length());
        }
        return answer;
    }
    public static void main(String[] args) {
        System.out.println(solution("abcabcabcabcdededededede"));
    }
}
