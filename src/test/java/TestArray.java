import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArray {
    public static int[] solution(String[] maps) {
        boolean[][] visited = new boolean[maps.length][maps[0].length()];
        List<Integer> territories = new ArrayList<>();

        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[i].length(); j++) {
                int territorySize = getTerritorySize(i, j, visited, maps);
                if (territorySize > 0) {
                    territories.add(territorySize);
                }
            }
        }

        Collections.sort(territories);
        return territories.isEmpty() ? new int[] { -1 } : territories.stream().mapToInt(Integer::intValue).toArray();
    }

    private static int getTerritorySize(int row, int col, boolean[][] visited, String[] maps) {
        if (row < 0 || col < 0 || row >= visited.length || col >= visited[0].length || visited[row][col]
                || maps[row].charAt(col) == 'X') {
            return 0;
        }

        visited[row][col] = true;
        int territorySize = maps[row].charAt(col) - '0';
        int[] dr = { -1, 1, 0, 0 };
        int[] dc = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];
            territorySize += getTerritorySize(newRow, newCol, visited, maps);
        }

        return territorySize;
    }

    @Test
    public void 정답() {
        Assertions.assertArrayEquals(new int[] { 1, 1, 27 },
                solution(new String[] { "X591X", "X1X5X", "X231X", "1XXX1" }));
        Assertions.assertArrayEquals(new int[] { -1 },
                solution(new String[] { "XXX", "XXX", "XXX" }));
    }
}
