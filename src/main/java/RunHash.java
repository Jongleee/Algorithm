
import javax.lang.model.type.ReferenceType;
import java.util.*;

public class RunHash {
    public static long main(String[] args) {

//        long n = 12131;
//        class Solution {
        class Solution {
            public long solution(long n) {
                Double s=Math.sqrt(n);
                if (s.intValue()==s){
                    return (long)Math.pow(s+1,2);
                }
                else return -1;
            }
        }
//        }
    }
}