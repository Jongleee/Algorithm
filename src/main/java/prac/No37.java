package prac;

public class No37 {

    class Solution {
        public int solution(String dartResult) {
            int answer = 0;
            int[] score = new int[3];
            int index = -1;
            String[] dartSplit = dartResult.split("");
            int cnt=0;
            while (cnt < dartSplit.length) {
                if (dartSplit[cnt].matches("\\d")) {
                    index++;
                    score[index] = Integer.parseInt(dartSplit[cnt]);
                    if (dartSplit[cnt + 1].matches("\\d")) {
                        score[index] = 10;
                        cnt++;
                    }
                }
                switch (dartSplit[cnt]) {
                    case "D":
                        score[index] = (int) Math.pow(score[index], 2);
                        break;
                    case "T":
                        score[index] = (int) Math.pow(score[index], 3);
                        break;
                    case "*":
                        score[index] = score[index] * 2;
                        if (index >= 1)
                            score[index - 1] = score[index - 1] * 2;
                        break;
                    case "#":
                        score[index] = -score[index];
                        break;
                    default:
                        break;
                }
                cnt++;

            }
            for (int i = 0; i < 3; i++) {
                answer += score[i];
            }
            return answer;
        }
    }
}
