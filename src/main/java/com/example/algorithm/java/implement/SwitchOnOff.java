package com.example.algorithm.java.implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SwitchOnOff {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] sw = new int[n + 1];

		String[] input = br.readLine().split(" ");
        for (int i = 1; i <= n; i++) {
            sw[i] = Integer.parseInt(input[i-1]);
        }

        int s = Integer.parseInt(br.readLine());
        for (int i = 0; i < s; i++) {
			input = br.readLine().split(" ");
            int gender = Integer.parseInt(input[0]);
            int num = Integer.parseInt(input[1]);

            if (gender == 1) {
                for (int j = num; j <= n; j += num) {
                    sw[j] ^= 1;
                }
            } else {
                int left = num - 1;
                int right = num + 1;
                while (left >= 1 && right <= n && sw[left] == sw[right]) {
                    sw[left--] ^= 1;
                    sw[right++] ^= 1;
                }
                sw[num] ^= 1;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; ++i) {
            sb.append(sw[i]).append(" ");
            if (i % 20 == 0) sb.append("\n");
        }
        System.out.println(sb);
    }
}

/*
8
0 1 0 1 0 0 0 1
2
1 3
2 3

1 0 0 0 1 1 0 1 
 */