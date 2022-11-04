package com.example.algorithm.Practice;

public class BallMoveSimulation {
    public long solution(int n, int m, int x, int y, int[][] queries) {
        //2, 5, 0, 1, new int[][]{{3, 1}, {2, 2}, {1, 1}, {2, 3}, {0, 1}, {2, 1}}
        long xMin = x, yMin = y;
        long xMax = x, yMax = y;
        for (int i = queries.length-1; i >= 0; i--) {
            int direction = queries[i][0], move = queries[i][1];
            switch (direction){
                case 0:
                    if(yMin>0) yMin+=move;
                    yMax=Math.min(m-1,yMax+move);
                    break;
                case 1:
                    if(yMax<m-1)yMax-=move;
                    yMin=Math.max(0,yMin-move);
                    break;
                case 2:
                    if(xMin>0) xMin+=move;
                    xMax=Math.min(n-1,xMax+move);
                    break;
                case 3:
                    if(xMax<n-1)xMax-=move;
                    xMin=Math.max(0,xMin-move);
                    break;
            }
            if(yMin>=m||yMax<0||xMin>=n||xMax<0) return 0;
        }
        return (yMax-yMin+1)*(xMax-xMin+1);
    }
}
