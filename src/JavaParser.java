import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 邱景 创建于 2017/1/24.
 * 用途：Java语法分析
 */
public class JavaParser {
    /**
     * 获取编译单元
     *
     * @param fileString 代码路径
     * @return CompilationUnit
     */
    public static CompilationUnit getCompilationUnit(String fileString) {

        ASTParser astParser = ASTParser.newParser(AST.JLS2);
        astParser.setSource(fileString.toCharArray());

      //  astParser.setKind(ASTParser.K_EXPRESSION);
      //  astParser.setResolveBindings(true);
        return (CompilationUnit) astParser.createAST(null);
    }

    //读出文件的内容
    public static String getCode(File sourceFile){
        BufferedReader bufferedReader = null;
        String content="";
        try {
            bufferedReader = new BufferedReader(new FileReader(sourceFile));
            String tempStr;
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