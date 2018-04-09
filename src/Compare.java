import java.io.*;
import java.util.Scanner;
public class Compare {
    private static int max(int x, int y){
        int max = 0;
        System.out.println("instrumentation "+"max "+ max);
        if(x > y){
            max = x;
            System.out.println("instrumentation "+"max "+ max);
        }
        else{
            max = y;
            System.out.println("instrumentation "+"max "+ max);
        }
        return max;
    }

    private static int threeNumbersPlus(int n){
        int sum = 0;
        System.out.println("instrumentation "+"sum "+ sum);

        for(int i = 0; i < n; i ++){
            Scanner sc = new Scanner(System.in);

            int x = sc.nextInt();
            System.out.println("instrumentation "+"x "+ x);

            sum += x;
            System.out.println("instrumentation "+"sum "+ sum);
        }

        return sum;
    }

    public static void main(String[] args){
//        Scanner sc = new Scanner(System.in);
//        int x = sc.nextInt();
//        int y = sc.nextInt();
//        System.out.println(max(x,y));
        System.out.println(threeNumbersPlus(7));
    }
}
