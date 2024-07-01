package com.example.algorithm.java.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SimilarWords {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Input[] words = new Input[n];
        for (int i = 0; i < n; i++) {
            words[i] = new Input(i, br.readLine());
        }
        Arrays.sort(words);

        Input first = words[0];
        Input second = words[1];
        if (first.index > second.index) {
            Input temp = first;
            first = second;
            second = temp;
        }
        int maxMatchLen = first.getMatchLen(second);

        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; i + j < n; j++) {
                Input nextFirst = words[i];
                Input nextSecond = words[i + j];

                if (nextFirst.index > nextSecond.index) {
                    Input temp = nextFirst;
                    nextFirst = nextSecond;
                    nextSecond = temp;
                }

                int matchLen = nextFirst.getMatchLen(nextSecond);
                if (matchLen > maxMatchLen) {
                    first = nextFirst;
                    second = nextSecond;
                    maxMatchLen = matchLen;
                } else if (matchLen == maxMatchLen) {
                    if (nextFirst.index < first.index
                            || (nextFirst.index == first.index && nextSecond.index < second.index)) {
                        first = nextFirst;
                        second = nextSecond;
                    }
                } else {
                    break;
                }
            }
        }
        System.out.printf("%s%n%s%n", first.value, second.value);
    }
    
}
class Input implements Comparable<Input> {
    public final int index;
    public final String value;

    public Input(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getMatchLen(Input input) {
        int i = 0;
        while (i < value.length() && i < input.value.length()) {
            if (value.charAt(i) != input.value.charAt(i)) {
                break;
            }
            i++;
        }
        return i;
    }

    @Override
    public int compareTo(Input input) {
        return value.compareTo(input.value);
    }
}

/*
9
noon
is
lunch
for
most
noone
waits
until
two

noon
noone


4
abcd
abe
abc
abchldp

abcd
abc
 */