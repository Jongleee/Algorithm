import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public long[] solution(long k, long[] roomNumbers) {
        int n = roomNumbers.length;
        if (n > k)
            return new long[] { -1 };

        Map<Long, Long> nextRoomMap = new HashMap<>();
        long[] assignedRooms = new long[n];

        for (int i = 0; i < n; i++) {
            assignedRooms[i] = getEmptyRoom(roomNumbers[i], nextRoomMap);
            nextRoomMap.put(roomNumbers[i], assignedRooms[i] + 1);
        }

        return assignedRooms;
    }

    private long getEmptyRoom(long roomNumber, Map<Long, Long> nextRoomMap) {
        if (!nextRoomMap.containsKey(roomNumber)) {
            nextRoomMap.computeIfAbsent(roomNumber, a -> a + 1);
            return roomNumber;
        }
        long emptyRoom = getEmptyRoom(nextRoomMap.get(roomNumber), nextRoomMap);
        nextRoomMap.put(roomNumber, emptyRoom + 1);
        return emptyRoom;
    }

    @Test
    void 정답() {
        Assertions.assertArrayEquals(new long[] { 1, 3, 4, 2, 5, 6 }, solution(10, new long[] { 1, 3, 4, 1, 3, 1 }));
    }
}
