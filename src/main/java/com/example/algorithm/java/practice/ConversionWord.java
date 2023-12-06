package com.example.algorithm.java.practice;

public class ConversionWord {
    private boolean[] visit;
    private int answer;

    public int solution(String begin, String target, String[] words) {
        visit = new boolean[words.length];
        answer = 0;

        if (!containsTarget(target, words)) {
            return 0;
        }

        dfs(begin, target, words, 0);
        return answer;
    }

    private boolean containsTarget(String target, String[] words) {
        for (String word : words) {
            if (word.equals(target)) {
                return true;
            }
        }
        return false;
    }

    private void dfs(String begin, String target, String[] words, int depth) {
        if (begin.equals(target)) {
            answer = depth;
            return;
        }

        for (int i = 0; i < words.length; i++) {
            if (visit[i]) {
                continue;
            }

            int matchingChars = countMatchingChars(begin, words[i]);
            if (matchingChars == begin.length() - 1) {
                visit[i] = true;
                dfs(words[i], target, words, depth + 1);
                visit[i] = false;
            }
        }
    }

    private int countMatchingChars(String str1, String str2) {
        int count = 0;
        for (int j = 0; j < str1.length(); j++) {
            if (str1.charAt(j) == str2.charAt(j)) {
                count++;
            }
        }
        return count;
    }

    // @Test
    // void 정답() {
    //     String[] w1 = { "hot", "dot", "dog", "lot", "log", "cog" };
    //     String[] w2 = { "hot", "dot", "dog", "lot", "log" };
    //     Assertions.assertEquals(4, solution("hit", "cog", w1));
    //     Assertions.assertEquals(0, solution("hit", "cog", w2));
    // }
}
