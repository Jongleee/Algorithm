package com.example.algorithm.java.practice;

public class StickerCollecting2 {
    public static void main(String[] args) {
        System.out.println(solution(new int[]{14, 6, 5, 11, 3, 9, 2, 10}));
    }

    public static int solution(int[] sticker) {
        int answer = 0;

        int len = sticker.length;

        if (len == 1) return sticker[0];

        int[] withFirst = new int[len];
        int[] withoutFirst = new int[len];

        withFirst[0] = sticker[0];
        withFirst[1] = sticker[0];
        withoutFirst[0] = 0;
        withoutFirst[1] = sticker[1];
        for (int i = 2; i < len;i++){
            withFirst[i] = Math.max(withFirst[i-1],withFirst[i-2] + sticker[i]);
            withoutFirst[i] = Math.max(withoutFirst[i-1],withoutFirst[i-2] + sticker[i]);
        }


        answer = Math.max(withFirst[len-2],withoutFirst[len-1]);

        return answer;
    }
}
