package com.example.algorithm.java.recursive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmoticonSales {
    private static class Emoticon {
        private double price;
        private double percent;

        public Emoticon(double price, double percent) {
            this.price = price;
            this.percent = percent;
        }

        public double getPrice() {
            return price;
        }

        public double getPercent() {
            return percent;
        }
    }

    private static int maxJoin = Integer.MIN_VALUE;
    private static int maxPrice = Integer.MIN_VALUE;

    public static int[] solution(int[][] users, int[] emoticons) {
        List<Emoticon> emos = new ArrayList<>();
        double[] sales = { 0.1, 0.2, 0.3, 0.4 };

        generateEmoticons(0, users, emos, emoticons, sales);

        return new int[] { maxJoin, maxPrice };
    }

    private static void generateEmoticons(int depth, int[][] users, List<Emoticon> emos, int[] emoticons,
            double[] sales) {
        if (depth == emoticons.length) {
            int totalJoin = 0;
            int totalSales = 0;

            for (int[] user : users) {
                int userPercent = user[0];
                int userPrice = user[1];
                int userSum = calculateUserSum(emos, userPercent);
                if (userSum >= userPrice) {
                    totalJoin++;
                } else {
                    totalSales += userSum;
                }

            }

            if (totalJoin > maxJoin) {
                maxJoin = totalJoin;
                maxPrice = totalSales;
            } else if (totalJoin == maxJoin && totalSales > maxPrice) {
                maxPrice = totalSales;
            }

            return;
        }

        for (double sale : sales) {
            Emoticon emo = new Emoticon((1 - sale) * emoticons[depth], sale * 100);
            emos.add(emo);
            generateEmoticons(depth + 1, users, emos, emoticons, sales);
            emos.remove(emo);
        }
    }

    private static int calculateUserSum(List<Emoticon> emos, int userPercent) {
        int sum=0;
        for (Emoticon emo : emos) {
            double emoPrice = emo.getPrice();
            double emoPercent = emo.getPercent();

            if (emoPercent >= userPercent) {
                sum += emoPrice;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int[][] u1 = { { 40, 10000 }, { 25, 10000 } };
        int[] e1 = { 7000, 9000 };
        int[][] u2 = { { 40, 2900 }, { 23, 10000 }, { 11, 5200 }, { 5, 5900 }, { 40, 3100 }, { 27, 9200 },
                { 32, 6900 } };
        int[] e2 = { 1300, 1500, 1600, 4900 };
        System.out.println(Arrays.toString(solution(u1, e1)));// 1, 5400
        System.out.println(Arrays.toString(solution(u2, e2)));// 4, 13860
    }
}