package com.example.algorithm.java.hashMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class PainfulMemorizeEnglishWords {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue() < o2.getValue()) {
					return 1;
				} else if (o1.getValue() > o2.getValue()) {
					return -1;
				}
				else if(o1.getKey().length() < o2.getKey().length()){
					return 1;
				}
				else if(o1.getKey().length() > o2.getKey().length()){
					return -1;
				}
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        Map<String, Integer> words = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            if (str.length() >= m) {
                words.put(str, words.getOrDefault(str, 0) + 1);
            }
        }

        pq.addAll(words.entrySet());

        while (!pq.isEmpty()) {
            sb.append(pq.poll().getKey()).append('\n');
        }

        System.out.println(sb);
    }
}

/*
7 4
apple
ant
sand
apple
append
sand
sand

sand
apple
append


12 5
appearance
append
attendance
swim
swift
swift
swift
mouse
wallet
mouse
ice
age

swift
mouse
appearance
attendance
append
wallet
 */