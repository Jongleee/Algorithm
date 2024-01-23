package com.example.algorithm.java.searchDFS;

import java.util.HashSet;
import java.util.Set;

public class BannedUsers {
    private Set<Set<String>> result;

    public int solution(String[] userId, String[] bannedId) {
        result = new HashSet<>();
        dfs(new HashSet<>(), 0, userId, bannedId);
        return result.size();
    }

    private void dfs(Set<String> currentSet, int index, String[] userIds, String[] bannedId) {
        if (index == bannedId.length) {
            result.add(new HashSet<>(currentSet));
            return;
        }

        for (String userId : userIds) {
            if (!currentSet.contains(userId) && isMatching(userId, bannedId[index])) {
                currentSet.add(userId);
                dfs(currentSet, index + 1, userIds, bannedId);
                currentSet.remove(userId);
            }
        }
    }

    private boolean isMatching(String userId, String bannedId) {
        if (userId.length() != bannedId.length()) {
            return false;
        }
        for (int i = 0; i < bannedId.length(); i++) {
            if (bannedId.charAt(i) == '*') {
                continue;
            }
            if (userId.charAt(i) != bannedId.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    // @Test
    // void 정답() {
    //     String[] user_id1 = { "frodo", "fradi", "crodo", "abc123", "frodoc" };
    //     String[] banned_id1 = { "fr*d*", "abc1**" };

    //     String[] user_id2 = { "frodo", "fradi", "crodo", "abc123", "frodoc" };
    //     String[] banned_id2 = { "*rodo", "*rodo", "******" };

    //     String[] user_id3 = { "frodo", "fradi", "crodo", "abc123", "frodoc" };
    //     String[] banned_id3 = { "fr*d*", "*rodo", "******", "******" };

    //     Assertions.assertEquals(2, solution(user_id1, banned_id1));
    //     Assertions.assertEquals(2, solution(user_id2, banned_id2));
    //     Assertions.assertEquals(3, solution(user_id3, banned_id3));
    // }
}
