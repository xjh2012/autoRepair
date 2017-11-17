package util;

/**
 * Created by xjh on 2017/11/17.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class CodeFileReader {

    public ArrayList<File> getCodeFiles(File rootFile) {
        ArrayList<File> codeFiles = new ArrayList<File>();
        if(rootFile.isDirectory()){
            File[] files = rootFile.listFiles();
            for(int i = 0;i<files.length;i++){
                if(files[i].isFile()){
                    if(files[i].getName().indexOf(".java")!=-1)
                        codeFiles.add(files[i]);
                }else{
                    codeFiles.addAll(getCodeFiles(files[i]));
                }
            }
        }else{
            codeFiles.add(rootFile);
        }

        return codeFiles;
    }

    public static String getCode(File sourceFile){
        BufferedReader bufferedReader = null;
        String content = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(sourceFile));
            String tempStr = null;
            while ((tempStr = bufferedReader.readLine()) != null){
                content +=tempStr+"\n";
            }
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                }catch (IOException e){
                }
            }
        }
        return content;
    }
}
