package com.example.algorithm.java.binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DarkCave {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int length = Integer.parseInt(reader.readLine());
        int lamps = Integer.parseInt(reader.readLine());
        
        int[] positions = new int[length + 1];
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
        int firstPosition = Integer.parseInt(tokenizer.nextToken());
        positions[firstPosition]++;
        int lastPosition = firstPosition;
        for (int i = 0; i < lamps - 1; i++) {
            lastPosition = Integer.parseInt(tokenizer.nextToken());
            positions[lastPosition]++;
        }

        int maxHeight = 1;
        for (int i = firstPosition; i < lastPosition;) {
            int nextPosition = i + 1;
            while (positions[nextPosition] == 0) {
                nextPosition++;
            }
            int space = (nextPosition - i + 1) / 2;
            if (space > maxHeight) maxHeight = space;
            i = nextPosition;
        }
        if (firstPosition > maxHeight) maxHeight = firstPosition;
        if (length - lastPosition > maxHeight) maxHeight = length - lastPosition;

        System.out.print(maxHeight);
    }
}


/*
3
1
0

3

5
2
2 4

2
 */