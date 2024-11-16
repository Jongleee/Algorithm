package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TriangleAndThreeSide {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            int[] arr = new int[3];
            StringTokenizer st = new StringTokenizer(br.readLine());
            arr[0] = Integer.parseInt(st.nextToken());
            arr[1] = Integer.parseInt(st.nextToken());
            arr[2] = Integer.parseInt(st.nextToken());

            if (arr[0] == 0 && arr[1] == 0 && arr[2] == 0)
                break;

            Arrays.sort(arr);

            if (arr[2] >= arr[0] + arr[1]) {
                System.out.println("Invalid");
            } else if (arr[0] == arr[1] && arr[1] == arr[2]) {
                System.out.println("Equilateral");
            } else if (arr[0] == arr[1] || arr[1] == arr[2] || arr[0] == arr[2]) {
                System.out.println("Isosceles");
            } else {
                System.out.println("Scalene");
            }
        }
    }
}

/*
7 7 7
6 5 4
3 2 5
6 2 6
0 0 0

Equilateral
Scalene
Invalid
Isosceles
*/