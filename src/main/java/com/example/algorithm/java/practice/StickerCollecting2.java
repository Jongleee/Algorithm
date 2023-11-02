package com.example.algorithm.java.practice;

public class StickerCollecting2 {
    public int solution(int[] sticker) {
        int len = sticker.length;

        if (len == 1)
            return sticker[0];

        int[] withFirst = new int[len];
        int[] withoutFirst = new int[len];

        withFirst[0] = sticker[0];
        withFirst[1] = sticker[0];
        withoutFirst[0] = 0;
        withoutFirst[1] = sticker[1];

        for (int i = 2; i < len; i++) {
            withFirst[i] = Math.max(withFirst[i - 1], withFirst[i - 2] + sticker[i]);
            withoutFirst[i] = Math.max(withoutFirst[i - 1], withoutFirst[i - 2] + sticker[i]);
        }

        return Math.max(withFirst[len - 2], withoutFirst[len - 1]);
    }

    // @Test
    // void 정답() {
    //     Assertions.assertEquals(36, solution(new int[] { 14, 6, 5, 11, 3, 9, 2, 10 }));
    //     Assertions.assertEquals(8, solution(new int[] { 1, 3, 2, 5, 4 }));
    // }
}
