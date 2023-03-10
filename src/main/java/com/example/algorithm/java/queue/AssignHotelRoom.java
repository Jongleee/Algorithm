package com.example.algorithm.java.queue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AssignHotelRoom {
    public static long[] solution(long k, long[] roomNumbers) {
        Map<Long, Long> nextRoomMap = new HashMap<>();
        long[] assignedRooms = new long[roomNumbers.length];

        for (int i = 0; i < roomNumbers.length; i++) {
            long emptyRoom = getNextEmptyRoom(roomNumbers[i], nextRoomMap);
            assignedRooms[i] = emptyRoom;
            nextRoomMap.put(roomNumbers[i], emptyRoom + 1);
        }

        return assignedRooms;
    }

    private static long getNextEmptyRoom(long roomNumber, Map<Long, Long> nextRoomMap) {
        if (!nextRoomMap.containsKey(roomNumber)) {
            nextRoomMap.computeIfAbsent(roomNumber, a -> a+1);
            return roomNumber;
        }

        long nextEmptyRoom = getNextEmptyRoom(nextRoomMap.get(roomNumber), nextRoomMap);
        nextRoomMap.put(roomNumber, nextEmptyRoom);
        return nextEmptyRoom;
    }

    public static void main(String[] args) {
        long[] assignedRooms = solution(10, new long[] {1, 3, 4, 1, 3, 1});
        System.out.println(Arrays.toString(assignedRooms));
    }
}
