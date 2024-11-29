package com.example.algorithm.java.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShortcutAssignment {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        boolean[] isCheck = new boolean[26];

        StringBuilder sb = new StringBuilder();

        for (int t = 0; t < n; t++) {
            String option = br.readLine();
            String str = findRepresentativeKey(option, isCheck);

            sb.append(str).append("\n");
        }

        System.out.println(sb);
    }

    static String findRepresentativeKey(String option, boolean[] isCheck) {
        for (int i = 0; i < option.length(); i++) {
            char alphabet = option.charAt(i);

            if (alphabet >= 'A' && alphabet <= 'Z') {
                alphabet = (char) ('a' + (alphabet - 'A'));
            }
            int num = alphabet - 'a';

            if (!isCheck[num]) {
                isCheck[num] = true;
                return makeKey(option, i);
            }

            while (true) {
                i++;
                if (i >= option.length() || option.charAt(i) == ' ')
                    break;
            }
        }

        for (int i = 1; i < option.length(); i++) {
            if (option.charAt(i) == ' ') {
                i++;
                continue;
            }

            char alphabet = option.charAt(i);

            if (alphabet >= 'A' && alphabet <= 'Z') {
                alphabet = (char) ('a' + (alphabet - 'A'));
            }
            int num = alphabet - 'a';

            if (!isCheck[num]) {
                isCheck[num] = true;
                return makeKey(option, i);
            }
        }

        return option;
    }

    static String makeKey(String option, int idx) {
        StringBuilder sb = new StringBuilder();
        sb.append(option, 0, idx);
        sb.append("[").append(option.charAt(idx)).append("]");
        sb.append(option.substring(idx + 1));

        return sb.toString();
    }
}

/*
8
New window
New file
Copy
Undo
Format
Font
Cut
Paste

[N]ew window
New [f]ile
[C]opy
[U]ndo
F[o]rmat
Fon[t]
Cut
[P]aste
*/