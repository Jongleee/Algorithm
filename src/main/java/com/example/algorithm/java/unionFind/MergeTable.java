package com.example.algorithm.java.unionFind;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MergeTable {
    private int[] parent = new int[2501];
    private String[] value = new String[2501];

    private int find(int a) {
        if (parent[a] == a)
            return a;
        else
            return parent[a] = find(parent[a]);
    }

    private void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b)
            parent[b] = a;
    }

    private int convertNum(int x, int y) {
        return 50 * (x - 1) + y;
    }

    public String[] solution(String[] commands) {
        for (int i = 1; i <= 2500; i++) {
            parent[i] = i;
            value[i] = "";
        }

        List<String> result = new ArrayList<>();
        for (String command : commands) {
            StringTokenizer st = new StringTokenizer(command);
            String cmd = st.nextToken();
            switch (cmd) {
                case "UPDATE":
                    if (st.countTokens() == 2) {
                        String before = st.nextToken();
                        String after = st.nextToken();
                        for (int i = 1; i <= 2500; i++) {
                            if (before.equals(value[i]))
                                value[i] = after;
                        }
                    } else {
                        int x = Integer.parseInt(st.nextToken());
                        int y = Integer.parseInt(st.nextToken());
                        String after = st.nextToken();
                        int num = convertNum(x, y);
                        value[find(num)] = after;
                    }
                    break;

                case "MERGE":
                    int x1 = Integer.parseInt(st.nextToken());
                    int y1 = Integer.parseInt(st.nextToken());
                    int x2 = Integer.parseInt(st.nextToken());
                    int y2 = Integer.parseInt(st.nextToken());
                    int n1 = convertNum(x1, y1);
                    int n2 = convertNum(x2, y2);
                    int root1 = find(n1);
                    int root2 = find(n2);
                    if (root1 == root2)
                        continue;
                    String rootString = value[root1].isBlank() ? value[root2] : value[root1];
                    value[root1] = "";
                    value[root2] = "";
                    union(root1, root2);
                    value[root1] = rootString;
                    break;

                case "UNMERGE":
                    int x = Integer.parseInt(st.nextToken());
                    int y = Integer.parseInt(st.nextToken());
                    int num = convertNum(x, y);
                    int root = find(num);
                    String rootString1 = value[root];
                    value[root] = "";
                    value[num] = rootString1;
                    List<Integer> deleteList = new ArrayList<>();
                    for (int i = 1; i <= 2500; i++) {
                        if (find(i) == root)
                            deleteList.add(i);
                    }
                    for (Integer t : deleteList)
                        parent[t] = t;
                    break;

                case "PRINT":
                    int printX = Integer.parseInt(st.nextToken());
                    int printY = Integer.parseInt(st.nextToken());
                    int printNum = convertNum(printX, printY);
                    int printRoot = find(printNum);
                    if (value[printRoot].isBlank())
                        result.add("EMPTY");
                    else
                        result.add(value[printRoot]);
                    break;
                default:
                    break;

            }
        }
        return result.toArray(new String[0]);
    }
    // @Test
    // public void 정답() {
    //     Assertions.assertArrayEquals(new String[] { "EMPTY", "group" },
    //             solution(new String[] { "UPDATE 1 1 menu", "UPDATE 1 2 category", "UPDATE 2 1 bibimbap",
    //                     "UPDATE 2 2 korean", "UPDATE 2 3 rice", "UPDATE 3 1 ramyeon", "UPDATE 3 2 korean",
    //                     "UPDATE 3 3 noodle", "UPDATE 3 4 instant", "UPDATE 4 1 pasta", "UPDATE 4 2 italian",
    //                     "UPDATE 4 3 noodle", "MERGE 1 2 1 3", "MERGE 1 3 1 4", "UPDATE korean hansik",
    //                     "UPDATE 1 3 group", "UNMERGE 1 4", "PRINT 1 3", "PRINT 1 4" }));
    //     Assertions.assertArrayEquals(new String[] { "d", "EMPTY" },
    //             solution(new String[] { "UPDATE 1 1 a", "UPDATE 1 2 b", "UPDATE 2 1 c", "UPDATE 2 2 d", "MERGE 1 1 1 2",
    //                     "MERGE 2 2 2 1", "MERGE 2 1 1 1", "PRINT 1 1", "UNMERGE 2 2", "PRINT 1 1" }));
    // }
}
