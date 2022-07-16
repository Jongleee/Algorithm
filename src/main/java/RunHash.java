import java.util.HashMap;
import java.util.*;

public class RunHash {
    public static void main(String[] args) {

        String[] participant = new String[]{"레","아","니"};
        String[] completion = new String[]{"아","니"};

        Map<String, Integer> hash = new HashMap<>();

        System.out.println(hash);  // {} 아무것도 없음

        for (String key : participant) hash.put(key, hash.getOrDefault(key,0) + 1);


        System.out.println(hash);  // 아=1, 레=1, 니=1

        for (String key : completion) hash.put(key, hash.get(key) - 1);

        System.out.println(hash);  // 아=0, 레=1, 니=0

        for (String key : hash.keySet()) {
            if (hash.get(key) !=  0)
                System.out.println(key);  // 레
        }

        System.out.println(hash); // 아=0, 레=1, 니=0
    }
}