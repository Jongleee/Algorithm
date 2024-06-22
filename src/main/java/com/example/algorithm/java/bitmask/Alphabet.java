package com.example.algorithm.java.bitmask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Alphabet {
    static int r;
    static int c;
    static int answer;
    static char[][] map;
    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};
    static int[][] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        map = new char[r + 2][c + 2];
        visit = new int[r + 2][c + 2];
        
        for (int[] row : visit) {
            Arrays.fill(row, -1);
        }

        for (int i = 0; i < r; i++) {
            char[] temp = br.readLine().toCharArray();
            for (int j = 0; j < c; j++) {
                map[i + 1][j + 1] = temp[j];
            }
        }
        
        char wall = map[1][1];
        Arrays.fill(map[0], wall);
        Arrays.fill(map[r + 1], wall);
        for (int i = 0; i < r + 2; i++) {
            map[i][0] = map[i][c + 1] = wall;
        }
        
        answer = 0;
        dfsWithVisit(1, 1, 1, 1 << (map[1][1] - 'A'));
        System.out.println(answer);
    }

    static void dfsWithVisit(int cnt, int r, int c, int bit) {
        answer = Math.max(answer, cnt);
        if (answer == 26) return;

        visit[r][c] = bit;

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if ((bit & (1 << (map[nr][nc] - 'A'))) == 0 &&
                (bit | (1 << (map[nr][nc] - 'A'))) != visit[nr][nc]) {
                dfsWithVisit(cnt + 1, nr, nc, bit | (1 << (map[nr][nc] - 'A')));
            }
        }
    }
}

/*
2 4
CAAB
ADCB

3


3 6
HFDFFB
AJHGDH
DGAGEH

6


5 5
IEFCJ
FHFKC
FFALF
HFGCF
HMCHH

10
 */