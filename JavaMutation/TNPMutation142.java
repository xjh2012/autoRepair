import java.io.*;
import java.util.ArrayList;

/**
 * @author VellBibi
 *【程序6】GCDAndLCM.java后者是辗转相除法 
 *题目：输入两个正整数m和n，求其最大公约数和最小公倍数。 
 *1.程序分析：利用辗除法。 
 *2.辗转相除法基于如下原理：两个整数的最大公约数等于其中较小的数和两数的相除余数的最大公约数。 
 *3.最小公倍数等于两数之积除以最大公约数
 */
public class threeNumbersPlus {

    /**
     * 最大公约数普通求法
     * @param m
     * @param n
     * @return 最大公约数
     */
    public static int getGCDNormal(int m, int n){//最大公约数普通求法
        int i = (m > n ? n : m);

        for(; i>2; i--){
            if(m%i == 0 && n%i == 0)
                return i;
        }
        return 1;
    }

    /**
     * 最大公约数辗转相除法
     * @param m
     * @param n
     * @return 最大公约数
     */
    public static int getGCD(int m, int n){//最大公约数辗转相除法
        if(m%n == 0) return n;
        else return getGCD(n, m%n);//递归辗转相除
    }

    /**
     * 最小公倍数
     * @param m
     * @param n
     * @return 最小公倍数
     */
    public static int getLCM(int m, int n){
        return (m * n)/getGCD(m, n);//最小公倍数等于两数之积除以最大公约数
    }

    public static int[] GCDAndLCM(int m, int n){

        int[] a = new int[2];
        a[0] = getLCM(m, n);
        a[1] = getGCD(m, n);
        return a;
    }

    public static void main(String[] args) throws IOException {
        // read file content from file
        StringBuffer sb = new StringBuffer("");

        String basicSourseFile = System.getProperty("user.dir") + File.separator;
        FileReader inputReader = new FileReader(basicSourseFile + "input" + File.separator + "GCDAndLCM_input.txt");
        BufferedReader inputCase = new BufferedReader(inputReader);

        String output = basicSourseFile + "JavaTestResult" + File.separator + "GCDAndLCM_output.txt";
        FileWriter fw = new FileWriter(output);

        //输入的每行，期待输出的每行
        String input_str = null;
        String output_str = null;


        //对每一个测试用例
        while ((input_str = inputCase.readLine()) != null) {

            String[] inputs = input_str.split(" ");//测试用例元素组

            int m = 0, n = 0;
            while ((input_str=inputCase.readLine()) != null) {
			  String[] inputs=input_str.split(" ");
			  int m=0, n=0;
			  m=Integer.parseInt(inputs[0]);
			  n=Integer.parseInt(inputs[1]);
			  String sum_str=String.valueOf(GCDAndLCM(m,n)[0]) + " " + String.valueOf(GCDAndLCM(m,n)[1])+ "\n";
			  fw.write(sum_str,0,sum_str.length());
			}
			
            n = Integer.parseInt(inputs[1]);

            String sum_str = String.valueOf(GCDAndLCM(m,n)[0]) + " " + String.valueOf(GCDAndLCM(m,n)[1]) +"\n";
            fw.write(sum_str , 0, sum_str.length());
        }

        inputCase.close();
        inputReader.close();
        fw.flush();
    }
}
