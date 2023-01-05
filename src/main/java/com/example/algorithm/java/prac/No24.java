package com.example.algorithm.java.prac;

public class No24 {
    class Solution {
        public String solution(String id) {
            StringBuilder answer = new StringBuilder();
            String newId = id.toLowerCase();// 1단계 소문자화
            newId = newId.replaceAll("[^-_.a-z0-9]", "");// 2단계 영소문자, 숫자, 빼기, 밑줄, 점 제외 제거
            newId = newId.replaceAll("[.]{2,}", "."); // 3단계 점 2개 이상이면 점 하나로
            newId = newId.replaceAll("(^[.])|([.]$)", ""); // 4단계 처음과 끝 점 제거
            if (newId.equals(""))
                newId += "a";// 5단계 비어있을때 "a"대입
            if (newId.length() >= 16)
                newId = newId.substring(0, 15);// 6-1단계 16자 이상일때 15자까지
            newId = newId.replaceAll("[.]$", "");// 6-2단계 끝에 .이 있는 경우 제거
            if (newId.length() <= 2)
                while (newId.length() < 3)
                    answer.append(newId.charAt(newId.length() - 1));
            // 7단계 2자 이하인 경우 마지막 문자열을 3자가 될때까지 반복하여 붙여줌
            return answer.toString();
        }
    }
}
