import java.util.ArrayList;

class Solution {
    public int solution(int[][] board, int[] moves) {
        int answer = 0;
        ArrayList<Integer> arr=new ArrayList<Integer>();
        for (int i = 0; i < moves.length; i++) {
            for (int j = 0; j < board.length ; j++) {
                if (board[j][moves[i]-1] != 0) {
                    arr.add(board[j][moves[i]-1]);
                    break;
                }
            }
        }
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i) == arr.get(i - 1)) answer += 2;
        }
        return answer;
    }
}