package com.example.algorithm.java.segmentTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class KingKogsReception {
    private static final int MAX = 1 << 21;
    private static final long[] tree = new long[MAX];
    private static final long[] treeMax = new long[MAX];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int queryCount = Integer.parseInt(br.readLine());
        int[] queries = new int[queryCount];

        initializeTree();

        for (int i = 0; i < queryCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            char op = st.nextToken().charAt(0);
            int t = Integer.parseInt(st.nextToken());

            if (op == '+') {
                int d = Integer.parseInt(st.nextToken());
                update(t, d);
                queries[i] = t;
            } else if (op == '-') {
                update(queries[t - 1], 0);
            } else if (op == '?') {
                long result = query(t) - t;
                if (result < 0)
                    result = 0;
                bw.write(result + "\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }

    private static void initializeTree() {
        int n = MAX >> 1;
        for (int i = n; i < 2 * n; i++) {
            treeMax[i] = i - n;
        }
        for (n >>= 1; n >= 1; n >>= 1) {
            for (int i = n; i < 2 * n; i++) {
                treeMax[i] = treeMax[2 * i + 1];
            }
        }
    }

    private static void update(int position, int value) {
        int index = (MAX >> 1) + position;
        tree[index] = value;
        treeMax[index] = index - (MAX >> 1) + value;

        for (int i = index >> 1; i >= 1; i >>= 1) {
            tree[i] = tree[2 * i] + tree[2 * i + 1];
            treeMax[i] = Math.max(treeMax[2 * i + 1], treeMax[2 * i] + tree[2 * i + 1]);
        }
    }

    private static long query(int limit) {
        int index = (MAX >> 1) + limit;
        long sum = 0;
        long maxResult = 0;

        while (index >= 0) {
            if ((index & 1) == 0) {
                maxResult = Math.max(maxResult, treeMax[index] + sum);
                sum += tree[index];
                index--;
            }
            index >>= 1;
        }

        return maxResult;
    }
}

/*
19
? 3
+ 2 2
? 3
? 4
+ 5 2
? 5
? 6
+ 1 2
? 2
? 3
? 4
? 5
? 6
? 7
? 9
- 8
? 2
? 3
? 6

0
1
0
2
1
3
2
1
2
1
0
0
2
1
1
*/