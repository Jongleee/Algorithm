public class No31 {class Solution {
    public int solution1(int n) {
        int answer = 1;
        for (int i = 2; i < n; i++) {
            if (isPrime(i)==1) answer+=1;
        }
        return answer;
    }
    private int isPrime(int n) {
        for (int i = 2; i<=(int)Math.sqrt(n); i++) {
            if (n % i == 0) {
                return 0;
            }
        }
        return 1;
    }

    public int solution2(int n) {
        int answer = 0;
        int[] number = new int[n+1];
        //2부터 n까지의 수를 배열에 넣는다.
        for(int i=2; i<=n; i++) {
            number[i] = i;
        }
        //j=2부터 j배수들을 0으로 만든다.
        //후에 0이면 넘어가고 아니면 그의 배수들을 다시 0으로 만든다.
        for(int i=2; i<=n; i++) {
            if(number[i]==0) continue;
            for(int j= 2*i; j<=n; j += i) {
                number[j] = 0;
            }
        }
        //배열에서 0이 아닌 것들의 개수를 세준다.
        for(int i=0; i<number.length; i++) {
            if(number[i]!=0) {
                answer++;
            }
        }
        return answer;
    }

}
}
