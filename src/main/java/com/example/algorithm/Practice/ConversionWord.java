package com.example.algorithm.Practice;

public class ConversionWord {
    boolean[] visit;
    int depth = 0;
    int answer = 0;

    public int solution(String begin, String target, String[] words) {
//        solution("hit","cog",new String[]{"hot", "dot", "dog", "lot", "log", "cog"});
        visit = new boolean[words.length];
        int test = 0;
        for (String word : words) {
            if (word.equals(target)) test++;
        }
        if (test == 0) return 0;
        dfs(begin, target, words, 0);
        return answer;
    }

    public void dfs(String begin, String target, String[] words, int depth) {
        if (begin.equals(target)) {
            answer = depth;
            return;
        }
        for (int i = 0; i < words.length; i++) {
            if (visit[i]) {
                continue;
            }
            int k = 0;
            for (int j = 0; j < begin.length(); j++) {
                if (begin.charAt(j) == words[i].charAt(j)) k++;
            }
            if (k == begin.length() - 1) {
                visit[i] = true;
                dfs(words[i], target, words, depth + 1);
                visit[i] = false;
            }
        }
    }
}
