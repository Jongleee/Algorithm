import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public boolean solution(int[][] key, int[][] lock) {
        int m = key.length;
        int n = lock.length;

        int len = n + m * 2 - 2;
        int[][] extendedMap = extendMap(lock, m, n, len);

        for (int i = 0; i < 4; i++) {
            if (check(extendedMap, key, n)) {
                return true;
            }
            rotate(key);
        }

        return false;
    }

    private int[][] extendMap(int[][] lock, int m, int n, int len) {
        int[][] extendedMap = new int[len][len];

        for (int i = m - 1; i < m + n - 1; i++) {
            for (int j = m - 1; j < m + n - 1; j++) {
                extendedMap[i][j] = lock[i - (m - 1)][j - (m - 1)];
            }
        }

        return extendedMap;
    }

    private boolean check(int[][] map, int[][] key, int lockLen) {
        int keyLen = key.length;
        int mapLen = map.length;

        for (int i = 0; i < mapLen - keyLen + 1; i++) {
            for (int j = 0; j < mapLen - keyLen + 1; j++) {

                addKey(map, key, keyLen, i, j);

                if (checkKey(map, keyLen, lockLen))
                    return true;

                removeKey(map, key, keyLen, i, j);
            }
        }

        return false;
    }

    private void addKey(int[][] map, int[][] key, int keyLen, int i, int j) {
        for (int k = 0; k < keyLen; k++) {
            for (int l = 0; l < keyLen; l++) {
                map[i + k][j + l] += key[k][l];
            }
        }
    }

    private boolean checkKey(int[][] map, int keyLen, int lockLen) {
        for (int k = keyLen - 1; k < keyLen + lockLen - 1; k++) {
            for (int l = keyLen - 1; l < keyLen + lockLen - 1; l++) {
                if (map[k][l] != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void removeKey(int[][] map, int[][] key, int keyLen, int i, int j) {
        for (int k = 0; k < keyLen; k++) {
            for (int l = 0; l < keyLen; l++) {
                map[i + k][j + l] -= key[k][l];
            }
        }
    }

    private void rotate(int[][] key) {
        int len = key.length;
        int[][] temp = new int[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                temp[i][j] = key[len - j - 1][i];
            }
        }

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                key[i][j] = temp[i][j];
            }
        }
    }

    @Test
    void 정답() {
        int[][] key = { { 0, 0, 0 }, { 1, 0, 0 }, { 0, 1, 1 } };
        int[][] lock = { { 1, 1, 1 }, { 1, 1, 0 }, { 1, 0, 1 } };
        Assertions.assertEquals(true, solution(key, lock));
    }
}
