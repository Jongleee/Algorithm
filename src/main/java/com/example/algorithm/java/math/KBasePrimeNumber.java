package com.example.algorithm.java.math;

public class KBasePrimeNumber {
    public static int solution(int n, int k) {
        int answer = 0;
        String s = convertToBaseK(n, k);
        int j;
        for (int i = 0; i < s.length(); i = j) {
            j = i + 1;
            while (j < s.length() && s.charAt(j) != '0')
                j++;

            if (isPrime(Long.parseLong(s.substring(i, j))))
                answer++;
        }

        return answer;
    }

    public static String convertToBaseK(int n, int k) {
        StringBuilder result = new StringBuilder();

        while (n > 0) {
            result.insert(0, n % k);
            n /= k;
        }

        return result.toString();
    }

    public static boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        } else if (n == 2) {
            return true;
        }

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(solution(110011, 10));
    }
}
