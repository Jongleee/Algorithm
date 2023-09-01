package com.example.algorithm.java.string;

public class FindKimAtSeoul {
    public String solution(String[] seoul) {
        int cnt = 0;
        for (String string : seoul) {
            if (string.equals("Kim"))
                break;
            cnt++;
        }
        return "김서방은 " + cnt + "에 있다";
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals("김서방은 1에 있다", solution(new String[] { "Jane", "Kim" }));

    //     Assertions.assertEquals("김서방은 2에 있다", solution(new String[] { "Jane", "Jeong", "Kim" }));
    // }
}
