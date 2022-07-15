import java.util.*;
class Solution4 {

    public String solution(String[] participant, String[] completion) {
        String answer = "";
        // 두 집단의 비교를 위하여 먼저 오름차순으로 각 배열을 정렬
        Arrays.sort(participant);
        Arrays.sort(completion);
        // 반복문으로 정렬된 값의 비교에서 일치하지 않는 경우를 찾아줌
        for (int i=0;i<completion.length;i++){
            if(!participant[i].equals(completion[i])){
                answer+=participant[i];
                return answer;
            }
        }
        // 위의 반복문에서 일치하지 않는 값이 없다면 배열의 마지막 값이 답이 됨
        answer+=participant[participant.length -1];
        return answer;
    }
}