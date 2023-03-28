package com.example.algorithm.java.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Lighthouse {

    class TreeNode {
        int num;
        List<TreeNode> childs;

        public TreeNode(int num) {
            this.num = num;
            this.childs = new ArrayList<>();
        }
    }

    Map<Integer, List<Integer>> routeMap;
    TreeNode routeNode;
    Set<Integer> lights;

    public int solution(int n, int[][] lighthouse) {
        lights = new HashSet<>();
        makeRouteMap(lighthouse);
        findRouteNode(n);
        makeTree();
        countMinLights(routeNode);
        return lights.size();
    }

    public void makeRouteMap(int[][] lighthouse) {
        routeMap = new HashMap<>();
        for (int[] route : lighthouse) {
            routeMap.computeIfAbsent(route[0], k -> new ArrayList<>()).add(route[1]);
            routeMap.computeIfAbsent(route[1], k -> new ArrayList<>()).add(route[0]);
        }
    }

    public void findRouteNode(int maxNodeNum) {
        for (int current = 1; current <= maxNodeNum; current++) {
            List<Integer> next = routeMap.get(current);
            if (next != null && next.size() > 1) {
                routeNode = new TreeNode(current);
                break;
            }
        }
        if (routeNode == null)
            routeNode = new TreeNode(1);
    }

    public void makeTree() {
        Queue<TreeNode> q = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        q.offer(routeNode);
        visited.add(routeNode.num);
        while (!q.isEmpty()) {
            TreeNode current = q.poll();
            List<Integer> next = routeMap.get(current.num);
            if (next == null)
                continue;
            for (int toNodeInRoute : next) {
                if (visited.contains(toNodeInRoute))
                    continue;
                TreeNode child = new TreeNode(toNodeInRoute);
                current.childs.add(child);
                q.offer(child);
                visited.add(child.num);
            }
        }
    }

    public void countMinLights(TreeNode parent) {
        if (parent.childs.isEmpty())
            return;
        boolean flag = false;
        for (TreeNode child : parent.childs) {
            countMinLights(child);
            if (!lights.contains(child.num)) {
                flag = true;
            }
        }
        if (flag)
            lights.add(parent.num);
    }
}
