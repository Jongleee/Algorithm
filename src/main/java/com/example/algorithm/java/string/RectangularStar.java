package com.example.algorithm.java.string;

import java.util.Scanner;

public class RectangularStar {
    
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < a; i++) {
                sb.append("*");
            }

            for (int i = 0; i < b; i++) {
                System.out.println(sb);
            }
        }
    }
}
