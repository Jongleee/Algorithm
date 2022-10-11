package prac;

public class No12 {
    public boolean solution(int x) {
        int n = 0;
        String[] a = Integer.toString(x).split("");
        // 숫자로 변환하여 한자리씩 더함
        for (int i = 0; i < a.length; i++) {
            n += Integer.parseInt(a[i]);
        }
        if (x % n == 0) {
            return true;
        } else return false;

    }

    public static void main(String[] args) {
        No12 sn = new No12();
        System.out.println(sn.solution(128));

    }
}