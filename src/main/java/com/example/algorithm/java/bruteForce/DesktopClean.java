package com.example.algorithm.java.bruteForce;

public class DesktopClean {
    public int[] solution(String[] wallpaper) {
        int minRow = Integer.MAX_VALUE;
        int minCol = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;
        int maxCol = Integer.MIN_VALUE;

        int row = wallpaper.length;
        int col = wallpaper[0].length();

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (wallpaper[r].charAt(c) == '#') {
                    minRow = Math.min(minRow, r);
                    minCol = Math.min(minCol, c);
                    maxRow = Math.max(maxRow, r);
                    maxCol = Math.max(maxCol, c);
                }
            }
        }

        return new int[] { minRow, minCol, maxRow + 1, maxCol + 1 };
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals("[0, 1, 3, 4]",
    //             Arrays.toString(solution(new String[] { ".#...", "..#..", "...#." })));
    //     Assertions.assertEquals("[1, 3, 5, 8]",
    //             Arrays.toString(solution(
    //                     new String[] { "..........", ".....#....", "......##..", "...##.....", "....#....." })));
    // }
}
