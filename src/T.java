import java.io.File;
import java.io.IOException;

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



}
