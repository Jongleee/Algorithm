package com.example.algorithm.java.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindMatchingScore {

    private static int[] scores;
    private static int[] externalCnt;
    private static Map<String, List<Integer>> externalUrl;
    private static Map<Integer, String> url;

    public static int solution(String word, String[] pages) {
        scores = new int[pages.length];
        externalCnt = new int[pages.length];
        int answer = 0;
        double score = 0;
        externalUrl = new HashMap<>();
        url = new HashMap<>();
        word = word.toLowerCase();
        for (int i = 0; i < pages.length; i++) {
            String page = pages[i].toLowerCase();
            setUrl(page, word, i);
        }
        for (int i = 0; i < scores.length; i++) {
            double scoreNow = scores[i];
            if (externalUrl.containsKey(url.get(i))) {
                for (int extIdx : externalUrl.get(url.get(i))) {
                    scoreNow += (double) scores[extIdx] / (double) externalCnt[extIdx];
                }
            }
            if (score < scoreNow) {
                answer = i;
                score = scoreNow;
            }
        }
        return answer;
    }

    public static void setUrl(String page, String word, int index) {

        String urlPattern = "<meta property=\"og:url\" content=\"";
        int urlStart = page.indexOf(urlPattern) + urlPattern.length();
        int urlEnd = page.indexOf("\"/>", urlStart);
        String currentUrl = page.substring(urlStart, urlEnd);
        url.put(index, currentUrl);

        // Set External url
        String[] aHerf = page.split("<a href=\"");
        for (int a = 1; a < aHerf.length; a++) {
            // 연결된 url
            String linkedUrl = aHerf[a].substring(0, aHerf[a].indexOf("\""));
            // 연결 url에 현재 url 추가
            if (!externalUrl.containsKey(linkedUrl))
                externalUrl.put(linkedUrl, new ArrayList<>());
            externalUrl.get(linkedUrl).add(index);
        }

        // Set Extrenal url Count
        externalCnt[index] = aHerf.length - 1;

        // Set score
        page = page.replaceAll("[^a-zA-Z]", " ");

        scores[index] = countWord(page.split(" "), word);
    }

    public static int countWord(String[] words, String word) {
        int cnt = 0;
        for (String w : words) {
            if (w.equals(word))
                cnt++;
        }
        return cnt;
    }

    public static void main(String[] args) {
        System.out.println(solution("Muzi", new String[] {
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://careers.kakao.com/interview/list\"/>\n</head>  \n<body>\n<a href=\"https://programmers.co.kr/learn/courses/4673\"></a>#!MuziMuzi!)jayg07con&&\n\n</body>\n</html>",
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://www.kakaocorp.com\"/>\n</head>  \n<body>\ncon%\tmuzI92apeach&2<a href=\"https://hashcode.co.kr/tos\"></a>\n\n\t^\n</body>\n</html>"
        }));
    }
    //답:1
}
