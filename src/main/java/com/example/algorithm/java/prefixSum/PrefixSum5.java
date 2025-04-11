package com.example.algorithm.java.prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class PrefixSum5 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(br);
        int size = nextInput(st);
        int queryCount = nextInput(st);

        int[][] prefixSum = new int[size + 1][size + 1];
        for (int i = 1; i <= size; i++) {
            st = new StreamTokenizer(br);
            for (int j = 1; j <= size; j++) {
                int value = nextInput(st);
                prefixSum[i][j] = prefixSum[i][j - 1] + prefixSum[i - 1][j]
                        - prefixSum[i - 1][j - 1] + value;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < queryCount; i++) {
            st = new StreamTokenizer(br);
            int x1 = nextInput(st);
            int y1 = nextInput(st);
            int x2 = nextInput(st);
            int y2 = nextInput(st);
            int sum = prefixSum[x2][y2] - prefixSum[x2][y1 - 1]
                    - prefixSum[x1 - 1][y2] + prefixSum[x1 - 1][y1 - 1];

            sb.append(sum).append('\n');
        }

        System.out.print(sb);
    }

    private static int nextInput(StreamTokenizer st) throws IOException {
        st.nextToken();
        return (int) st.nval;
    }
}

/*
2 4
1 2
3 4
1 1 1 1
1 2 1 2
2 1 2 1
2 2 2 2

1
2
3
4


4 3
1 2 3 4
2 3 4 5
3 4 5 6
4 5 6 7
2 2 3 4
3 4 3 4
1 1 4 4

27
6
64
*/