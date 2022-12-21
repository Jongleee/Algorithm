package com.example.algorithm.searchDFS;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class SheepAndWolf {
    static ArrayList<Integer>[] childs;
    static int[] graphInfo;
    static int sheepCnt = 0;

    public static int solution(int[] info, int[][] edges) {
        graphInfo = info;
        childs = new ArrayList[info.length];
        for (int[] edge : edges) {
            int parent = edge[0];
            int child = edge[1];
            if (childs[parent] == null) {
                childs[parent] = new ArrayList<>();
            }
            childs[parent].add(child);
        }

        List<Integer> list = new ArrayList<>();
        list.add(0);
        dfs(0, 0, 0, list);
        return sheepCnt;
    }

    private static void dfs(int index, int sheepCnt, int wolfCnt, List<Integer> nextPos) {
        switch (graphInfo[index]) {
            case 0:
                sheepCnt++;
                break;
            case 1:
                wolfCnt++;
                break;
            default:
                break;
        }
        if (wolfCnt >= sheepCnt)
            return;
        SheepAndWolf.sheepCnt = Math.max(sheepCnt, SheepAndWolf.sheepCnt);

        List<Integer> list = new ArrayList<>(nextPos);
        list.remove(Integer.valueOf(index));
        if (childs[index] != null) {
            list.addAll(childs[index]);
        }

        for (int next : list) {
            dfs(next, sheepCnt, wolfCnt, list);
        }
    }
    public static void main(String[] args) {
        System.out.println(solution(
            new int[]{0,1,0,1,1,0,1,0,0,1,0}, new
            int[][]{{0,1},{0,2},{1,3},{1,4},{2,5},{2,6},{3,7},{4,8},{6,9},{9,10}}));    
    }
}
