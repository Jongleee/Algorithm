import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    private boolean[][] pillar;
    private boolean[][] bar;

    public int[][] solution(int n, int[][] buildFrame) {
        pillar = new boolean[n + 1][n + 1];
        bar = new boolean[n + 1][n + 1];

        int count = 0;
        for (int[] build : buildFrame) {
            int x = build[0];
            int y = build[1];
            int type = build[2];
            int action = build[3];

            switch (type) {
                case 0:
                    count = processPillar(n, count, x, y, action);
                    break;

                case 1:
                    count = processBar(n, count, x, y, action);
                    break;

                default:
                    break;
            }
        }

        return getResultArray(count);
    }

    private int processBar(int n, int count, int x, int y, int action) {
        if (action == 1) {
            if (checkBar(x, y)) {
                bar[x][y] = true;
                count++;
            }
        } else {
            bar[x][y] = false;
            if (!canDelete(n))
                bar[x][y] = true;
            else
                count--;
        }
        return count;
    }

    private int processPillar(int n, int count, int x, int y, int action) {
        if (action == 1) {
            if (checkPillar(x, y)) {
                pillar[x][y] = true;
                count++;
            }
        } else {
            pillar[x][y] = false;
            if (!canDelete(n))
                pillar[x][y] = true;
            else
                count--;
        }
        return count;
    }

    private int[][] getResultArray(int count) {
        int[][] result = new int[count][3];
        int idx = 0;

        for (int i = 0; i < pillar.length; i++) {
            for (int j = 0; j < pillar[i].length; j++) {
                if (pillar[i][j]) {
                    result[idx][0] = i;
                    result[idx][1] = j;
                    result[idx++][2] = 0;
                }
                if (bar[i][j]) {
                    result[idx][0] = i;
                    result[idx][1] = j;
                    result[idx++][2] = 1;
                }
            }
        }

        return result;
    }

    private boolean canDelete(int n) {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (pillar[i][j] && !checkPillar(i, j))
                    return false;
                if (bar[i][j] && !checkBar(i, j))
                    return false;
            }
        }
        return true;
    }

    private boolean checkPillar(int x, int y) {
        return (y == 0) || (y > 0 && pillar[x][y - 1]) || (x > 0 && bar[x - 1][y] || bar[x][y]);
    }

    private boolean checkBar(int x, int y) {
        return (y > 0 && pillar[x][y - 1] || pillar[x + 1][y - 1]) || (x > 0 && bar[x - 1][y] && bar[x + 1][y]);
    }

    @Test
    void 정답() {
        int[][] b1 = {
                { 1, 0, 0, 1 }, { 1, 1, 1, 1 }, { 2, 1, 0, 1 }, { 2, 2, 1, 1 },
                { 5, 0, 0, 1 }, { 5, 1, 0, 1 }, { 4, 2, 1, 1 }, { 3, 2, 1, 1 }
        };
        int[][] r1 = {
                { 1, 0, 0 }, { 1, 1, 1 }, { 2, 1, 0 }, { 2, 2, 1 },
                { 3, 2, 1 }, { 4, 2, 1 }, { 5, 0, 0 }, { 5, 1, 0 }
        };
        int[][] b2 = {
                { 0, 0, 0, 1 }, { 2, 0, 0, 1 }, { 4, 0, 0, 1 }, { 0, 1, 1, 1 },
                { 1, 1, 1, 1 }, { 2, 1, 1, 1 }, { 3, 1, 1, 1 }, { 2, 0, 0, 0 }, { 1, 1, 1, 0 }, { 2, 2, 0, 1 }
        };
        int[][] r2 = {
                { 0, 0, 0 }, { 0, 1, 1 }, { 1, 1, 1 },
                { 2, 1, 1 }, { 3, 1, 1 }, { 4, 0, 0 }
        };
        Assertions.assertArrayEquals(r1, solution(5,b1));
        Assertions.assertArrayEquals(r2, solution(5,b2));
    }
}
