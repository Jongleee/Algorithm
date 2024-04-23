package com.example.algorithm.java.hashSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class MinigameWithIms {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        char ch = st.nextToken().charAt(0);

        HashSet<String> hs = new HashSet<>();
        while (n-- > 0) {
            hs.add(br.readLine());
        }

        int ans = hs.size();
        switch (ch) {
            case 'F':
                ans /= 2;
                break;
            case 'O':
                ans /= 3;
                break;
            default:
                break;
        }

        System.out.print(ans);
    }
}
/*
7 Y
lms0806
lms0806
exponentiale
lms0806
jthis
lms0806
leo020630

4

12 F
lms0806
powergee
skeep194
lms0806
tony9402
lms0806
wider93
lms0806
mageek2guanaah
lms0806
jthis
lms0806

3
 */