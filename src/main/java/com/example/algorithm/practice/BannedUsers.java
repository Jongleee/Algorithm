package com.example.algorithm.practice;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class BannedUsers {
    static HashSet<HashSet<String>> result;
    public int solution(String[] user_id, String[] banned_id) {
//        new String[]{"frodo", "fradi", "crodo", "abc123", "frodoc"},
//                new String[]{"fr*d*", "*rodo", "******", "******"}
        result = new HashSet<>();

        dfs(new LinkedHashSet<>(), user_id, banned_id);

        return result.size();
    }


    private static void dfs(HashSet<String> hs, String[] user_id, String[] banned_id) {
        if (hs.size() == banned_id.length) {
            if (isBanList(hs, banned_id)) {
                result.add(new HashSet<>(hs));
            }
            return;
        }

        for (String userId : user_id) {
            if (hs.add(userId)) {
                dfs(hs, user_id, banned_id);
                hs.remove(userId);
            }
        }
    }


    private static boolean isBanList(HashSet<String> hs, String[] banned_id) {
        int index = 0;
        for (String userID : hs) {
            if (userID.length() != banned_id[index].length()) {
                return false;
            }
            for (int i = 0; i < banned_id[index].length(); i++) {
                if (banned_id[index].charAt(i) == '*') {
                    continue;
                }
                if (userID.charAt(i) != banned_id[index].charAt(i)) {
                    return false;
                }
            }
            index++;
        }
        return true;
    }
    class Solution2 {
        String[] regex;
        HashSet<Integer> set = new HashSet<>();
        public int solution(String[] user_id, String[] banned_id) {
            regex = new String[banned_id.length];
            for(int i=0; i<banned_id.length; i++){
                regex[i] = banned_id[i].replace("*", "[\\w]");
            }
            dfs(0,user_id,0);

            return set.size();
        }
        void dfs(int index, String[] user_id, int bit){
            if(index==regex.length){
                set.add(bit);
                return;
            }
            for(int i=0; i<user_id.length; ++i) {
                if((((bit>>i) & 1) != 1) && user_id[i].matches(regex[index])){
                    dfs(index+1, user_id, bit|1<<i);
                }
            }
        }
    }
}
