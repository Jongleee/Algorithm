package com.example.algorithm.java.hashSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PhoneNumberList {
    public boolean solution(String[] phone_book) {
        Set<String> set = new HashSet<>(Arrays.asList(phone_book));

        for (String phone : phone_book) {
            for (int i = 1; i < phone.length(); i++) {
                if (set.contains(phone.substring(0, i))) {
                    return false;
                }
            }
        }

        return true;
    }
}
