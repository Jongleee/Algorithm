package com.example.algorithm.java.example;

public class UnionFindExample {
    class UnionFind {
        int[] parent;
        int[] size;
        int count;

        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            count = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (size[rootX] > size[rootY]) {
                    parent[rootY] = rootX;
                    size[rootX] += size[rootY];
                } else {
                    parent[rootX] = rootY;
                    size[rootY] += size[rootX];
                }
                count--;
            }
        }

        public int count() {
            return count;
        }
    }
}
