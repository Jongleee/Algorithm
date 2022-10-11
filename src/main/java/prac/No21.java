package prac;

public class No21 {

    class Solution {
        public int solution(int[] nums) {
            int answer = 0;
            //겹치지 않는 숫자 3개 더하고 소수인 경우 answer 카운트 증가
            for (int i = 0; i < nums.length - 2; i++) {
                for (int j = i + 1; j < nums.length - 1; j++) {
                    for (int k = j + 1; k < nums.length; k++) {
                        if (isPrime(nums[i] + nums[j] + nums[k]) == 1) {
                            answer++;
                        }
                    }
                }
            }
            return answer;
        }

        // 소수 판별을 위한 메소드 생성
        private int isPrime(int n) {
            for (int i = 2; i <= (int) Math.sqrt(n); i++) {
                if (n % i == 0) {
                    return 0;
                }
            }
            return 1;
        }

    }
}
