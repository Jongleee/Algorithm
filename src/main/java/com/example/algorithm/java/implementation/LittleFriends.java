package com.example.algorithm.java.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class LittleFriends {
    private HashMap<Character, int[][]> map;
    private LinkedList<Character> list;
    private char[][] charBoard;

    public String solution(int m, int n, String[] board) {
        StringBuilder answer = new StringBuilder();
        charBoard = new char[m][n];
        list = new LinkedList<>();
        map = new HashMap<>();

        initializeBoard(m, n, board);
        sortList();

        int idx = 0;
        while (!list.isEmpty()) {
            if (canDelete(list.get(idx))) {
                char popped = list.remove(idx);
                answer.append(popped);
                deleteChar(popped);
                idx = 0;
            } else {
                idx++;
                if (idx == list.size()) {
                    return "IMPOSSIBLE";
                }
            }
        }

        return answer.toString();
    }

    private void initializeBoard(int m, int n, String[] board) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = board[i].charAt(j);
                charBoard[i][j] = c;
                if (c != '.' && c != '*') {
                    if (!list.contains(c)) {
                        list.add(c);
                        map.put(c, new int[2][2]);
                        map.get(c)[0][0] = i;
                        map.get(c)[0][1] = j;
                    } else {
                        map.get(c)[1][0] = i;
                        map.get(c)[1][1] = j;
                    }
                }
            }
        }
    }

    private void sortList() {
        Collections.sort(list);
    }

    private void deleteChar(char a) {
        int[][] positions = map.get(a);
        charBoard[positions[0][0]][positions[0][1]] = '.';
        charBoard[positions[1][0]][positions[1][1]] = '.';
    }

    private boolean canDelete(char a) {
        int[][] positions = map.get(a);
        int r1 = positions[0][0];
        int c1 = positions[0][1];
        int r2 = positions[1][0];
        int c2 = positions[1][1];

        if (c1 < c2) {
            if (linearColumnCheck(c1, c2, r1, a) && linearRowCheck(r1, r2, c2, a)) {
                return true;
            }
            if (linearRowCheck(r1, r2, c1, a) && linearColumnCheck(c1, c2, r2, a)) {
                return true;
            }
        } else {
            if (linearRowCheck(r1, r2, c2, a) && linearColumnCheck(c2, c1, r1, a)) {
                return true;
            }
            if (linearColumnCheck(c2, c1, r2, a) && linearRowCheck(r1, r2, c1, a)) {
                return true;
            }
        }

        return false;
    }

    private boolean linearRowCheck(int r1, int r2, int c, char a) {
        for (int i = r1; i < r2 + 1; i++) {
            if (charBoard[i][c] != '.' && charBoard[i][c] != a) {
                return false;
            }
        }
        return true;
    }

    private boolean linearColumnCheck(int c1, int c2, int r, char a) {
        for (int i = c1; i < c2 + 1; i++) {
            if (charBoard[r][i] != '.' && charBoard[r][i] != a) {
                return false;
            }
        }
        return true;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals("ABCD", solution(3, 3, new String[] { "DBA", "C*A", "CDB" }));
    //     Assertions.assertEquals("RYAN", solution(2, 4, new String[] { "NRYN", "ARYA" }));
    //     Assertions.assertEquals("MUZI", solution(4, 4, new String[] { ".ZI.", "M.**", "MZU.", ".IU." }));
    //     Assertions.assertEquals("IMPOSSIBLE", solution(2, 2, new String[] { "AB", "BA" }));
}
