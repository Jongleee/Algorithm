public class No37 {

    class Solution {
        public int solution(String dartResult) {
            int answer = 0;
            int[] score = new int[3];
            int index = -1;
            String[] dartSplit = dartResult.split("");
            for (int i = 0; i < dartSplit.length; i++) {
                if (dartSplit[i].matches("[0-9]")) {
                    index++;
                    score[index] = Integer.parseInt(dartSplit[i]);
                    if (dartSplit[i + 1].matches("[0-9]")) {
                        score[index] = 10;
                        i++;
                    }
                }
                switch (dartSplit[i]){
                    case "D":
                        score[index] = (int) Math.pow(score[index], 2);
                        break;
                    case "T":
                        score[index] = (int) Math.pow(score[index], 3);
                        break;
                    case "*":
                        score[index] = score[index] * 2;
                        if (index >= 1) score[index - 1] = score[index - 1] * 2;
                        break;
                    case "#":
                        score[index] = -score[index];
                }


            }
            for (int i = 0; i < 3; i++) {
                answer += score[i];
            }
            return answer;
        }
    }
}
