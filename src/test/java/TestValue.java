import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValue {
    String[] people;
    HashMap<Character, Integer> map;
    boolean[] visited;
    int[] positions;
    int count;

    public int solution(int n, String[] data) {
        if (data.length != n) {
            return 0;
        }

        people = data;
        map = new HashMap<>();
        visited = new boolean[8];
        positions = new int[8];
        count = 0;

        initializeMap();

        dfs(0);
        return count;
    }

    public void initializeMap() {
        char[] chars = { 'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T' };
        for (int i = 0; i < chars.length; i++) {
            map.put(chars[i], i);
        }
    }

    public void dfs(int idx) {
        if (idx == 8) {
            if (isValidArrangement()) {
                count++;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    positions[idx] = i;
                    dfs(idx + 1);
                    visited[i] = false;
                }
            }
        }
    }

    public boolean isValidArrangement() {
        for (String condition : people) {
            int pos1 = positions[map.get(condition.charAt(0))];
            int pos2 = positions[map.get(condition.charAt(2))];
            int distance = condition.charAt(4) - '0' + 1;
            char operator = condition.charAt(3);

            switch (operator) {
                case '=':
                    if (Math.abs(pos1 - pos2) != distance) {
                        return false;
                    }
                    break;
                case '>':
                    if (Math.abs(pos1 - pos2) <= distance) {
                        return false;
                    }
                    break;
                case '<':
                    if (Math.abs(pos1 - pos2) >= distance) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    @Test
    public void 정답() {
        Assertions.assertEquals(3648, solution(2, new String[] { "N~F=0", "R~T>2" }));
        Assertions.assertEquals(0, solution(2, new String[] { "M~C<2", "C~M>1" }));
    }
}
