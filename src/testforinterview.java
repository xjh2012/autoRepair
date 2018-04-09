import java.util.*;

import static java.lang.String.*;

//给出两个相同长度的由字符 a 和 b 构成的字符串，定义它们的距离为对应位置不同的字符的数量。如串”aab”与串”aba”的距离为 2；串”ba”与串”aa”的距离为 1；串”baa”和串”baa”的距离为 0。下面给出两个字符串 S 与 T，其中 S 的长度不小于 T 的长度。我们用|S|代表 S 的长度，|T|代表 T 的长度，那么在 S 中一共有|S|-|T|+1 个与 T 长度相同的子串，现在你需要计算 T 串与这些|S|-|T|+1 个子串的距离的和。

//在十进制表示中，任意一个正整数都可以用字符‘0’-‘9’表示出来。但是当‘0’-‘9’这些字符每种字符的数量有限时，可能有些正整数就无法表示出来了。比如你有两个‘1’ ，一个‘2’ ，那么你能表示出 11，12，121 等等，但是无法表示出 10，122，200 等数。

//        现在你手上拥有一些字符，它们都是‘0’-‘9’的字符。你可以选出其中一些字符然后将它们组合成一个数字，那么你所无法组成的最小的正整数是多少？
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
