package util;

/**
 * Created by xjh on 2017/12/14.
 */
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static javax.tools.StandardLocation.CLASS_PATH;

public class ClassUtil {
    private static final Log logger = LogFactory.getLog(ClassUtil.class);
    private static JavaCompiler compiler;
    static{
        compiler = ToolProvider.getSystemJavaCompiler();
    }

    /**
     * 获取java文件路径
     * @param file
     * @return
     */
    private static String getFilePath(String file){
        int last1 = file.lastIndexOf('/');
        int last2 = file.lastIndexOf('\\');
        return file.substring(0, last1>last2?last1:last2)+File.separatorChar;
    }

    /**
     * 编译java文件
     * @param ops 编译参数
     * @param files 编译文件
     */
    private static void javac(List<String> ops, String... files){
        StandardJavaFileManager manager = null;
        try{
            manager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(files);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, ops, null, it);
            task.call();
            if(logger.isDebugEnabled()){
                for(String file:files)
                    logger.debug("Compile Java File:" + file);
            }
        }catch(Exception e){
            logger.error(e);
        }finally{
            if(manager!=null){
                try {
                    manager.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 生成java文件
     * @param file 文件名
     * @param source java代码
     * @throws Exception
     */
    private static void writeJavaFile(String file,String source)throws Exception{
        if(logger.isDebugEnabled()){
            logger.debug("Write Java Source Code to:"+file);
        }
        BufferedWriter bw = null;
        try{
            File dir = new File(getFilePath(file));
            if(!dir.exists())
                dir.mkdirs();
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(source);
            bw.flush();
        }catch(Exception e){
            throw e;
        }finally{
            if(bw!=null){
                bw.close();
            }
        }
    }
    /**
     * 加载类
     * @param name 类名
     * @return
     */
    private static Class<?> load(String name){
        Class<?> cls = null;
        ClassLoader classLoader = null;
        try{
            classLoader = ClassUtil.class.getClassLoader();
            cls = classLoader.loadClass(name);
            if(logger.isDebugEnabled()){
                logger.debug("Load Class["+name+"] by "+classLoader);
            }
        }catch(Exception e){
            logger.error(e);
        }
        return cls;
    }

    /**
     * 编译代码并加载类
     * @param filePath java代码路径
     * @param source java代码
     * @param clsName 类名
     * @param ops 编译参数
     * @return
     */
    public static Class<?> loadClass(String filePath, String source, String clsName, List<String> ops){
        try {
            writeJavaFile(CLASS_PATH+filePath,source);
            javac(ops,CLASS_PATH+filePath);
            return load(clsName);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 调用类方法
     * @param cls 类
     * @param methodName 方法名
     * @param paramsCls 方法参数类型
     * @param params 方法参数
     * @return
     */
    public static Object invoke(Class<?> cls,String methodName,Class<?>[] paramsCls,Object[] params){
        Object result = null;
        try {
            Method method = cls.getDeclaredMethod(methodName, paramsCls);
            Object obj = cls.newInstance();
            result = method.invoke(obj, params);
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }
}