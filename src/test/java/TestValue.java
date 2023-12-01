import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    private int[] scores;
    private int[] externalCnt;
    private Map<String, List<Integer>> externalUrl;
    private Map<Integer, String> url;

    public int solution(String word, String[] pages) {
        initializeDataStructures(pages.length);
        word = word.toLowerCase();

        for (int i = 0; i < pages.length; i++) {
            String page = pages[i].toLowerCase();
            setUrl(page, word, i);
        }

        return calculatePageRank();
    }

    private void initializeDataStructures(int size) {
        scores = new int[size];
        externalCnt = new int[size];
        externalUrl = new HashMap<>();
        url = new HashMap<>();
    }

    private void setUrl(String page, String word, int index) {
        String urlPattern = "<meta property=\"og:url\" content=\"";
        int urlStart = page.indexOf(urlPattern) + urlPattern.length();
        int urlEnd = page.indexOf("\"/>", urlStart);
        String currentUrl = page.substring(urlStart, urlEnd);
        url.put(index, currentUrl);

        String[] aHerf = page.split("<a href=\"");
        for (int a = 1; a < aHerf.length; a++) {
            String linkedUrl = aHerf[a].substring(0, aHerf[a].indexOf("\""));
            externalUrl.computeIfAbsent(linkedUrl, k -> new ArrayList<>()).add(index);
        }

        externalCnt[index] = aHerf.length - 1;

        page = page.replaceAll("[^a-zA-Z]", " ");
        scores[index] = countWord(page.split(" "), word);
    }

    private int countWord(String[] words, String word) {
        int cnt = 0;
        for (String w : words) {
            if (w.equals(word)) {
                cnt++;
            }
        }
        return cnt;
    }

    private int calculatePageRank() {
        int answer = 0;
        double maxScore = 0;

        for (int i = 0; i < scores.length; i++) {
            double scoreNow = scores[i];

            if (externalUrl.containsKey(url.get(i))) {
                for (int extIdx : externalUrl.get(url.get(i))) {
                    scoreNow += (double) scores[extIdx] / (double) externalCnt[extIdx];
                }
            }

            if (maxScore < scoreNow) {
                answer = i;
                maxScore = scoreNow;
            }
        }

        return answer;
    }

    @Test
    void 정답() {
        String[] p1 = {
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://a.com\"/>\n</head>  \n<body>\nBlind Lorem Blind ipsum dolor Blind test sit amet, consectetur adipiscing elit. \n<a href=\"https://b.com\"> Link to b </a>\n</body>\n</html>",
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://b.com\"/>\n</head>  \n<body>\nSuspendisse potenti. Vivamus venenatis tellus non turpis bibendum, \n<a href=\"https://a.com\"> Link to a </a>\nblind sed congue urna varius. Suspendisse feugiat nisl ligula, quis malesuada felis hendrerit ut.\n<a href=\"https://c.com\"> Link to c </a>\n</body>\n</html>",
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://c.com\"/>\n</head>  \n<body>\nUt condimentum urna at felis sodales rutrum. Sed dapibus cursus diam, non interdum nulla tempor nec. Phasellus rutrum enim at orci consectetu blind\n<a href=\"https://a.com\"> Link to a </a>\n</body>\n</html>" };
        String[] p2 = {
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://careers.kakao.com/interview/list\"/>\n</head>  \n<body>\n<a href=\"https://programmers.co.kr/learn/courses/4673\"></a>#!MuziMuzi!)jayg07con&&\n\n</body>\n</html>",
                "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://www.kakaocorp.com\"/>\n</head>  \n<body>\ncon%\tmuzI92apeach&2<a href=\"https://hashcode.co.kr/tos\"></a>\n\n\t^\n</body>\n</html>" };
        Assertions.assertEquals(0, solution("blind", p1));
        Assertions.assertEquals(1, solution("Muzi", p2));
    }
}
