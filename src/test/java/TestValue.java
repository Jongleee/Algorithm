import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int[] citations) {
        Arrays.sort(citations);
        int n = citations.length;
        
        for (int i = 0; i < n; i++) {
            int h = n - i;
            int minCitations = Math.min(citations[i], h);
            
            if (minCitations >= h) {
                return h;
            }
        }

        return 0;
    }


    @Test
    void 정답() {
        int[] citiations = { 3, 0, 6, 1, 5 };
        Assertions.assertEquals(3, solution(citiations));
    }
}
