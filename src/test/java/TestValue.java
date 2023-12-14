import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    private static final String INVALID = "invalid";

    private String temp;

    public String solution(String sentence) {
        boolean[] used = new boolean[26];
        StringBuilder answer = new StringBuilder();

        temp = sentence;
        while (!temp.isEmpty()) {
            String ret;
            List<Integer> pos = new ArrayList<>();

            if (Character.isLowerCase(temp.charAt(0))) {
                ret = processLowerCase(used, pos);
            } else {
                ret = processUpperCase(used, pos);
            }

            if (ret.equals(INVALID)) {
                return INVALID;
            }

            answer.append(ret).append(" ");
        }

        answer.delete(answer.length() - 1, answer.length());
        return answer.toString();
    }

    private String processLowerCase(boolean[] used, List<Integer> pos) {
        if (used[temp.charAt(0) - 'a']) {
            return INVALID;
        }
        used[temp.charAt(0) - 'a'] = true;

        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == temp.charAt(0)) {
                pos.add(i);
            }
        }

        if (pos.size() != 2) {
            return INVALID;
        }

        String center = temp.substring(1, pos.get(pos.size() - 1));
        if (center.isEmpty()) {
            return INVALID;
        }

        String ret = rule1(center);
        if (ret.equals("-1")) {
            ret = allUpper(center);
            if (ret.equals("-1")) {
                return INVALID;
            }
        } else {
            if (used[temp.charAt(2) - 'a']) {
                return INVALID;
            }
            used[temp.charAt(2) - 'a'] = true;
        }

        temp = temp.substring(pos.get(pos.size() - 1) + 1);
        return ret;
    }

    private String processUpperCase(boolean[] used, List<Integer> pos) {
        String ret;
        if (temp.length() == 1 || Character.isUpperCase(temp.charAt(1))) {
            ret = String.valueOf(temp.charAt(0));
            temp = temp.substring(1);
        } else {
            for (int i = 0; i < temp.length(); i++) {
                if (temp.charAt(1) == temp.charAt(i)) {
                    pos.add(i);
                }
            }

            if (pos.size() != 2) {
                if (pos.get(pos.size() - 1) == temp.length() - 1) {
                    return INVALID;
                }
                if (Character.isLowerCase(temp.charAt(pos.get(pos.size() - 1) + 1))) {
                    return INVALID;
                }

                String center = temp.substring(0, pos.get(pos.size() - 1) + 2);
                ret = rule1(center);
                if (ret.equals("-1")) {
                    return INVALID;
                }

                if (used[temp.charAt(1) - 'a']) {
                    return INVALID;
                }
                used[temp.charAt(1) - 'a'] = true;

                temp = temp.substring(pos.get(pos.size() - 1) + 2);
            } else {
                ret = temp.charAt(0) + "";
                temp = temp.substring(1);
            }
        }
        return ret;
    }

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

    @Test
    void 정답() {
        Assertions.assertEquals("HELLO WORLD", solution("HaEaLaLaObWORLDb"));
        Assertions.assertEquals("invalid", solution("AxAxAxAoBoBoB"));
    }
}
