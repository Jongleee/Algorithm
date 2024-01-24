import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    private int n;

    public int solution(int n, int m, int[][] timetable) {
        this.n = n;
        int maxNum = getMaxNum(m, timetable);

        if (maxNum == 1)
            return 0;
        if (maxNum > (n * n + 1) / 2)
            return 1;

        int dis = 1;

        while (can(dis + 1, maxNum)) {
            dis++;
        }

        return dis;
    }

    private boolean can(int dis, int maxNum) {
        List<Point> pointList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            pointList.add(new Point(i));

            for (int k = i + 1; k < n * n; k++) {
                if (valid(k, pointList, dis)) {
                    pointList.add(new Point(k));
                }
            }

            if (pointList.size() >= maxNum)
                return true;
            pointList.clear();
        }

        return false;
    }

    private boolean valid(int k, List<Point> pointList, int dis) {
        for (Point p : pointList) {
            if (p.getDistance(k) < dis)
                return false;
        }
        return true;
    }

    private int getMaxNum(int m, int[][] timetable) {
        Set<Integer> uniqueTimes = new HashSet<>();

        for (int i = 0; i < m; i++) {
            uniqueTimes.add(timetable[i][0]);
            uniqueTimes.add(timetable[i][1]);
        }

        List<Integer> sortedTimes = new ArrayList<>(uniqueTimes);
        Collections.sort(sortedTimes);

        int[] peopleNums = new int[sortedTimes.size()];
        int max = 0;

        for (int i = 0; i < m; i++) {
            int from = Collections.binarySearch(sortedTimes, timetable[i][0]);
            int to = Collections.binarySearch(sortedTimes, timetable[i][1]);

            for (int j = from; j <= to; j++) {
                peopleNums[j]++;
                max = Math.max(max, peopleNums[j]);
            }
        }

        return max;
    }

    class Point {
        int i;

        public Point(int i) {
            this.i = i;
        }

        public int getDistance(int num) {
            return Math.abs(this.i / n - num / n) + Math.abs(this.i % n - num % n);
        }
    }

    @Test
    void 정답() {
        int n1 = 3;
        int m1 = 2;
        int[][] t1 = { { 1170, 1210 }, { 1200, 1260 } };
        int n2 = 2;
        int m2 = 1;
        int[][] t2 = { { 840, 900 } };
        int n3 = 2;
        int m3 = 2;
        int[][] t3 = { { 600, 630 }, { 630, 700 } };
        int n4 = 4;
        int m4 = 5;
        int[][] t4 = { { 1140, 1200 }, { 1150, 1200 }, { 1100, 1200 }, { 1210, 1300 }, { 1220, 1280 } };

        Assertions.assertEquals(4, solution(n1, m1, t1));
        Assertions.assertEquals(0, solution(n2, m2, t2));
        Assertions.assertEquals(2, solution(n3, m3, t3));
        Assertions.assertEquals(4, solution(n4, m4, t4));
    }
}
