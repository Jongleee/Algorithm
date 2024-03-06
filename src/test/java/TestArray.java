import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    public String[] solution(String[] record) {
        List<String> chatLog = new ArrayList<>();
        Map<String, String> nickMap = new HashMap<>();

        for (String log : record) {
            StringTokenizer st = new StringTokenizer(log);
            String command = st.nextToken();
            String userId = st.nextToken();

            if (command.equals("Enter") || command.equals("Change")) {
                String nickname = st.nextToken();
                nickMap.put(userId, nickname);
            }

            if (command.equals("Enter")) {
                chatLog.add(userId + "님이 들어왔습니다.");
            } else if (command.equals("Leave")) {
                chatLog.add(userId + "님이 나갔습니다.");
            }
        }

        String[] answer = new String[chatLog.size()];
        int logIdx = 0;

        for (String str : chatLog) {
            int endOfId = str.indexOf("님");
            String userId = str.substring(0, endOfId);
            answer[logIdx++] = str.replace(userId, nickMap.get(userId));
        }

        return answer;
    }

    @Test
    void 정답() {
        String[] record = { "Enter uid1234 Muzi", "Enter uid4567 Prodo", "Leave uid1234", "Enter uid1234 Prodo",
                "Change uid4567 Ryan" };
        String[] result = { "Prodo님이 들어왔습니다.", "Ryan님이 들어왔습니다.", "Prodo님이 나갔습니다.", "Prodo님이 들어왔습니다." };

        Assertions.assertArrayEquals(result, solution(record));
    }
}
