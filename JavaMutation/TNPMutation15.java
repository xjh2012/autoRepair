package util;


import java.io.*;
import java.util.Scanner;

/**
 * Created by xjh on 2017/12/11.
 */
public class threeNumbersPlus {
    public static void main(String[] args) throws IOException {
	  int sum=0;
	  StringBuffer sb=new StringBuffer("");
	  String basicSourseFile=System.getProperty("user.dir") + File.separator;
	  FileReader inputReader=new FileReader(basicSourseFile + "input" + File.separator+ "threeWordPlus_input.txt");
	  BufferedReader inputCase=new BufferedReader(inputReader);
	  String output=basicSourseFile + "JavaTestResult" + File.separator+ "threeWordPlus_output.txt";
	  FileWriter fw=new FileWriter(output);
	  String input_str=null;
	  String output_str=null;
	  int inputTest=0;
	  String delimiter=" ";
	  while ((input_str=inputCase.readLine()) != null) {
	    String input[]=input_str.split(delimiter);
	    inputTest=input.length;
	    sum=0;
	    for (int i=0; i < inputTest; i++) {
	      String inputKeyboard=input[i];
	      int x=Integer.parseInt(inputKeyboard);
	      sum+=x;
	    }
	    System.out.println(sum);
	    String sum_str=String.valueOf(sum) + "\n";
	    fw.write(sum_str,0,sum_str.length());
	  }
	  inputCase.close();
	  inputReader.close();
	  fw.flush();
	}
	


}
