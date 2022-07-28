public class No39 {
<<<<<<< HEAD
    public int solution(int n) {
        int answer = 1;
        for (int i = 1; i < n; i++) {
            int sum = i;
            for (int j = i + 1; j < n; j++) {
                sum += j;
                if (sum > n) break;
                if (sum == n) {
                    answer += 1;
                    break;
                }
            }
        }
        return answer;
    }

=======
    class Solution {
        public int solution(int n) {
            int answer = 1;
            for (int i =1; i < n; i++) {
                int sum=i;
                for (int j = i+1; j <n; j++) {
                    sum+=j;
                    if(sum>n) break;
                    if(sum==n){answer+=1; break;}
                }
            }
            return answer;
        }
    }
>>>>>>> fb4f084ef03003a3f04c923d4d076afc4af57e35
}
