package com.example.algorithm.java.twoPorinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsecutivePrimeSum {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int number = Integer.parseInt(br.readLine());

        List<Integer> primeList = generatePrimes(number);
        int count = countConsecutivePrimeSums(primeList, number);

        System.out.print(count);
    }

    private static List<Integer> generatePrimes(int limit) {
        boolean[] isNotPrime = new boolean[limit + 1];
        List<Integer> primes = new ArrayList<>();

        if (limit >= 2) {
            primes.add(2);
        }

        for (int i = 3; i <= limit; i += 2) {
            if (isNotPrime[i])
                continue;
            primes.add(i);
            for (int j = i * 2; j <= limit; j += i) {
                isNotPrime[j] = true;
            }
        }

        return primes;
    }

    private static int countConsecutivePrimeSums(List<Integer> primes, int target) {
        int count = 0;
        int start = 0;
        int end = 0;
        int sum = 0;

        while (true) {
            if (sum >= target) {
                if (sum == target) {
                    count++;
                }
                sum -= primes.get(start++);
            } else {
                if (end == primes.size())
                    break;
                sum += primes.get(end++);
            }
        }

        return count;
    }
}

/*
20

0


41

3


53

2
*/