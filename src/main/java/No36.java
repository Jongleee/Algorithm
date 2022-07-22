public class No36 {
    class Solution {
        public String solution(int[] numbers, String hand) {
            String answer = "";
            int rightLocation = 12;
            int leftLocation = 10;
            for (int i = 0; i < numbers.length; i++) {
                if(numbers[i]==0) numbers[i]=11;
                if (numbers[i] % 3 == 0) {
                    answer = answer + "R";
                    rightLocation = numbers[i];
                } else if (numbers[i] % 3 == 1) {
                    answer = answer + "L";
                    leftLocation = numbers[i];
                } else {
                    int leftD = Math.abs(numbers[i] - leftLocation) / 3 + Math.abs(numbers[i] - leftLocation) % 3;
                    int rightD = Math.abs(numbers[i] - rightLocation) / 3 + Math.abs(numbers[i] - rightLocation) % 3;
                    if (rightD > leftD) {
                        answer = answer + "L";
                        leftLocation = numbers[i];
                    } else if (rightD < leftD) {
                        answer = answer + "R";
                        rightLocation = numbers[i];
                    } else{
                        if (hand.equals("right")) {
                            rightLocation = numbers[i];
                            answer = answer + "R";
                        } else {
                            leftLocation = numbers[i];
                            answer = answer + "L";
                        }
                    }
                }

            }
            return answer;
        }
    }
}
