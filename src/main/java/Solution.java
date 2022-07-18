import java.util.ArrayList;

class Solution {
    public int[] solution(int[] answers) {
        int[] firstSupo={1,2,3,4,5};
        int[] secondSupo={2,1,2,3,2,4,2,5};
        int[] thirdSupo={3,3,1,1,2,2,4,4,5,5};
        int [] right=new int[3];
        for (int i = 0; i < answers.length; i++) {
            if (firstSupo[i%5]==answers[i]) right[0]++;
            if (secondSupo[i%8]==answers[i]) right[1]++;
            if (thirdSupo[i%10]==answers[i]) right[2]++;
        }
        int maxRight=Math.max(right[1],Math.max(right[2],right[0]));
        ArrayList<Integer>tempAnswer=new ArrayList<>();
        for (int i = 0; i <2; i++) {
            if(maxRight==right[i]) tempAnswer.add(i+1);
        }
//        int answerSize=tempAnswer.size();
//        int []answer=tempAnswer.toArray(new int[answerSize]);
        return tempAnswer.toArray(new int[]);
    }

    public static void main(String[] args){
        Solution ns = new Solution();
        int[]a={1,3,0,0,45,4};
        int[]b={4, 5, 8, 34, 32, 35};
        //변수 선언
        System.out.println(ns.solution(a,b));

    }
}