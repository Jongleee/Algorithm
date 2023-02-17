package com.example.algorithm.java.queue;

import java.util.Deque;
import java.util.LinkedList;

public class Printer {

    public static int solution(int[] priorities, int location) {
        Deque<Document> queue = new LinkedList<>();
        int answer = 0;

        for (int i = 0; i < priorities.length; i++) {
            queue.add(new Document(i, priorities[i]));
        }

        while (!queue.isEmpty()) {
            Document currentDoc = queue.poll();

            boolean hasHigherPriority = queue.stream().anyMatch(doc -> doc.priority > currentDoc.priority);

            if (hasHigherPriority) {
                queue.addLast(currentDoc);
            } else {
                answer++;
                if (currentDoc.index == location) {
                    return answer;
                }
            }
        }

        return answer;
    }

    private static class Document {
        int index;
        int priority;

        public Document(int index, int priority) {
            this.index = index;
            this.priority = priority;
        }
    }

    public static void main(String[] args) {
        int[] p1 = { 2, 1, 3, 2 };
        int l1 = 2;
        int[] p2 = { 1, 1, 9, 1, 1, 1 };
        int l2 = 0;
        System.out.println(solution(p1, l1));// 1
        System.out.println(solution(p2, l2));// 5
    }
    
}
