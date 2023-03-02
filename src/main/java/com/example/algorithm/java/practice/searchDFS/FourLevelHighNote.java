package com.example.algorithm.java.practice.searchDFS;

public class FourLevelHighNote {

    private int answer;

    public int solution1(int n) {
        answer = 0;
        dfs(n, 0);
        return answer;
    }

    private void dfs(int value, int cnt) {
        if (value < 1 || 2 * Math.log(value) / Math.log(3) < cnt)
            return;
        if (value == 3) {
            if (cnt == 2)
                answer++;
            return;
        }

        if (value % 3 == 0 && cnt >= 2) {
            dfs(value / 3, cnt - 2);
        }
        dfs(value - 1, cnt + 1);
    }

    public int solution2(int n) {
        return dfs2(n, 0);
    }

    private int dfs2(int value, int cnt) {
        if (value < 1 || 2 * Math.log(value) / Math.log(3) < cnt) {
            return 0;
        }
        if (value == 3) {
            return (cnt == 2) ? 1 : 0;
        }

        int result = 0;
        if (value % 3 == 0 && cnt >= 2) {
            result += dfs2(value / 3, cnt - 2);
        }
        result += dfs2(value - 1, cnt + 1);
        return result;
    }
}
