package com.example.algorithm.java.unionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class CardGame {
    static int n, m, k;
    static boolean[] hasCard;
    static int[] nextCard;
    static StringBuilder sb = new StringBuilder();
    static Queue<Integer> compressionQueue = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        hasCard = new boolean[n + 1];
        nextCard = new int[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            hasCard[Integer.parseInt(st.nextToken())] = true;
        }

        for (int i = n - 1; i > 0; i--) {
            nextCard[i] = hasCard[i + 1] ? i + 1 : nextCard[i + 1];
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            play(Integer.parseInt(st.nextToken()));
        }
        System.out.println(sb);
    }

    private static void play(int cheolsuCard) {
        int chosenCard = nextCard[cheolsuCard];
        compressionQueue.add(chosenCard);

        while (!hasCard[chosenCard]) {
            chosenCard = nextCard[chosenCard];
            compressionQueue.add(chosenCard);
        }

        while (!compressionQueue.isEmpty()) {
            nextCard[compressionQueue.poll()] = nextCard[chosenCard];
        }

        hasCard[chosenCard] = false;
        sb.append(chosenCard).append('\n');
    }
}

/*
10 7 5
2 5 3 7 8 4 9
4 1 1 3 8

5
2
3
4
9
*/