import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Solution method = new Solution();
        System.out.println(Arrays.toString(method.solution(new String[][]{{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL", "SFO"}})));
    }
}