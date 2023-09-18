package com.example.algorithm.java.searchDFS;

public class MagicElevator {
    public int solution(int storey) {
        return moveElevator(storey);
    }

    private int moveElevator(int storey) {
        if (storey <= 1) {
            return storey;
        }

        int floorDividedBy10 = storey / 10;
        int floorModulo10 = storey % 10;

        int goUp = floorModulo10 + moveElevator(floorDividedBy10);
        int goDown = (10 - floorModulo10) + moveElevator(floorDividedBy10 + 1);

        return Math.min(goUp, goDown);
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(6, solution(16));
    //     Assertions.assertEquals(16, solution(2554));
    // }
}
