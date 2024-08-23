package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Rogo {
    static class Rec {
		int x1;
		int y1;
		int x2;
		int y2;

		public Rec(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		Rec[] rectangles = new Rec[n + 1];
		boolean[] visited = new boolean[n + 1];
		Queue<Integer> queue = new LinkedList<>();
		int groups = 0;

		rectangles[0] = new Rec(0, 0, 0, 0);

		for (int i = 1; i <= n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			rectangles[i] = new Rec(x1, y1, x2, y2);
		}

		for (int i = 0; i <= n; i++) {
			if (visited[i])
				continue;

			queue.add(i);
			visited[i] = true;

			while (!queue.isEmpty()) {
				int current = queue.poll();
				for (int j = 0; j <= n; j++) {
					if (!visited[j] && intersects(rectangles[current], rectangles[j])) {
						visited[j] = true;
						queue.add(j);
					}
				}
			}
			groups++;
		}
		System.out.println(groups - 1);
	}

	private static boolean intersects(Rec a, Rec b) {
		return !(a.x2 < b.x1 || b.x2 < a.x1 || a.y2 < b.y1 || b.y2 < a.y1)
				&& !(a.x1 > b.x1 && b.x2 > a.x2 && a.y1 > b.y1 && b.y2 > a.y2)
				&& !(a.x1 < b.x1 && b.x2 < a.x2 && a.y1 < b.y1 && b.y2 < a.y2);
	}
}

/*
5
1 1 4 4
3 3 6 6
4 4 5 5
5 0 8 3
6 1 7 2

2

1
-5 -5 5 5

1
*/