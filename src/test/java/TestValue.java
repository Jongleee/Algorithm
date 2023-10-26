import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(String[][] bookTimeSlots) {
        int[] roomUsage = new int[1449];
        for (String[] timeSlot : bookTimeSlots) {
            int startTime = convertToMinutes(timeSlot[0]);
            int endTime = convertToMinutes(timeSlot[1]) + 9;
            incrementRoomUsage(roomUsage, startTime, endTime);
        }

        return maxRoomUsage(roomUsage);
    }

    public int convertToMinutes(String time) {
        int hours = Integer.parseInt(time.substring(0, 2));
        int minutes = Integer.parseInt(time.substring(3, 5));
        int totalMinutes = (hours * 60) + minutes;
        return totalMinutes;
    }

    public void incrementRoomUsage(int[] roomUsage, int startTime, int endTime) {
        for (int i = startTime; i <= endTime; i++) {
            roomUsage[i]++;
        }
    }

    public int maxRoomUsage(int[] roomUsage) {
        return Arrays.stream(roomUsage).max().getAsInt();
    }

    @Test
    void 정답() {
        String[][] b1 = { { "15:00", "17:00" }, { "16:40", "18:20" }, { "14:20", "15:20" }, { "14:10", "19:20" },
                { "18:20", "21:20" } };
        String[][] b2 = { { "09:10", "10:10" }, { "10:20", "12:20" } };
        String[][] b3 = { { "10:20", "12:30" }, { "10:20", "12:30" }, { "10:20", "12:30" } };

        Assertions.assertEquals(3, solution(b1));
        Assertions.assertEquals(1, solution(b2));
        Assertions.assertEquals(3, solution(b3));
    }
}
