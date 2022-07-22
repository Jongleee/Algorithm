public class Main {
    public String solution(int n) {
        String answer = "";
        int temp = 0;
        while (n > 0) {
            answer = answer + Integer.toString(n % 10);
            temp = temp + n % 10;
            n = n / 10;
            if (n >= 1) answer = answer + "+";
            if (n < 1) answer = answer + "=" + Integer.toString(temp);
        }
        return answer;
    }

    public static void main(String[] args) {
        Main method = new Main();
        System.out.println(method.solution(718253));
    }
}