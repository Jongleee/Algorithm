package com.example.algorithm.java.bitmask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SetProblem {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int m = Integer.parseInt(br.readLine());

        int s = 0;
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String command = st.nextToken();

            switch (command) {
                case "all":
                    s = (1 << 21) - 1;
                    break;
                case "empty":
                    s = 0;
                    break;
                default:
                    int x = Integer.parseInt(st.nextToken());
                    switch (command) {
                        case "add":
                            s |= (1 << x);
                            break;
                        case "remove":
                            s &= ~(1 << x);
                            break;
                        case "check":
                            sb.append((s & (1 << x)) != 0 ? 1 : 0).append('\n');
                            break;
                        case "toggle":
                            s ^= (1 << x);
                            break;
                        default:
                            break;
                    }
            }
        }

        System.out.println(sb);
    }
}
/*
26
add 1
add 2
check 1
check 2
check 3
remove 2
check 1
check 2
toggle 3
check 1
check 2
check 3
check 4
all
check 10
check 20
toggle 10
remove 20
check 10
check 20
empty
check 1
toggle 1
check 1
toggle 1
check 1

1
1
0
1
0
1
0
1
0
1
1
0
0
0
1
0
 */