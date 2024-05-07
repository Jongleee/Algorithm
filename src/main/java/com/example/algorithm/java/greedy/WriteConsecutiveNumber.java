package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class WriteConsecutiveNumber {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        String num = st.nextToken();
        int len = num.length();
        int[] nums = new int[len];
        for(int i = 0; i < len; i++) {
            nums[i] = num.charAt(i) - '0';
        }
        
        int idx = 0;
        int n = 0;
        while(idx < len) {
            n++;
            int temp = n;
            int length = (int)(Math.log10(n) + 1);
            int[] arr = new int[length];
            for(int i = length - 1; i >= 0; i--) {
                arr[i] = temp % 10;
                temp /= 10;
            }
            for(int i = 0; i < length; i++) {
                if(nums[idx] == arr[i])
                    idx++;
                if(idx >= len)
                    break;
            }
        }
        bw.write(n + "");
        bw.close();
    }
}

/*
1234

4

82340329923

43


32098221

61


1111111

14

00000000000000000000000000000000000000000000000000000000000000000000000

400

345029834023049820394802334909240982039842039483294792934790209

279
 */