package com.example.algorithm.java.prac;

import java.util.Arrays;

public class No33 {

    class Solution {
        public int solution(int n, int[] lost, int[] reserve) {
            int answer = 0;
            int[] member = new int[n];
            Arrays.fill(member, 1);
            countLost(n, lost, member);
            Arrays.sort(reserve);
            countReserve(n, reserve, member);
            countNextRound(n, member);

            for (int i = 0; i < n; i++) {
                if (member[i] != 0)
                    answer++;
            }
            return answer;
        }

        private void countNextRound(int n, int[] member) {
            for (int i = 0; i < n; i++) {
                if (member[i] == 0) {
                    if (i > 0 && member[(i - 1)] == 2) {
                        member[i] = 1;
                        member[(i - 1)] = 1;
                    } else if (i < n - 1 && member[(i + 1)] == 2) {
                        member[i] = 1;
                        member[(i + 1)] = 1;
                    }
                }
            }
        }

        private void countReserve(int n, int[] reserve, int[] member) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < reserve.length; j++) {
                    if (reserve[j] == (i + 1))
                        member[i]++;
                }
            }
        }

        private void countLost(int n, int[] lost, int[] member) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < lost.length; j++) {
                    if (lost[j] == (i + 1))
                        member[i]--;
                }
            }
        }
    }
}
