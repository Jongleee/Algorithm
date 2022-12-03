package com.example.algorithm.practice;

public class LockAndKey {
    public static void main(String[] args) {
        System.out.println(
                solution(new int[][]{{0, 0, 0}, {1, 0, 0}, {0, 1, 1}}, new int[][]{{1, 1, 1}, {1, 1, 0}, {1, 0, 1}})
        );
    }

    public static boolean solution(int[][] key, int[][] lock) {
        boolean answer = true;

        int m = key.length;
        int n = lock.length;

        int len = n + m * 2 - 2;
        int[][] map = new int[len][len];

        for (int i = m - 1; i < m + n - 1; i++) {
            for (int j = m - 1; j < m + n - 1; j++) {
                map[i][j] = lock[i - (m - 1)][j - (m - 1)];
            }
        }

        for (int i = 0; i < 4; i++) {
            if (check(map, key, n)) {
                return true;
            }
            rotate(key);
        }


        return false;
    }

    public static boolean check(int[][] map, int[][] key, int lockLen){
        int keyLen = key.length;
        int mapLen = map.length;
        for(int i=0; i<mapLen-keyLen+1; i++){
            for(int j=0; j<mapLen-keyLen+1; j++){

                for(int k=0; k<keyLen; k++){
                    for(int l=0; l<keyLen; l++){
                        map[i+k][j+l] += key[k][l];
                    }
                }

                boolean flag = true;
                for(int k=keyLen-1; k<keyLen+lockLen-1; k++){
                    for(int l=keyLen-1; l<keyLen+lockLen-1; l++){
                        if(map[k][l] != 1){
                            flag = false;
                            break;
                        }
                    }
                    if(!flag) break;
                }

                if(flag) return true;

                for(int k=0; k<keyLen; k++){
                    for(int l=0; l<keyLen; l++){
                        map[i+k][j+l] -= key[k][l];
                    }
                }

            }
        }

        return false;
    }

    public static void rotate(int[][] key) {
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
}
