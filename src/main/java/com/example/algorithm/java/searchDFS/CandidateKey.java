package com.example.algorithm.java.searchDFS;

import java.util.HashSet;

public class CandidateKey {
    static String[][] relation;
    static HashSet<String> set;

    public static int solution(String[][] input) {
        relation = input;

        set = new HashSet<>();

        for (int i = 1; i <= relation[0].length; i++) {
            boolean[] selected = new boolean[relation[0].length];
            dfs(0, 0, i, selected);
        }

        return set.size();
    }

    public static void dfs(int idx, int cnt, int max, boolean[] selected) {
        if (cnt == max) {

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    sb.append(i);
                }
            }

            String cols = sb.toString();
            if (isCandidateKey(cols, selected)) {
                set.add(cols);
            }
            return;
        }

        if (idx >= selected.length)
            return;

        selected[idx] = true;
        dfs(idx + 1, cnt + 1, max, selected);

        selected[idx] = false;
        dfs(idx + 1, cnt, max, selected);
    }

    public static boolean isCandidateKey(String candidate, boolean[] selected) {
        for (String s : set) {
            boolean flag = true;
            for (int i = 0; i < s.length(); i++) {
                if (!candidate.contains(s.charAt(i) + "")) {
                    flag = false;
                }
            }

            if (flag) {
                return false;
            }
        }

        HashSet<String> values = new HashSet<>();
        int[] columnIndex = makeIndex(candidate, selected);

        for (int i = 0; i < relation.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < columnIndex.length; j++) {
                sb.append(relation[i][columnIndex[j]]);
            }
            String value = sb.toString();
            if (values.contains(value)) {
                return false;
            } else {
                values.add(value);
            }
        }

        return true;
    }

    private static int[] makeIndex(String candidate, boolean[] selected) {
        int[] columnIndices = new int[candidate.length()];
        int index = 0;
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                columnIndices[index] = i;
                index++;
            }
        }
        return columnIndices;
    }

    public static void main(String[] args) {
        String[][] r1 = { { "100", "ryan", "music", "2" }, { "200", "apeach", "math", "2" },
                { "300", "tube", "computer", "3" }, { "400", "con", "computer", "4" }, { "500", "muzi", "music", "3" },
                { "600", "apeach", "music", "2" } };
        System.out.println(solution(r1));
    }
}
