package util;

/**
 * Created by xjh on 2017/12/14.
 */
import ags.SourceCompiler;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class DynamicCompileTest {
    public boolean compile(File sourceDir, String mutation_name) throws IOException {
        // 2.将欲动态编译的代码写入文件中 1.创建临时目录 2.写入临时文件目录
        File dir = new File(System.getProperty("user.dir") + "/temp"); //临时目录
        // 如果 \temp 不存在 就创建
        if (!dir.exists()) {
            dir.mkdir();
        }
//        FileWriter writer = new FileWriter(new File(dir, "Hello.java"));
//        writer.write(source);
//        writer.flush();
//        writer.close();
        String basicSourseFile = System.getProperty("user.dir") + File.separator;
        // 3.取得当前系统的编译器
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        // 4.获取一个文件管理器
        StandardJavaFileManager javaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        // 5.文件管理器根与文件连接起来
        Iterable it = javaFileManager.getJavaFileObjects(new File(sourceDir, mutation_name));
        // 6.创建编译任务
        CompilationTask task = javaCompiler.getTask(null, javaFileManager, null, Arrays.asList("-d", "./temp"), null, it);
        // 7.执行编译
        boolean flag = task.call();
        if(flag == false){
            System.out.println("Compile failed ;;;;;;;;;;;;;;;;;;;;;;;;;");
            FileReader outputExceptionReader = new FileReader(basicSourseFile + "JavaTestResult" + File.separator + "threeWordPlus_output.txt");
            return false;
        }
        else{
            System.out.println("compile success!!!");
        }
        javaFileManager.close();

        // 8.运行程序
        Runtime run = Runtime.getRuntime();
        Process process = run.exec("java -cp ./temp util/threeNumbersPlus");

        //读取输出的东西
        InputStream in = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));


        String info = "";//执行输出的东西
        while ((info = reader.readLine()) != null) {
            System.out.println(info);
//
        }


        return true;

    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
       // compile();
        // 1.创建需要动态编译的代码字符串
//        String nr = "\r\n"; //回车
//        String source = "package util; " + nr +
//                "import java.io.IOException;\n" +
//                "import java.util.Scanner;" + nr +
//                " public class Hello {" + nr +
//                " public static void main(String[] args) throws IOException {" + nr +
//                " int x;" + nr +
//                " int sum = 0;" + nr +
//                " Scanner sc = new Scanner(System.in);" + nr +
////                " for(int i = 0; i < 3; i ++ ){" + nr +
//                " x = sc.nextInt();" + nr +
////                " sum += x;" + nr +
////                " }" + nr +
//                " System.out.println(sum);" + nr +
//                " }" + nr +
//                " }";

    }
}