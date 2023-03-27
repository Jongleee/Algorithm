package com.example.algorithm.java.practice;

import java.util.ArrayList;
import java.util.List;

public class BrianWorry {
    private static final String INVALID = "invalid";

    private String rule1(String str) {
        StringBuilder ret = new StringBuilder();
        if (str.length() < 3) {
            return "-1";
        }
        char c = str.charAt(1);
        boolean ok = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLowerCase(str.charAt(i))) {
                ok = true;
            }
            if (i % 2 == 0) {
                if (Character.isLowerCase(str.charAt(i))) {
                    return "-1";
                }
                ret.append(str.charAt(i));
            } else {
                if (c != str.charAt(i)) {
                    return "-1";
                }
            }
        }
        if (!ok) {
            return "-1";
        }
        return ret.toString();
    }

    private String allUpper(String str) {
        StringBuilder ret = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (!Character.isUpperCase(c)) {
                return "-1";
            }
            ret.append(c);
        }
        return ret.toString();
    }

    public String solution(String sentence) {
        boolean[] used = new boolean[26];
        StringBuilder answer = new StringBuilder();
        while (!sentence.isEmpty()) {
            String ret;
            List<Integer> pos = new ArrayList<>();
            if (Character.isLowerCase(sentence.charAt(0))) {
                if (used[sentence.charAt(0) - 'a']) {
                    return INVALID;
                }
                used[sentence.charAt(0) - 'a'] = true;
                for (int i = 0; i < sentence.length(); i++) {
                    if (sentence.charAt(i) == sentence.charAt(0)) {
                        pos.add(i);
                    }
                }
                if (pos.size() != 2) {
                    return INVALID;
                }

                String center = sentence.substring(1, pos.get(pos.size() - 1));
                if (center.isEmpty()) {
                    return INVALID;
                }
                ret = rule1(center);
                if (ret.equals("-1")) {
                    ret = allUpper(center);
                    if (ret.equals("-1")) {
                        return INVALID;
                    }
                } else {
                    if (used[sentence.charAt(2) - 'a']) {
                        return INVALID;
                    }
                    used[sentence.charAt(2) - 'a'] = true;
                }
                sentence = sentence.substring(pos.get(pos.size() - 1) + 1);
            } else {
                if (sentence.length() == 1 || Character.isUpperCase(sentence.charAt(1))) {
                    ret = String.valueOf(sentence.charAt(0));
                    sentence = sentence.substring(1);
                } else {
                    for (int i = 0; i < sentence.length(); i++) {
                        if (sentence.charAt(1) == sentence.charAt(i)) {
                            pos.add(i);
                        }
                    }

                    if (pos.size() != 2) {
                        if (pos.get(pos.size() - 1) == sentence.length() - 1) {
                            return INVALID;
                        }
                        if (Character.isLowerCase(sentence.charAt(pos.get(pos.size() - 1) + 1))) {
                            return INVALID;
                        }
                        String center = sentence.substring(0, pos.get(pos.size() - 1) + 2);
                        ret = rule1(center);
                        if (ret.equals("-1")) {
                            return INVALID;
                        }
                        if (used[sentence.charAt(1) - 'a']) {
                            return INVALID;
                        }
                        used[sentence.charAt(1) - 'a'] = true;
                        sentence = sentence.substring(pos.get(pos.size() - 1) + 2);
                    } else {
                        ret = sentence.charAt(0) + "";
                        sentence = sentence.substring(1);
                    }
                }
            }
            answer.append(ret + " ");
        }
        answer.delete(answer.length()-1, answer.length());
        return answer.toString();
    }
}
