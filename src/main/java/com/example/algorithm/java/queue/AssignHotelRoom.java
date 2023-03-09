package com.example.algorithm.java.queue;

import java.util.HashMap;
import java.util.Map;

public class AssignHotelRoom {
    public long[] solution(long k, long[] room_number) {
        Map<Long, Long> nextRooms = new HashMap<>();
        long[] answer = new long[room_number.length];

        for (int i = 0; i < room_number.length; i++) {
            long emptyRoom = findEmptyRoom(room_number[i], nextRooms);
            answer[i] = emptyRoom;
            nextRooms.put(room_number[i], emptyRoom + 1);
        }

        return answer;
    }

    private long findEmptyRoom(long request, Map<Long, Long> nextRooms) {
        if (!nextRooms.containsKey(request)) {
            nextRooms.put(request, request + 1);
            return request;
        }

        long emptyRoom = findEmptyRoom(nextRooms.get(request), nextRooms);
        nextRooms.put(request, emptyRoom);
        return emptyRoom;
    }
}
