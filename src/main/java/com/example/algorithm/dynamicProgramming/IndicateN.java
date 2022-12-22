package com.example.algorithm.dynamicProgramming;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IndicateN {
    public static int solution(int n, int number) {
        List<Set<Integer>> countList = new ArrayList<>();
        countList.add(new HashSet<>());

        for (int i = 0; i < 9; i++)
            countList.add(new HashSet<>());

        countList.get(1).add(n); // N을 1개 쓴 값은 N 혼자이다.

        for (int i = 2; i < 9; i++) {
            Set<Integer> countSet = countList.get(i);

            for (int j = 1; j <= i; j++) {
                Set<Integer> preSet = countList.get(j);
                Set<Integer> postSet = countList.get(i - j);

                makeSet(countSet, preSet, postSet);
            }

            countSet.add(Integer.parseInt(String.valueOf(n).repeat(i)));
        }

        for (Set<Integer> sub : countList) {
            if (sub.contains(number))
                return countList.indexOf(sub);
        }

        return -1;
    }

    private static void makeSet(Set<Integer> countSet, Set<Integer> preSet, Set<Integer> postSet) {
        for (int preNum : preSet) {
            for (int postNum : postSet) {
                countSet.add(preNum + postNum);
                countSet.add(preNum - postNum);
                countSet.add(preNum * postNum);

                if (preNum != 0 && postNum != 0)
                    countSet.add(preNum / postNum);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(solution(5, 12));
    }
}
