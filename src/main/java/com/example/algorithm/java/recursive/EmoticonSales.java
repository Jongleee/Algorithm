package com.example.algorithm.java.recursive;

import java.util.ArrayList;
import java.util.List;

public class EmoticonSales {
    private static class Emoticon {
        private double price;
        private double discount;

        public Emoticon(double price, double discount) {
            this.price = price;
            this.discount = discount;
        }

        public double getPrice() {
            return price;
        }

        public double getDiscount() {
            return discount;
        }
    }

    private int maxJoin = Integer.MIN_VALUE;
    private int maxPrice = Integer.MIN_VALUE;

    public int[] solution(int[][] users, int[] emoticons) {
        List<Emoticon> emoticonList = new ArrayList<>();
        double[] discounts = { 0.1, 0.2, 0.3, 0.4 };

        generateEmoticons(0, users, emoticonList, emoticons, discounts);

        return new int[] { maxJoin, maxPrice };
    }

    private void generateEmoticons(int depth, int[][] users, List<Emoticon> emoticons, int[] emoticonPrices,
            double[] discounts) {
        if (depth == emoticonPrices.length) {
            int totalJoinedUsers = 0;
            int totalSales = 0;

            for (int[] user : users) {
                int userDiscount = user[0];
                int userPrice = user[1];
                int userSum = calculateUserSum(emoticons, userDiscount);

                if (userSum >= userPrice) {
                    totalJoinedUsers++;
                } else {
                    totalSales += userSum;
                }
            }

            if (totalJoinedUsers > maxJoin) {
                maxJoin = totalJoinedUsers;
                maxPrice = totalSales;
            } else if (totalJoinedUsers == maxJoin && totalSales > maxPrice) {
                maxPrice = totalSales;
            }

            return;
        }

        for (double discount : discounts) {
            Emoticon emoticon = new Emoticon((1 - discount) * emoticonPrices[depth], discount * 100);
            emoticons.add(emoticon);
            generateEmoticons(depth + 1, users, emoticons, emoticonPrices, discounts);
            emoticons.remove(emoticon);
        }
    }

    private int calculateUserSum(List<Emoticon> emoticons, int userDiscount) {
        int sum = 0;
        for (Emoticon emoticon : emoticons) {
            double emoticonPrice = emoticon.getPrice();
            double emoticonDiscount = emoticon.getDiscount();

            if (emoticonDiscount >= userDiscount) {
                sum += emoticonPrice;
            }
        }
        return sum;
    }

    // @Test
    // public void 정답() {
    //     int[][] u1 = { { 40, 10000 }, { 25, 10000 } };
    //     int[] e1 = { 7000, 9000 };
    //     int[][] u2 = { { 40, 2900 }, { 23, 10000 }, { 11, 5200 }, { 5, 5900 }, { 40, 3100 }, { 27, 9200 },
    //             { 32, 6900 } };
    //     int[] e2 = { 1300, 1500, 1600, 4900 };
    //     Assertions.assertArrayEquals(new int[] { 1, 5400 }, solution(u1, e1));
    //     Assertions.assertArrayEquals(new int[] { 4, 13860 }, solution(u2, e2));
    // }
}