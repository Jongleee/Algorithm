package com.example.algorithm.java.string;

public class LesserDifferentBit {
    public long[] solution(long[] numbers) {
        long[] answer = new long[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] % 2 == 0) {
                answer[i] = numbers[i] + 1;
            } else {
                String binaryString = Long.toBinaryString(numbers[i]);
                int zeroIdx = binaryString.lastIndexOf("0");
                if (zeroIdx != -1) {
                    binaryString = binaryString.substring(0, zeroIdx) + "10" + binaryString.substring(zeroIdx + 2);
                    answer[i] = Long.parseLong(binaryString, 2);
                } else {
                    binaryString = "10" + binaryString.substring(1);
                    answer[i] = Long.parseLong(binaryString, 2);
                }
            }
        }
        return answer;
    }
}
