import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    class Food {
        private final int index;
        private int time;

        public Food(int index, int time) {
            this.index = index;
            this.time = time;
        }

        public int getIndex() {
            return index;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

    public int solution(int[] foodTimes, long k) {
        Queue<Food> foodQueue = new PriorityQueue<>(
                Comparator.comparingInt(Food::getTime).thenComparingInt(Food::getIndex));

        for (int i = 0; i < foodTimes.length; i++) {
            foodQueue.add(new Food(i + 1, foodTimes[i]));
        }

        int prevTime = 0;
        int n = foodTimes.length;

        while (!foodQueue.isEmpty()) {
            Food food = foodQueue.peek();
            int diff = food.getTime() - prevTime;
            long totalTime = (long) diff * n;

            if (totalTime <= k) {
                k -= totalTime;
                prevTime = food.getTime();
                foodQueue.poll();
                n--;
            } else {
                break;
            }
        }

        if (foodQueue.isEmpty()) {
            return -1;
        }

        List<Food> remainingFoods = new ArrayList<>(foodQueue);
        remainingFoods.sort(Comparator.comparingInt(Food::getIndex));

        int index = (int) (k % n);
        return remainingFoods.get(index).getIndex();
    }

    @Test
    void 정답() {
        int[] f1 = { 3, 1, 2 };
        int k1 = 5;
        Assertions.assertEquals(1, solution(f1, k1));
    }
}
