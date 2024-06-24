package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class InstallRouter {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int[] x = new int[n];
        
        for (int i = 0; i < n; ++i) {
            x[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(x);

        int left = 0;
        int right = 1000000000;
        
        while (left <= right) {
            int mid = (left + right) >> 1;
            int res = countRouters(x, mid);
            
            if (res >= c) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        System.out.println(left - 1);
        br.close();
    }

    private static int countRouters(int[] x, int k) {
        int count = 0;
        int coverage = -1;
        
        for (int router : x) {
            if (router >= coverage) {
                count++;
                coverage = router + k;
            }
        }
        
        return count;
    }
}

/*
5 3
1
2
8
4
9

3
 */