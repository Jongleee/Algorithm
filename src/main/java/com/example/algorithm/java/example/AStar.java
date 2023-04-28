package com.example.algorithm.java.example;

import java.util.ArrayList;

public class AStar {
    static class AData {

        int x;
        int y;
        int pIndex;
        int g; // 시작지점으로부터 온 거리
        int h; // 목적지까지의 거리
        int f; // 기대 거리
    
        public AData(int x, int y, int pIndex, int g, int h) { // OpenList에 추가할 때 사용할 값
            this.x = x;
            this.y = y;
            this.pIndex = pIndex;
            this.g = g;
            this.h = h;
            this.f = g + h; // F값은 G+H 이므로 따로 입력하지 않도록 했다.
        }
    
    }

    static ArrayList<AData> openList = new ArrayList<>();
    static ArrayList<AData> closeList = new ArrayList<>();
    static ArrayList<int[]> path = new ArrayList<>(); // 마지막에 경로를 저장할 곳이다.

    private static final int[] dx = { 0, 0, -1, 1 }; // 상하좌우
    private static final int[] dy = { 1, -1, 0, 0 }; // 상하좌우

    private static void aStarFind(int startx, int starty, int endx, int endy, int nowIndex) {
        closeList.add(new AData(startx, starty, -1, 0, 0)); // 시작지점을 바로 탐색할 수 있도록 CloseList에 미리 넣어준다.

        while (closeList.get(nowIndex).x != endx || closeList.get(nowIndex).y != endy) { // 목적지에 도착할때까지 반복한다.
            for (int i = 0; i < dx.length; i++) {
                int nowx = closeList.get(nowIndex).x + dx[i];
                int nowy = closeList.get(nowIndex).y + dy[i];

                boolean flag = false; // 해당 위치가 열린, 닫힌리스트중에 있는가 확인할때 쓴다.

                flag = flag(nowx, nowy, flag);
                flag = findShorterRoute(nowIndex, nowx, nowy, flag);

                if (!flag) { // 열린, 닫힌리스트에 없다면 열린리스트에 새로 추가해줌
                    openList.add(new AData(nowx, nowy, nowIndex, closeList.get(nowIndex).g + 1,
                            Math.abs(endx - nowx) + Math.abs(endy - nowy)));
                }

            }
            if (openList.isEmpty())
                break;
            nowIndex = findNext(nowIndex);
        }
        printRoute(nowIndex);
    }

    private static boolean flag(int nowx, int nowy, boolean flag) {
        for (int i = 0; i < closeList.size(); i++) {
            if (closeList.get(i).x == nowx && closeList.get(i).y == nowy) {
                flag = true;
            }
        }
        return flag;
    }

    private static int findNext(int nowIndex) {
        int fTemp = openList.get(0).f;
        int index = 0;

        for (int i = 0; i < openList.size(); i++) {
            if (openList.get(i).f < fTemp) {
                fTemp = openList.get(i).f;
                index = i;

            } else if (openList.get(i).f == fTemp) { // F값이 동일하다면 H값이 작은것을 택한다.
                if (openList.get(i).h < openList.get(index).h) {
                    index = i;
                }
            }

        }

        closeList.add(openList.get(index));

        openList.remove(index); // CloseList에 탐색할 위치를 넣어주고 OpenList에서 지운다.

        nowIndex++;
        return nowIndex;
    }
    private static boolean findShorterRoute(int nowIndex, int nowx, int nowy, boolean flag) {
        for (int i = 0; i < openList.size(); i++) {
            if (openList.get(i).x == nowx && openList.get(i).y == nowy) {
                flag = true;
                if (openList.get(i).g > closeList.get(nowIndex).g + 1) {
                    openList.set(i, new AData(nowx, nowy, nowIndex, closeList.get(nowIndex).g + 1, openList.get(i).h));
                }
            }
        }
        return flag;
    }

    private static void printRoute(int nowIndex) {
        while (nowIndex != -1) { // 도착지점부터 역순으로 되짚어간다
            int[] pathtemp = { closeList.get(nowIndex).x, closeList.get(nowIndex).y };
            path.add(pathtemp);
            nowIndex = closeList.get(nowIndex).pIndex;
        }

        while (!path.isEmpty()) { // 되짚어간 경로를 다시 원래대로 출력한다.
            System.out.println(path.get(path.size() - 1)[0] + " " + path.get(path.size() - 1)[1]);
            path.remove(path.size() - 1);
        }
    }


    public static void main(String[] args) {
        int startx = 0;
        int starty = 0;
        int endx = 5;
        int endy = 5;
        int nowIndex = 0;

        aStarFind(startx, starty, endx, endy, nowIndex);

    }
}
