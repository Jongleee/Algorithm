package com.example.algorithm.java.greedy;

import java.util.Arrays;

public class Lifeboat {
	public int solution(int[] people, int limit) {
		Arrays.sort(people);

		int answer = 0;
		int min = 0;
		int max = people.length - 1;

		while (min <= max) {
			if (people[min] + people[max] <= limit) {
				min++;
			}
			max--;
			answer++;
		}

		return answer;
	}
}
// @Test
// 	public void 정답() {
// 		Assertions.assertEquals(1, solution(new int[] { 50 }, 50));
// 		Assertions.assertEquals(2, solution(new int[] { 20, 50, 50, 80 }, 100));
// 		Assertions.assertEquals(3, solution(new int[] { 70, 50, 80, 50 }, 100));
// 		Assertions.assertEquals(3, solution(new int[] { 50, 30, 20, 70, 10 }, 100));
// 		Assertions.assertEquals(3, solution(new int[] { 70, 80, 50 }, 100));
// 		Assertions.assertEquals(5, solution(new int[] { 10, 20, 30, 40, 50, 60, 70, 80, 90 }, 100));
// 	}
