import java.util.*;

import static java.lang.String.*;


public class testforinterview {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        String str = sc.next();

        Set set = new HashSet();

        Map<String ,Integer> map = new HashMap();
        for(char ch : str.toCharArray()){
            set.add(ch);
            int n = map.get(valueOf(ch)) + 1;
            map.put(valueOf(ch), n);
        }

        if(set.size() < 10){
            if(set.contains(0)){
                set.remove(0);

            }
            int i = 1;
            int flag = 0;
            for(Iterator ch = set.iterator(); ch.hasNext();){

                if(!ch.next().toString().equals(valueOf(i))){

                    flag = 1;
                        System.out.println(i);
                        return;
                }
                i++;


            }if(flag ==0) System.out.println(i);
        }
        else{

            for(String ch : map.keySet()){

            }

        }


    }
}
