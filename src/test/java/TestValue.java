import java.util.LinkedList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(int cacheSize, String[] cities) {
        if (cacheSize == 0)
            return cities.length * 5;

        int answer = 0;
        LinkedList<String> cache = new LinkedList<>();

        for (int i = 0; i < cities.length; i++) {
            String cityName = cities[i].toUpperCase();

            if (cache.remove(cityName)) {
                answer += 1;
                cache.add(cityName);
            } else {
                answer += 5;
                if (cache.size() >= cacheSize) {
                    cache.remove(0);
                }
                cache.add(cityName);
            }
        }
        return answer;
    }

    @Test
    void 정답() {
        String[] cities = { "Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo",
                "Seoul" };
        Assertions.assertEquals(21, solution(3, cities));
    }
}
