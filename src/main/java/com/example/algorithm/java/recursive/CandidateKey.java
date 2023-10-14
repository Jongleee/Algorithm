package com.example.algorithm.java.recursive;

import java.util.HashSet;
import java.util.Set;

public class CandidateKey {
    private String[][] relation;
    private Set<String> candidateKeys;

    public int solution(String[][] input) {
        relation = input;
        candidateKeys = new HashSet<>();

        int columnCount = relation[0].length;

        for (int i = 1; i <= columnCount; i++) {
            boolean[] selected = new boolean[columnCount];
            generateCandidateKeys(0, 0, i, selected);
        }

        return candidateKeys.size();
    }

    private void generateCandidateKeys(int idx, int count, int max, boolean[] selected) {
        if (count == max) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    sb.append(i);
                }
            }

            String cols = sb.toString();
            if (isCandidateKey(cols, selected)) {
                candidateKeys.add(cols);
            }
            return;
        }

        if (idx >= selected.length)
            return;

        selected[idx] = true;
        generateCandidateKeys(idx + 1, count + 1, max, selected);

        selected[idx] = false;
        generateCandidateKeys(idx + 1, count, max, selected);
    }

    private boolean isCandidateKey(String candidate, boolean[] selected) {
        for (String key : candidateKeys) {
            boolean containsAllChars = true;
            for (int i = 0; i < key.length(); i++) {
                if (!candidate.contains(String.valueOf(key.charAt(i)))) {
                    containsAllChars = false;
                    break;
                }
            }

            if (containsAllChars) {
                return false;
            }
        }

        Set<String> values = new HashSet<>();
        int[] columnIndex = makeColumnIndices(candidate, selected);

        for (String[] row : relation) {
            StringBuilder sb = new StringBuilder();
            for (int index : columnIndex) {
                sb.append(row[index]);
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

    private int[] makeColumnIndices(String candidate, boolean[] selected) {
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

    // @Test
    // void 정답() {
    //     String[][] r1 = { { "100", "ryan", "music", "2" }, { "200", "apeach", "math", "2" },
    //             { "300", "tube", "computer", "3" }, { "400", "con", "computer", "4" }, { "500", "muzi", "music", "3" },
    //             { "600", "apeach", "music", "2" } };
    //     Assertions.assertEquals(2, solution(r1));
    // }
}
