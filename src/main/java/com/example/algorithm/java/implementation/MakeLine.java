package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MakeLine {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= n; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            sb.append(st.nextToken()).append(" ");

            int[] arr = new int[20];
            int cnt = 0;
            for (int i = 0; i < arr.length; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (arr[j] > arr[i]) {
                        cnt++;
                    }
                }
            }
            sb.append(cnt).append('\n');
        }

        System.out.println(sb);
    }
}
/*
4
1 900 901 902 903 904 905 906 907 908 909 910 911 912 913 914 915 916 917 918 919
2 919 918 917 916 915 914 913 912 911 910 909 908 907 906 905 904 903 902 901 900
3 901 902 903 904 905 906 907 908 909 910 911 912 913 914 915 916 917 918 919 900
4 918 917 916 915 914 913 912 911 910 909 908 907 906 905 904 903 902 901 900 919

1 0
2 190
3 19
4 171
 */