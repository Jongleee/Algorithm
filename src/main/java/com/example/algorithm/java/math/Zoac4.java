package com.example.algorithm.java.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Zoac4 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        String[] splitInput = input.split(" ");

        int H = Integer.parseInt(splitInput[0]);
        int W = Integer.parseInt(splitInput[1]);
        int N = Integer.parseInt(splitInput[2]);
        int M = Integer.parseInt(splitInput[3]);

        int f = (H - 1) / (N + 1) + 1;
        int s = (W - 1) / (M + 1) + 1;

        System.out.println(f * s);
    }
    //5 4 1 1
    //6
}
