import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public String[] solution(String[] files) {
        Arrays.sort(files, (o1, o2) -> {
            String[] part1 = modPart(o1);
            String[] part2 = modPart(o2);

            int headComparing = part1[0].compareTo(part2[0]);
            if (headComparing != 0) {
                return headComparing;
            }

            return Integer.compare(Integer.parseInt(part1[1]), Integer.parseInt(part2[1]));
        });

        return files;
    }

    private String[] modPart(String s) {
        String[] part = new String[3];
        boolean state = false;
        int numberStartIdx = -1;

        int i = 0;
        for (char currentChar : s.toCharArray()) {
            if (!state && isNumber(currentChar)) {
                part[0] = s.substring(0, i).toLowerCase();
                numberStartIdx = i;
                state = true;
            } else if (state && !isNumber(currentChar)) {
                String number = s.substring(numberStartIdx, i);
                if (number.length() > 5) {
                    i -= (number.length() - 5);
                }
                part[1] = s.substring(numberStartIdx, i);
                part[2] = s.substring(i);
                break;
            }
            i++;
        }

        if (part[1] == null) {
            String number = s.substring(numberStartIdx, i);
            if (number.length() > 5) {
                i -= (number.length() - 5);
            }
            part[1] = s.substring(numberStartIdx, i);
            part[2] = s.substring(i);
        }

        return part;
    }

    private boolean isNumber(char c) {
        return Character.isDigit(c);
    }

    @Test
    void 정답() {
        String[] file1 = { "img12.png", "img10.png", "img02.png", "img1.png", "IMG01.GIF", "img2.JPG" };
        String[] result1 = { "img1.png", "IMG01.GIF", "img02.png", "img2.JPG", "img10.png", "img12.png" };

        String[] file2 = { "F-5 Freedom Fighter", "B-50 Superfortress", "A-10 Thunderbolt II", "F-14 Tomcat" };
        String[] result2 = { "A-10 Thunderbolt II", "B-50 Superfortress", "F-5 Freedom Fighter", "F-14 Tomcat" };

        Assertions.assertArrayEquals(result1, solution(file1));
        Assertions.assertArrayEquals(result2, solution(file2));
    }
}
