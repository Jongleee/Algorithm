import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[] order) {
        Queue<Integer> workingQueue = new LinkedList<>();
        Stack<Integer> waitingStack = new Stack<>();

        int cnt = 0;

        for (int i = 0; i < order.length; i++) {
            waitingStack.add(i + 1);

            while (!waitingStack.isEmpty()) {
                if (waitingStack.peek() == order[cnt]) {
                    workingQueue.offer(waitingStack.pop());
                    cnt++;
                } else {
                    break;
                }
            }
        }

        return workingQueue.size();
    }

    @Test
    void 정답() {
        int[] order1 = { 4, 3, 1, 2, 5 };
        int[] order2 = { 5, 4, 3, 2, 1 };

        Assertions.assertEquals(2, solution(order1));
        Assertions.assertEquals(5, solution(order2));
    }
}
