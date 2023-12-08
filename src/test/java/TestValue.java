import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    public int solution(String[] lines) {
        TimeInfo[] timeInfo = convertTime(lines);
        int answer = 0;

        for (int i = 0; i < timeInfo.length; i++) {
            double area = timeInfo[i].endTime + 1;
            int cnt = 1;

            for (int j = i + 1; j < timeInfo.length; j++) {
                if (timeInfo[j].startTime < area) {
                    cnt++;
                }
            }

            answer = Math.max(answer, cnt);
        }

        return answer;
    }

    private TimeInfo[] convertTime(String[] lines) {
        TimeInfo[] timeInfo = new TimeInfo[lines.length];

        for (int i = 0; i < lines.length; i++) {
            String[] split = lines[i].split(" ");
            String[] timeParts = split[1].split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);
            double second = Double.parseDouble(timeParts[2]);
            double processingTime = Double.parseDouble(split[2].substring(0, split[2].length() - 1));

            double totalSeconds = hour * 3600 + minute * 60 + second;
            double startTime = totalSeconds - processingTime + 0.001;
            double endTime = totalSeconds;

            timeInfo[i] = new TimeInfo(startTime, endTime);
        }

        return timeInfo;
    }

    private static class TimeInfo {
        double startTime;
        double endTime;

        public TimeInfo(double startTime, double endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    @Test
    void 정답() {
        String[] t1 = {
                "2016-09-15 20:59:57.421 0.351s",
                "2016-09-15 20:59:58.233 1.181s",
                "2016-09-15 20:59:58.299 0.8s",
                "2016-09-15 20:59:58.688 1.041s",
                "2016-09-15 20:59:59.591 1.412s",
                "2016-09-15 21:00:00.464 1.466s",
                "2016-09-15 21:00:00.741 1.581s",
                "2016-09-15 21:00:00.748 2.31s",
                "2016-09-15 21:00:00.966 0.381s",
                "2016-09-15 21:00:02.066 2.62s" };
        String[] t2 = {
                "2016-09-15 01:00:04.002 2.0s",
                "2016-09-15 01:00:07.000 2s" };
        String[] t3 = {
                "2016-09-15 01:00:04.001 2.0s",
                "2016-09-15 01:00:07.000 2s" };
        Assertions.assertEquals(7, solution(t1));
        Assertions.assertEquals(2, solution(t2));
        Assertions.assertEquals(1, solution(t3));
    }
}
