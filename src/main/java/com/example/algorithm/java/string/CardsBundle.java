package com.example.algorithm.java.string;

public class CardsBundle {
    public String solution(String[] cards1, String[] cards2, String[] goal) {
        int one = 0;
        int two = 0;

        for (String g : goal) {
            if (one < cards1.length && g.equals(cards1[one])) {
                one++;
            } else if (two < cards2.length && g.equals(cards2[two])) {
                two++;
            } else {
                return "No";
            }
        }

        return "Yes";
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals("Yes", solution(new String[] { "i", "drink", "water" }, new String[] { "want", "to" },
    //             new String[] { "i", "want", "to", "drink", "water" }));
    //     Assertions.assertEquals("No", solution(new String[] { "i", "water", "drink" }, new String[] { "want", "to" },
    //             new String[] { "i", "want", "to", "drink", "water" }));
    // }
}
