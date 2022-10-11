class Solution {
    public int solution(int[] arr) {
        int answer = arr[0];
        for (int i = 1; i <arr.length ; i++) {
            int gcd = gcd(answer, arr[i]);
            answer= answer*arr[i]/gcd;
        }

        return answer;
    }
    public int gcd(int x, int y){
        int a=Math.max(x,y);
        int b = Math.min(x,y);
        while (a%b!=0){
            int r = a%b;
            a=b;
            b=r;
        }
        return b;
    }
}