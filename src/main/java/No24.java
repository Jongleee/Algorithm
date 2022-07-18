public class No24 {
    class Solution {
        public String solution(String id) {
            String new_id=id.toLowerCase();//1단계 소문자화
            new_id=new_id.replaceAll("[^-_.a-z0-9]", "");//2단계 영소문자, 숫자, 빼기, 밑줄, 점 제외 제거
            new_id = new_id.replaceAll("[.]{2,}", "."); // 3단계 점 2개 이상이면 점 하나로
            new_id = new_id.replaceAll("^[.]|[.]$", ""); // 4단계 처음과 끝 점 제거
            if (new_id.equals("")) new_id+="a";//5단계 비어있을때 "a"대입
            if (new_id.length()>=16) new_id= new_id.substring(0,15);//6-1단계 16자 이상일때 15자까지
            new_id=new_id.replaceAll("[.]$", "");//6-2단계 끝에 .이 있는 경우 제거
            if (new_id.length()<=2)
                while (new_id.length()<3)  new_id+=new_id.charAt(new_id.length()-1);
            //7단계 2자 이하인 경우 마지막 문자열을 3자가 될때까지 반복하여 붙여줌
            return new_id;
        }
    }
}
