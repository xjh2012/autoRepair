import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.File;

/**
 * Created by xjh on 2017/12/12.
 */
public class JavaASTTree {
    public static void main(String[] args) {
        String basicSourceFile = System.getProperty("user.dir") + File.separator;

        //模板程序,语法分析
        String modelSourceFile = basicSourceFile + "JavaModelProgram" + File.separator + "threeNumbersPlus.java";
        File file = new File(modelSourceFile);//模板程序路径

        ASTParser parser = ASTParser.newParser(AST.JLS8);

        JavaParser javaParser = new JavaParser();//模板程序的语法树
        String fileString = javaParser.getCode(file);//模板程序内容
        parser.setSource(fileString.toCharArray());
        CompilationUnit unit = (CompilationUnit) parser.createAST(null);//语法单元

       // CompilationUnit modelUnit = javaParser.getCompilationUnit(fileString);
        JavaVisitor javaVisitor = new JavaVisitor();
        unit.accept(javaVisitor);
    }
}
