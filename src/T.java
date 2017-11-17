import java.io.*;
import java.util.Random;

/**
 * 邱景 创建于 2017/1/24.
 * 用途：工具类
 */
public class T {
    /**
     * 读取文件内容
     *
     * @param file 要读取内容的文件
     * @return 文件内容
     */
    public static String readFile(File file) {
        try {
            return org.apache.commons.io.FileUtils.readFileToString(file, "utf8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 读取文件内容
     *
     * @param fileName 要读取内容的文件名
     * @return 文件内容
     */
    public static String readFile(String fileName) {
        try {
            return org.apache.commons.io.FileUtils.readFileToString(new File(fileName), "utf8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 写入文件内容
     *
     * @param fileName 要写入内容的文件名
     * @param data 要写入的文件内容
     * @return 文件内容
     */
    public static void writeFile(String fileName, String data) {
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(data,0,data.length());
            fw.flush();
            //Runtime.getRuntime().exec(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int random(int scope) {
        Random random = new Random();
        return Math.abs(random.nextInt())%scope;
    }


}
