import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        char[][] copy = new char[m][n];

        for (int i = 0; i < m; i++) {
            copy[i] = board[i].toCharArray();
        }

        boolean flag = true;

        while (flag) {
            flag = false;
            boolean[][] v = new boolean[m][n];

            for (int i = 0; i < m - 1; i++) {
                for (int j = 0; j < n - 1; j++) {
                    if (copy[i][j] != '-' && check(i, j, copy)) {
                        v[i][j] = true;
                        v[i][j + 1] = true;
                        v[i + 1][j] = true;
                        v[i + 1][j + 1] = true;
                        flag = true;
                    }
                }
            }

            answer += erase(m, n, copy, v);
        }

        return answer;
    }

    public boolean check(int x, int y, char[][] board) {
        char ch = board[x][y];
        return ch == board[x][y + 1] && ch == board[x + 1][y] && ch == board[x + 1][y + 1];
    }

    public int erase(int m, int n, char[][] board, boolean[][] v) {
        int cnt = 0;

        for (int j = 0; j < n; j++) {
            Queue<Character> q = new LinkedList<>();
            for (int i = m - 1; i >= 0; i--) {
                if (v[i][j]) {
                    cnt++;
                } else if (board[i][j] != '-') {
                    q.add(board[i][j]);
                }
            }

            for (int i = m - 1; i >= 0; i--) {
                if (!q.isEmpty()) {
                    board[i][j] = q.poll();
                } else {
                    board[i][j] = '-';
                }
            }
        }

        return cnt;
    }

    @Test
    void 정답() {
        String[] board1 = { "CCBDE", "AAADE", "AAABF", "CCBBF" };
        String[] board2 = { "TTTANT", "RRFACC", "RRRFCC", "TRRRAA", "TTMMMF", "TMMTTJ" };

        Assertions.assertEquals(14, solution(4, 5, board1));
        Assertions.assertEquals(15, solution(6, 6, board2));
    }
}
