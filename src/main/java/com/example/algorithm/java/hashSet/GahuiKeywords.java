package com.example.algorithm.java.hashSet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class GahuiKeywords {
    static int n;
    static int m;
    static Set<String> set = new HashSet<>();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());

        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());

        for (int i = 0; i < n; i++) {
            set.add(br.readLine());
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), ",");
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                set.remove(s);
            }
            sb.append(set.size()).append("\n");
        }

        System.out.print(sb);
    }
}

/*

5 2
map
set
dijkstra
floyd
os
map,dijkstra
map,floyd

3
2

2 2
gt26cw
1211train
kiwoom,lottegiant
kbo

2
2
 */