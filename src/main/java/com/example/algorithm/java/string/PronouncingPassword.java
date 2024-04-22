package com.example.algorithm.java.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PronouncingPassword {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String password;
        StringBuilder sb = new StringBuilder();
        
        while (!(password = in.readLine()).equals("end")) {
            if (isAcceptable(password)) {
                sb.append('<').append(password).append("> is acceptable.\n");
            } else {
                sb.append('<').append(password).append("> is not acceptable.\n");
            }
        }

        System.out.println(sb);
    }

    public static boolean isAcceptable(String password) {
        char[] pass = password.toCharArray();
        char prev = '.';
        int count = 0;
        boolean flag = false;

        for (char p : pass) {
            if (isVowel(p)) flag = true;

            if (isVowel(p) != isVowel(prev)) count = 1;
            else count++;

            if (count > 2 || (prev == p && (p != 'e' && p != 'o'))) {
                return false;
            }

            prev = p;
        }

        return flag;
    }

    public static boolean isVowel(char ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }
}
/*
a
tv
ptoui
bontres
zoggax
wiinq
eep
houctuh
end

<a> is acceptable.
<tv> is not acceptable.
<ptoui> is not acceptable.
<bontres> is not acceptable.
<zoggax> is not acceptable.
<wiinq> is not acceptable.
<eep> is acceptable.
<houctuh> is acceptable.
*/