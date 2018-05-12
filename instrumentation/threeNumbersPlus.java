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
		System.out.println("instrumentation " + "sum" + " " + sum);

        // read file content from file
        StringBuffer sb = new StringBuffer("");
		System.out.println("instrumentation " + "sb" + " " + sb);

        String basicSourseFile = System.getProperty("user.dir") + File.separator;
		System.out.println("instrumentation " + "basicSourseFile" + " " + basicSourseFile);
        FileReader inputReader = new FileReader(basicSourseFile + "input" + File.separator + "threeWordPlus_input.txt");
		System.out.println("instrumentation " + "inputReader" + " " + inputReader);
        BufferedReader inputCase = new BufferedReader(inputReader);
		System.out.println("instrumentation " + "inputCase" + " " + inputCase);

        String output = basicSourseFile + "JavaTestResult" + File.separator + "threeWordPlus_output.txt";
		System.out.println("instrumentation " + "output" + " " + output);
        FileWriter fw = new FileWriter(output);
		System.out.println("instrumentation " + "fw" + " " + fw);

        //输入的每行，期待输出的每行
        String input_str = null;
		System.out.println("instrumentation " + "input_str" + " " + input_str);
        String output_str = null;
		System.out.println("instrumentation " + "output_str" + " " + output_str);

        int inputTest=0;
		System.out.println("instrumentation " + "inputTest" + " " + inputTest);
        String delimiter = " ";
		System.out.println("instrumentation " + "delimiter" + " " + delimiter);

        //对每一个测试用例
        while ((input_str = inputCase.readLine()) != null) {
            String input[] = input_str.split(delimiter);//测试用例元素组
			System.out.println("instrumentation " + "input" + " " + input);
            inputTest=input.length;
			System.out.println("instrumentation " + "inputTest" + " " + inputTest);
            sum = 0;
			System.out.println("instrumentation " + "sum" + " " + sum);
            for(int i = 0; i < inputTest; i ++){
                String inputKeyboard = input[i];
				System.out.println("instrumentation " + "inputKeyboard" + " " + inputKeyboard);
                int x = Integer.parseInt(inputKeyboard);
				System.out.println("instrumentation " + "x" + " " + x);

                sum += 1;
				System.out.println("instrumentation " + "sum" + " " + sum);

            }
            // System.out.println(input_str + " =  " + output_str);

            System.out.println(sum);
            String sum_str = String.valueOf(sum) +"\n";
			System.out.println("instrumentation " + "sum_str" + " " + sum_str);
            fw.write(sum_str , 0, sum_str.length());

        }

        inputCase.close();
        inputReader.close();
        fw.flush();

    }


}
