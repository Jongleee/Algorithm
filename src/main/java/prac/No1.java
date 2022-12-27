package prac;

class Solution1 {
    public String solution(int a, int b) {
        String answer = "";
        // 1월 1일이 금요일이라고 주어졌으므로 금요일을 시작으로 요일의 값을 정하고 각 월의 총 일자를 정해줌
        String[] week = { "FRI", "SAT", "SUN", "MON", "TUE", "WED", "THU" };
        int[] date = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        // 지나온 달의 일수를 모두 더해줌
        int day = 0;
        for (int i = 0; i < a - 1; i++) {
            day += date[i];
        }
        // 해당 일자까지의 일 수를 더해줌
        day += b - 1;
        // 해당 값을 7로 나눈 나머지가 금요일부터 지나온 요일
        answer = week[day % 7];
        return answer;
    }
}
