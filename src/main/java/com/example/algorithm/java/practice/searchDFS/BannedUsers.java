package com.example.algorithm.java.practice.searchDFS;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class BannedUsers {
    static HashSet<HashSet<String>> result;
    public static int solution(String[] userId, String[] bannedId) {
        result = new HashSet<>();

        dfs(new LinkedHashSet<>(), userId, bannedId);

        return result.size();
    }


    private static void dfs(HashSet<String> hs, String[] userIds, String[] bannedId) {
        if (hs.size() == bannedId.length) {
            if (isBanList(hs, bannedId)) {
                result.add(new HashSet<>(hs));
            }
            return;
        }

        for (String userId : userIds) {
            if (hs.add(userId)) {
                dfs(hs, userIds, bannedId);
                hs.remove(userId);
            }
        }
    }


    private static boolean isBanList(HashSet<String> hs, String[] bannedId) {
        int index = 0;
        for (String userID : hs) {
            if (userID.length() != bannedId[index].length()) {
                return false;
            }
            for (int i = 0; i < bannedId[index].length(); i++) {
                if (bannedId[index].charAt(i) == '*') {
                    continue;
                }
                if (userID.charAt(i) != bannedId[index].charAt(i)) {
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
        public int solution(String[] userId, String[] bannedId) {
            regex = new String[bannedId.length];
            for(int i=0; i<bannedId.length; i++){
                regex[i] = bannedId[i].replace("*", "[\\w]");
            }
            dfs(0,userId,0);

            return set.size();
        }
        void dfs(int index, String[] userId, int bit){
            if(index==regex.length){
                set.add(bit);
                return;
            }
            for(int i=0; i<userId.length; ++i) {
                if((((bit>>i) & 1) != 1) && userId[i].matches(regex[index])){
                    dfs(index+1, userId, bit|1<<i);
                }
            }
        }
    }
    public static void main(String[] args) {
        System.out.println(solution(new String[]{"frodo", "fradi", "crodo", "abc123", "frodoc"},
        new String[]{"fr*d*", "*rodo", "******", "******"}));
    }
}
