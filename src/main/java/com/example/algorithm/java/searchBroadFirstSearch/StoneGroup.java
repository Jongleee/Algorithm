package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class StoneGroup {
    static class Stones {
		int aGroup;
		int bGroup;
		int cGroup;

		Stones(int aGroup, int bGroup, int cGroup) {
			this.aGroup = aGroup;
			this.bGroup = bGroup;
			this.cGroup = cGroup;
		}
	}

	static Queue<Stones> queue = new LinkedList<>();
	static boolean[][] visited = new boolean[3][1501];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int cntFirstGroup = Integer.parseInt(st.nextToken());
		int cntSecondGroup = Integer.parseInt(st.nextToken());
		int cntThirdGroup = Integer.parseInt(st.nextToken());

		visited[0][cntFirstGroup] = true;
		visited[1][cntSecondGroup] = true;
		visited[2][cntThirdGroup] = true;

		queue.add(new Stones(cntFirstGroup, cntSecondGroup, cntThirdGroup));

		while (!queue.isEmpty()) {
			Stones now = queue.poll();

			if (sameNumAll(now)) {
				System.out.println("1");
				return;
			}

			if (now.aGroup != now.bGroup) {
				Stones next = now;

				if (now.aGroup > now.bGroup) {
					next.aGroup -= next.bGroup;
					next.bGroup += next.bGroup;
				} else {
					next.bGroup -= next.aGroup;
					next.aGroup += next.aGroup;
				}

				if (isNotNegative(next) && !isVisited(next)) {
					queue.add(next);
				}
			}

			if (now.aGroup != now.cGroup) {
				Stones next = now;

				if (now.aGroup > now.cGroup) {
					next.aGroup -= next.cGroup;
					next.cGroup += next.cGroup;
				} else {
					next.cGroup -= next.aGroup;
					next.aGroup += next.aGroup;
				}

				if (isNotNegative(next) && !isVisited(next)) {
					queue.add(next);
				}
			}

			if (now.bGroup != now.cGroup) {
				Stones next = now;

				if (now.bGroup > now.cGroup) {
					next.bGroup -= next.cGroup;
					next.cGroup += next.cGroup;
				} else {
					next.cGroup -= next.bGroup;
					next.bGroup += next.bGroup;
				}

				if (isNotNegative(next) && !isVisited(next)) {
					queue.add(next);
				}
			}
		}
		System.out.println("0");

	}

	public static boolean isNotNegative(Stones stones) {
		return stones.aGroup >= 0 && stones.bGroup >= 0 && stones.cGroup >= 0;
	}

	public static boolean sameNumAll(Stones stones) {
		return stones.aGroup == stones.bGroup && stones.aGroup == stones.cGroup && stones.bGroup == stones.cGroup;
	}

	public static boolean isVisited(Stones stones) {
		if (visited[0][stones.aGroup] && visited[1][stones.bGroup] && visited[2][stones.cGroup])
			return true;
		visited[0][stones.aGroup] = true;
		visited[1][stones.bGroup] = true;
		visited[2][stones.cGroup] = true;
		return false;
	}
}

/*
10 15 35

1


1 1 2

0
*/