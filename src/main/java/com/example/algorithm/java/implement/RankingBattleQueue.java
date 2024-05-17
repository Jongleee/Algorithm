package com.example.algorithm.java.implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class RankingBattleQueue {
    static StringBuilder sb;

    static class Room {
        int cnt;
        int firstLevel;
        Queue<User> list;

        public Room(int cnt, int firstLevel) {
            this.cnt = cnt;
            this.firstLevel = firstLevel;
            this.list = new PriorityQueue<>();
        }
    }

    static class User implements Comparable<User> {
        String name;
        int level;

        public User(String name, int level) {
            this.name = name;
            this.level = level;
        }

        @Override
        public int compareTo(User o) {
            return this.name.compareTo(o.name);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        int p = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        List<Room> roomList = new ArrayList<>();

        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            int level = Integer.parseInt(st.nextToken());
            String name = st.nextToken();
            boolean isEnter = false;

            for (Room room : roomList) {
                if (room.cnt < m && room.firstLevel + 10 >= level && room.firstLevel - 10 <= level) {
                    isEnter = true;
                    room.list.add(new User(name, level));
                    room.cnt++;
                    break;
                }
            }

            if (!isEnter) {
                Room room = new Room(1, level);
                room.list.add(new User(name, level));
                roomList.add(room);
            }
        }

        for (Room room : roomList) {
            if (room.cnt == m) {
                sb.append("Started!\n");
            } else {
                sb.append("Waiting!\n");
            }

            while (!room.list.isEmpty()) {
                User user = room.list.poll();
                sb.append(user.level).append(" ").append(user.name).append("\n");
            }
        }

        System.out.print(sb.toString());
    }
}

/*
10 5
10 a
15 b
20 c
25 d
30 e
17 f
18 g
26 h
24 i
28 j

Started!
10 a
15 b
20 c
17 f
18 g
Started!
25 d
30 e
26 h
24 i
28 j
 */