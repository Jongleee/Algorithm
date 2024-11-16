package com.example.algorithm.java.implementation;

public class WalkingPark {
    public int[] solution(String[] park, String[] routes) {
        int sx = 0;
        int sy = 0;

        char[][] arr = new char[park.length][park[0].length()];

        for (int i = 0; i < park.length; i++) {
            arr[i] = park[i].toCharArray();

            if (park[i].contains("S")) {
                sy = i;
                sx = park[i].indexOf("S");
            }
        }

        for (String st : routes) {
            String way = st.split(" ")[0];
            int len = Integer.parseInt(st.split(" ")[1]);

            int nx = sx;
            int ny = sy;

            for (int i = 0; i < len; i++) {
                switch (way) {
                    case "E":
                        nx++;
                        break;
                    case "W":
                        nx--;
                        break;
                    case "S":
                        ny++;
                        break;
                    case "N":
                        ny--;
                        break;
                    default:
                        break;
                }
                if (nx < 0 || ny < 0 || ny >= arr.length || nx >= arr[0].length || arr[ny][nx] == 'X') {
                    break;
                }
                if (i == len - 1) {
                    sx = nx;
                    sy = ny;
                }
            }
        }

        return new int[] { sy, sx };
    }
    // @Test
    // public void 정답() {
    // Assertions.assertEquals("[2, 1]",
    // Arrays.toString(solution(new String[] { "SOO", "OOO", "OOO" }, new String[] {
    // "E 2", "S 2", "W 1" })));
    // Assertions.assertEquals("[0, 1]",
    // Arrays.toString(solution(new String[] { "SOO", "OXX", "OOO" }, new String[] {
    // "E 2", "S 2", "W 1" })));
    // Assertions.assertEquals("[0, 0]", Arrays
    // .toString(solution(new String[] { "OSO", "OOO", "OXO", "OOO" }, new String[]
    // { "E 2", "S 3", "W 1" })));
    // }
}
