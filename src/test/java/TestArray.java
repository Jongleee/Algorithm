import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public String[] solution(String[][] plans) {
        String[] answer = new String[plans.length];
        Stack<String[]> workingStack = new Stack<>();
        Queue<String[]> waitQueue = new ArrayDeque<>();
        int index = 0;

        Arrays.sort(plans, (a, b) -> compareTimes(a[1], b[1]));

        for (String[] plan : plans) {
            waitQueue.offer(plan);
        }

        while (!waitQueue.isEmpty()) {
            workingStack.push(waitQueue.poll());

            if (waitQueue.isEmpty()) {
                while (!workingStack.isEmpty()) {
                    answer[index++] = workingStack.pop()[0];
                }
                break;
            }

            int term = calculateTimeDifference(waitQueue.peek()[1], workingStack.peek()[1]);

            while (!workingStack.isEmpty() && term >= Integer.parseInt(workingStack.peek()[2])) {
                String[] completed = workingStack.pop();
                answer[index++] = completed[0];
                term -= Integer.parseInt(completed[2]);
            }

            if (!workingStack.isEmpty()) {
                String[] currentWorking = workingStack.pop();
                currentWorking[2] = String.valueOf(Integer.parseInt(currentWorking[2]) - term);
                workingStack.push(currentWorking);
            }
        }

        return answer;
    }

    private int compareTimes(String timeA, String timeB) {
        int minutesA = calculateMinutes(timeA);
        int minutesB = calculateMinutes(timeB);
        return Integer.compare(minutesA, minutesB);
    }

    private int calculateTimeDifference(String startTime, String endTime) {
        int startMinutes = calculateMinutes(startTime);
        int endMinutes = calculateMinutes(endTime);
        return startMinutes - endMinutes;
    }

    private int calculateMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    @Test
    void 정답() {
        String[][] p1 = new String[][] { { "science", "12:40", "50" }, { "music", "12:20", "40" },
                { "history", "14:00", "30" }, { "computer", "12:30", "100" } };
        Assertions.assertArrayEquals(new String[] {"science", "history", "computer", "music" }, solution(p1));
    }
}
