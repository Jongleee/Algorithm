package com.example.algorithm.java.hashMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RecieveReportResult {
    public int[] solution(String[] idList, String[] report, int k) {
        Map<String, Set<String>> reporterInfoMap = new HashMap<>();
        Map<String, Integer> reportedCountInfoMap = new HashMap<>();
        Set<String> reportSet = new HashSet<>(Arrays.asList(report));

        for (String reportInfo : reportSet) {
            String reporter = reportInfo.split(" ")[0];
            String reported = reportInfo.split(" ")[1];

            reporterInfoMap.computeIfAbsent(reporter, key -> new HashSet<>()).add(reported);
            reportedCountInfoMap.put(reported, reportedCountInfoMap.getOrDefault(reported, 0) + 1);
        }

        int[] answer = new int[idList.length];
        for (int i = 0; i < idList.length; i++) {
            String user = idList[i];
            Set<String> reportsReceived = reporterInfoMap.getOrDefault(user, new HashSet<>());

            for (String reported : reportsReceived) {
                if (reportedCountInfoMap.getOrDefault(reported, 0) >= k) {
                    answer[i]++;
                }
            }
        }

        return answer;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals("[2, 1, 1, 0]",
    //             Arrays.toString(solution(new String[] { "muzi", "frodo", "apeach", "neo" },
    //                     new String[] { "muzi frodo", "apeach frodo", "frodo neo", "muzi neo", "apeach muzi" }, 2)));
    //     Assertions.assertEquals("[0, 0]", Arrays
    //             .toString(solution(new String[] { "con", "ryan" },
    //                     new String[] { "ryan con", "ryan con", "ryan con", "ryan con" }, 3)));
    // }
}
