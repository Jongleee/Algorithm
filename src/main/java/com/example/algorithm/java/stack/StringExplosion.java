package com.example.algorithm.java.stack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class StringExplosion {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        char[] inputChars = br.readLine().toCharArray();
        char[] bombChars = br.readLine().toCharArray();
        char[] resultChars = new char[inputChars.length];

        int bombLen = bombChars.length;
        int inputLen = inputChars.length;
        char lastBombChar = bombChars[bombLen - 1];
        int resultIndex = 0;

        for (int i = 0; i < inputLen; i++) {
            resultChars[i - resultIndex] = inputChars[i];

            if (inputChars[i] == lastBombChar) {
                boolean isBomb = true;
                for (int j = 0; j < bombLen - 1; j++) {
                    if (i - resultIndex - j - 1 < 0 || resultChars[i - resultIndex - j - 1] != bombChars[bombLen - 2 - j]) {
                        isBomb = false;
                        break;
                    }
                }

                if (isBomb) {
                    resultIndex += bombLen;
                }
            }
        }

        if (resultIndex == inputLen) {
            bw.write("FRULA");
        } else {
            String result = new String(resultChars, 0, inputLen - resultIndex);
            bw.write(result);
        }

        bw.flush();
        bw.close();
        br.close();
    }
}

/*
mirkovC4nizCC44
C4

mirkovniz


12ab112ab2ab
12ab

FRULA
 */