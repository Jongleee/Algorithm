package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TreeInvestment {
    static class Tree {
        int age, count;
        Tree next, prev;

        Tree(int age, int count) {
            this.age = age;
            this.count = count;
        }
    }

    static class TreeList {
        Tree head;

        TreeList() {
            head = new Tree(0, 0);
        }

        void addFirst(Tree tree) {
            tree.prev = head;
            if (head.next != null) {
                tree.next = head.next;
                head.next.prev = tree;
            }
            head.next = tree;
        }

        void remove(Tree tree) {
            tree.prev.next = null;
        }
    }

    private static int n, k;
    private static int[][] nutrientAdditions;
    private static int[][] nutrients;
    private static TreeList[][] treeLists;
    private static final int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
    private static final int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        initialize(br);

        while (k-- > 0) {
            springAndSummer();
            fall();
            winter();
        }

        System.out.println(countAllTrees());
    }

    private static void initialize(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        nutrientAdditions = new int[n + 1][n + 1];
        nutrients = new int[n + 1][n + 1];
        treeLists = new TreeList[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            Arrays.fill(nutrients[i], 5);
        }

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                nutrientAdditions[i][j] = Integer.parseInt(st.nextToken());
                treeLists[i][j] = new TreeList();
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int age = Integer.parseInt(st.nextToken());
            treeLists[x][y].addFirst(new Tree(age, 1));
        }
    }

    private static void springAndSummer() {
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                processCellInSpringAndSummer(x, y);
            }
        }
    }

    private static void processCellInSpringAndSummer(int x, int y) {
        Tree deadTree = null;
        boolean canSurvive = true;
        Tree current = treeLists[x][y].head.next;

        while (current != null) {
            if (canSurvive) {
                int totalNutrientRequired = current.age * current.count;
                if (nutrients[x][y] >= totalNutrientRequired) {
                    nutrients[x][y] -= totalNutrientRequired;
                    current.age++;
                } else {
                    int survivingCount = nutrients[x][y] / current.age;
                    nutrients[x][y] -= current.age * survivingCount;
                    nutrients[x][y] += (current.count - survivingCount) * (current.age / 2);

                    if (survivingCount > 0) {
                        current.count = survivingCount;
                        current.age++;
                        deadTree = current.next;
                    } else {
                        deadTree = current;
                    }
                    canSurvive = false;
                }
            } else {
                nutrients[x][y] += current.count * (current.age / 2);
            }
            current = current.next;
        }

        if (deadTree != null) {
            treeLists[x][y].remove(deadTree);
        }
    }

    private static void fall() {
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                spreadSeeds(x, y);
            }
        }
    }

    private static void spreadSeeds(int x, int y) {
        Tree current = treeLists[x][y].head.next;
        while (current != null) {
            if (current.age % 5 == 0) {
                for (int i = 0; i < dx.length; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];
                    if (isValid(nx, ny)) {
                        addNewTree(nx, ny, current.count);
                    }
                }
            }
            current = current.next;
        }
    }

    private static void addNewTree(int x, int y, int count) {
        Tree existingTree = treeLists[x][y].head.next;
        if (existingTree != null && existingTree.age == 1) {
            existingTree.count += count;
        } else {
            treeLists[x][y].addFirst(new Tree(1, count));
        }
    }

    private static void winter() {
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                nutrients[x][y] += nutrientAdditions[x][y];
            }
        }
    }

    private static int countAllTrees() {
        int total = 0;
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                Tree current = treeLists[x][y].head.next;
                while (current != null) {
                    total += current.count;
                    current = current.next;
                }
            }
        }
        return total;
    }

    private static boolean isValid(int x, int y) {
        return x >= 1 && x <= n && y >= 1 && y <= n;
    }
}

/*
5 2 6
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 1 3
3 2 3

85


5 2 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 1 3
3 2 3

15
*/