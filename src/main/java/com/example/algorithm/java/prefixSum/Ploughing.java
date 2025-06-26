package com.example.algorithm.java.prefixSum;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Ploughing {
    static int k, m, n;
    static int[][] rowPrefixSum, colPrefixSum;
    static int[] borderSum = new int[4];

    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        k = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        rowPrefixSum = new int[n][m + 1];
        colPrefixSum = new int[n + 1][m];

        for (int i = 0; i < n; i++) {
            if (i % 1000 == 500)
                System.gc();
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                int v = Integer.parseInt(st.nextToken());
                rowPrefixSum[i][j + 1] = rowPrefixSum[i][j] + v;
                colPrefixSum[i + 1][j] = colPrefixSum[i][j] + v;
            }
        }

        int max = 1;
        int start = 1, end = 1;
        while (end < m) {
            if (end % 1000 == 500)
                System.gc();
            end++;
            if (!checkHorizontal(start, end))
                start++;
            else
                max = Math.max(max, end - start + 1);
        }

        start = 1;
        end = 1;
        while (end < n) {
            if (end % 1000 == 500)
                System.gc();
            end++;
            if (!checkVertical(start, end))
                start++;
            else
                max = Math.max(max, end - start + 1);
        }

        bw.write(Long.toString(n + m - max));
        bw.flush();
        bw.close();
    }

    static int rowSum(int a, int b, int row) {
        return rowPrefixSum[row - 1][b] - rowPrefixSum[row - 1][a - 1];
    }

    static int colSum(int a, int b, int col) {
        return colPrefixSum[b][col - 1] - colPrefixSum[a - 1][col - 1];
    }

    static boolean checkHorizontal(int start, int end) {
        int colStart = 1, colEnd = m, rowStart = 1, rowEnd = n;
        borderSum[0] = colSum(1, n, 1);
        borderSum[1] = rowSum(1, m, 1);
        borderSum[2] = rowSum(1, m, n);
        borderSum[3] = colSum(1, n, m);

        while (true) {
            if (rowStart == rowEnd && borderSum[1] <= k)
                return true;
            if (rowStart < rowEnd && (borderSum[1] <= k || borderSum[2] <= k)) {
                if (borderSum[1] <= k) {
                    borderSum[1] = rowSum(colStart, colEnd, ++rowStart);
                } else {
                    borderSum[2] = rowSum(colStart, colEnd, --rowEnd);
                }
                borderSum[0] = colSum(rowStart, rowEnd, colStart);
                borderSum[3] = colSum(rowStart, rowEnd, colEnd);
            } else if (colStart < start && borderSum[0] <= k) {
                borderSum[0] = colSum(rowStart, rowEnd, ++colStart);
                borderSum[1] = rowSum(colStart, colEnd, rowStart);
                borderSum[2] = rowSum(colStart, colEnd, rowEnd);
            } else if (colEnd > end && borderSum[3] <= k) {
                borderSum[3] = colSum(rowStart, rowEnd, --colEnd);
                borderSum[1] = rowSum(colStart, colEnd, rowStart);
                borderSum[2] = rowSum(colStart, colEnd, rowEnd);
            } else
                return false;
        }
    }

    static boolean checkVertical(int start, int end) {
        int colStart = 1, colEnd = m, rowStart = 1, rowEnd = n;
        borderSum[0] = colSum(1, n, 1);
        borderSum[1] = rowSum(1, m, 1);
        borderSum[2] = rowSum(1, m, n);
        borderSum[3] = colSum(1, n, m);

        while (true) {
            if (colStart == colEnd && borderSum[0] <= k)
                return true;
            if (colStart < colEnd && (borderSum[0] <= k || borderSum[3] <= k)) {
                if (borderSum[0] <= k) {
                    borderSum[0] = colSum(rowStart, rowEnd, ++colStart);
                } else {
                    borderSum[3] = colSum(rowStart, rowEnd, --colEnd);
                }
                borderSum[1] = rowSum(colStart, colEnd, rowStart);
                borderSum[2] = rowSum(colStart, colEnd, rowEnd);
            } else if (rowStart < start && borderSum[1] <= k) {
                borderSum[1] = rowSum(colStart, colEnd, ++rowStart);
                borderSum[0] = colSum(rowStart, rowEnd, colStart);
                borderSum[3] = colSum(rowStart, rowEnd, colEnd);
            } else if (rowEnd > end && borderSum[2] <= k) {
                borderSum[2] = rowSum(colStart, colEnd, --rowEnd);
                borderSum[0] = colSum(rowStart, rowEnd, colStart);
                borderSum[3] = colSum(rowStart, rowEnd, colEnd);
            } else
                return false;
        }
    }
}

/*
12 6 4
6 0 4 8 0 5
0 4 5 4 6 0
0 5 6 5 6 0
5 4 0 0 5 4

8
*/