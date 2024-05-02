package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class GasStation {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        int n = Integer.parseInt(br.readLine()) - 1;
        StringTokenizer st1 = new StringTokenizer(br.readLine());
        StringTokenizer st2 = new StringTokenizer(br.readLine());

        long min = Integer.MAX_VALUE;
        long result = 0;

        for (int i = 0; i < n; i++) {
            min = Math.min(min, Integer.parseInt(st2.nextToken()));
            result += min * Integer.parseInt(st1.nextToken());
        }

        bw.write(Long.toString(result));
        bw.flush();
        bw.close();
    }
}

/*
4
2 3 1
5 2 4 1

18

4
3 3 4
1 1 1 1

10
 */