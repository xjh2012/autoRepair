package util;


import java.io.*;
import java.util.Scanner;

/**
 * Created by xjh on 2017/12/11.
 */
public class threeNumbersPlus {
    public static void main(String[] args) throws IOException {
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
	


}
