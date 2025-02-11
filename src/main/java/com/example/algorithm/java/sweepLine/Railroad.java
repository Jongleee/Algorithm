package com.example.algorithm.java.sweepLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Railroad {
    private static class Line {
        long start, end;

        public Line(long start, long end) {
            this.start = Math.min(start, end);
            this.end = Math.max(start, end);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        List<Line> lines = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            lines.add(new Line(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken())));
        }

        long d = Long.parseLong(br.readLine().trim());
        System.out.println(findMaxLines(lines, d));
    }

    private static int findMaxLines(List<Line> lines, long d) {
        List<Long> startPoints = new ArrayList<>();
        List<Long> endPoints = new ArrayList<>();

        for (Line line : lines) {
            if (line.start + d >= line.end) {
                startPoints.add(line.end - d);
                endPoints.add(line.start);
            }
        }

        if (startPoints.isEmpty())
            return 0;

        Collections.sort(startPoints);
        Collections.sort(endPoints);

        int maxCount = 0, count = 0;
        int i = 0, j = 0;

        while (i < startPoints.size()) {
            if (startPoints.get(i) > endPoints.get(j)) {
                count--;
                j++;
            } else {
                count++;
                maxCount = Math.max(maxCount, count);
                i++;
            }
        }
        return maxCount;
    }
}

/*
5
-5 5 
30 40
-5 5
50 40
5 -5
10

3


4
20 80
70 30
35 65
40 60
10

0


8
5 40
35 25
10 20
10 25
30 50
50 60
30 25
80 100
30

4
*/