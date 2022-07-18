public class No17 {
        public int[] solution(int[] lottos, int[] win_nums) {
            ///가려진 숫자의 수를 구함
            int cntUnknown=0;
            for (int i = 0; i <6; i++) {
                if (lottos[i]==0) cntUnknown++;
            }
            ///보이는 숫자 중 맞는 갯수를 구함
            int cntMatch=0;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if(lottos[i]==win_nums[j]){
                        cntMatch++;
                    }
                }
            }
            // 7에서 맞는 갯수의 값을 빼면 등수가 되는데 두값이 0인 경우에 7등이 되므로
            // 그 경우 6으로 고정할 수 있게 Math.min 사용
            return new int[] {Math.min(7-(cntMatch+cntUnknown),6),Math.min(7-cntMatch,6)};
        }
}
