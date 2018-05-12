package util;


import java.io.*;
import java.util.Scanner;

/**
 * Created by xjh on 2017/12/11.
 */
public class threeNumbersPlus {
    public static void main(String[] args) throws IOException {
        // new CppASTTree();
        int sum = 0;
        // Scanner sc = new Scanner(System.in);

        // read file content from file
        StringBuffer sb = new StringBuffer("");

        String basicSourseFile = System.getProperty("user.dir") + File.separator;
        FileReader inputReader = new FileReader(basicSourseFile + "input" + File.separator + "threeWordPlus_input.txt");
        BufferedReader inputCase = new BufferedReader(inputReader);

        String output = basicSourseFile + "JavaTestResult" + File.separator + "threeWordPlus_output.txt";
        FileWriter fw = new FileWriter(output);

        //输入的每行，期待输出的每行
        String input_str = null;
        String output_str = null;

        int inputTest=0;
        String delimiter = " ";

        //对每一个测试用例
        while ((input_str = inputCase.readLine()) != null) {
            String input[] = input_str.split(delimiter);//测试用例元素组
            inputTest=input.length;
            sum = 0;
            System.out.println(sum);
			

            System.out.println(sum);
            String sum_str = String.valueOf(sum) +"\n";
            fw.write(sum_str , 0, sum_str.length());

        }

        inputCase.close();
        inputReader.close();
        fw.flush();

    }


}
