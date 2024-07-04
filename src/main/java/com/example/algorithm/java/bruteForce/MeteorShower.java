package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MeteorShower {
    public static class Star {
        int x;
        int y;

        public Star(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        st.nextToken();
        st.nextToken();
        int l = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        Star[] stars = new Star[k];

        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            stars[i] = new Star(x, y);
        }

        int maxStarsInBounds = Integer.MIN_VALUE;

        for (Star s1 : stars) {
            for (Star s2 : stars) {
                maxStarsInBounds = Math.max(maxStarsInBounds, countStarsInBounds(s1.x, s2.y, stars, l));
            }
        }

        System.out.println(k - maxStarsInBounds);
        br.close();
    }

    public static int countStarsInBounds(int x, int y, Star[] stars, int l) {
        int count = 0;
        for (Star s : stars) {
            if (x <= s.x && s.x <= x + l && y <= s.y && s.y <= y + l) {
                count++;
            }
        }
        return count;
    }
}

/*
12 10 4 7
2 4
7 3
3 1
5 6
4 7
12 10
8 6

3
 */